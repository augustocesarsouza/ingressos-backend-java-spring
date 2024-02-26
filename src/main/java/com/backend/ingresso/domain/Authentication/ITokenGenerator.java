package com.backend.ingresso.domain.Authentication;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.backend.ingresso.domain.InfoErrors.InfoErrors;
import com.backend.ingresso.domain.entities.User;
import com.backend.ingresso.domain.entities.UserPermission;

import java.util.List;

public interface ITokenGenerator{
    public InfoErrors<TokenOutValue> generatorByEmail(User user, List<UserPermission> userPermission, String typeTokenGenerateEmailOrCpf);
    public Claim getClaimUserId(String token) throws TokenExpiredException;

}
