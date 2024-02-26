package com.backend.ingresso.data.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.backend.ingresso.domain.Authentication.ITokenGenerator;
import com.backend.ingresso.domain.Authentication.TokenOutValue;
import com.backend.ingresso.domain.InfoErrors.InfoErrors;
import com.backend.ingresso.domain.entities.User;
import com.backend.ingresso.domain.entities.UserPermission;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TokenGenerator implements ITokenGenerator {
    @Value("${JWT-SECRET-KEY}")
    private String secretKey;
    @Override
    public InfoErrors<TokenOutValue> generatorByEmail(User user, List<UserPermission> userPermission,
                                                      String typeTokenGenerateEmailOrCpf) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        LocalDateTime currentUtcDateTime = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime expires = currentUtcDateTime.plusHours(1);
        Date expiresDate = Date.from(expires.toInstant(ZoneOffset.UTC));

        var permission = userPermission.stream().map((el) ->
                el.getPermission().getPermissionName()).collect(Collectors.joining(","));
        // testar permission - amanha e continuar imprementar

        if(user == null)
            return InfoErrors.Fail(new TokenOutValue(), "error: user is null");

        String token = JWT.create()
                //.withIssuer("Produtos")
                .withClaim(typeTokenGenerateEmailOrCpf, user.getEmail())
                //.withClaim("Password", password) - tirei daqui
                .withClaim("userID", user.getId().toString())
                .withClaim("Permissioes", permission)
                .withExpiresAt(expiresDate)
                .sign(algorithm);

        var tokenValue = new TokenOutValue();
        var successfullyCreatedToken = tokenValue.ValidateToken(token, expiresDate);

        if (successfullyCreatedToken)
        {
            return InfoErrors.Ok(tokenValue);
        }
        else
        {
            return InfoErrors.Fail(new TokenOutValue(), "error when creating token");
        }
    }
    public Claim getClaimUserId(String token) throws TokenExpiredException {
        return JWT.require(Algorithm.HMAC256(secretKey))
                //.withIssuer("Produtos")
                .build().verify(token)//verific se o token está valido ou nao
                .getClaim("userID");
    }
}
