package com.backend.ingresso.application.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.backend.ingresso.application.dto.TokenAlreadyVisualizedDTO;
import com.backend.ingresso.application.services.interfaces.IUserConfirmationService;
import com.backend.ingresso.data.utilToken.ForbiddenError;
import com.backend.ingresso.data.utilityExternal.Interface.ICacheRedisUti;
import com.backend.ingresso.domain.repositories.IUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class UserConfirmationService implements IUserConfirmationService {
    private final IUserRepository userRepository;
    private final ICacheRedisUti cacheRedisUti;
    @Value("${JWT-SECRET-KEY}")
    private String secretKey;

    //put redis here
    public UserConfirmationService(IUserRepository userRepository, ICacheRedisUti cacheRedisUti) {
        this.userRepository = userRepository;
        this.cacheRedisUti = cacheRedisUti;
    }
    @Override
    @Transactional
    public ResultService<TokenAlreadyVisualizedDTO> GetConfirmToken(String token) {
        try{
            Claim idClaim = getClaimId(token);
            UUID idClaimValue = UUID.fromString(idClaim.asString());

            String keyRedis = String.format("TokenString%s", idClaimValue);
            String cache = cacheRedisUti.getString(keyRedis);

            var user = userRepository.getUserByIdCheckUserExists(idClaimValue);

            if(user == null)
                return ResultService.Fail("user not found");

            if(!user.getConfirmEmail() && cache == null){
                //aqui tem que criar um jeito de reenviar token de criação se já Expirou no redis e nao confirmou ainda
                //criar e tratar lá no frontend ao para ele reenviar e criar uma rota aqui no backend

                //na proxima vez que e SE LOGAR mandar novamente a confirmação no email dele
                //verifcia se ele já confirmou o email se não antes de logar mandar novamente
                return ResultService.Fail("redis has expired resend token");
            }

            if(cache != null){
                cacheRedisUti.remove(keyRedis);
            }else {
                return ResultService.Ok(new TokenAlreadyVisualizedDTO(1, "already viewed"));
            }

            user.confirmEmail(true);

            var userDatabaseUpdate = userRepository.update(user);
            if(userDatabaseUpdate == null)
                return ResultService.Fail("error updating user in database");

            return ResultService.Ok(new TokenAlreadyVisualizedDTO(0,"all right"));

        }catch (NullPointerException ex){//Info Header nao informado e acessar claim são mesmo erro
            return ResultService.Fail("error some information needs to be checked or Token is invalid or Header is missing information");
        }catch (TokenExpiredException e){
            return ResultService.Fail("token has expired");
        }catch (SignatureVerificationException exSig){
            String message = exSig.getMessage();
            return ResultService.Fail(message);
        }
    }

    private Claim getClaimId(String token) throws TokenExpiredException {
        return JWT.require(Algorithm.HMAC256(secretKey))
                .build().verify(token)
                .getClaim("id");
    }
}
