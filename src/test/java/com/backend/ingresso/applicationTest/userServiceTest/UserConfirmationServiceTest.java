package com.backend.ingresso.applicationTest.userServiceTest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.backend.ingresso.application.services.UserConfirmationService;
import com.backend.ingresso.data.utilityExternal.Interface.ICacheRedisUti;
import com.backend.ingresso.domain.entities.User;
import com.backend.ingresso.domain.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@RunWith(Suite.class)
//@Suite.SuiteClasses({/* outras classes de teste */})
public class UserConfirmationServiceTest {
    @Mock
    private IUserRepository userRepository;
    @Mock
    private ICacheRedisUti cacheRedisUti;

    private UserConfirmationService userConfirmationService;
    private String secretKey;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        userConfirmationService = new UserConfirmationService(userRepository,
                cacheRedisUti);
        secretKey = "secretKey123456";

        ReflectionTestUtils.setField(userConfirmationService, "secretKey", secretKey);
    }

    //GetConfirmToken//
    @Test
    public void shouldGetConfirmTokenWithoutErrors(){
        User user = new User(UUID.fromString("a7f1d17f-67b2-4203-8af4-1f47cf929d50"),
                null, "augustoinstagram@gmail.com", null, null, true);

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        LocalDateTime currentUtcDateTime = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime expires = currentUtcDateTime.plusMinutes(10);
        Date expiresDate = Date.from(expires.toInstant(ZoneOffset.UTC));

        String token = JWT.create()
                .withClaim("id", user.getId().toString())
                .withExpiresAt(expiresDate)
                .sign(algorithm);

        when(cacheRedisUti.getString(any())).thenReturn("redisRandom");
        when(userRepository.getUserByIdCheckUserExists(any())).thenReturn(user);
        when(userRepository.update(any())).thenReturn(new User());

        var result = userConfirmationService.GetConfirmToken(token);

        assertTrue(result.IsSuccess);
        assertEquals(result.Data.getTokenAlreadyVisualized(), 0);
        assertEquals(result.Data.getErroMessage(), "all right");
    }

    @Test
    public void shouldGetConfirmTokenWithErrorClaimIdNotFound(){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        LocalDateTime currentUtcDateTime = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime expires = currentUtcDateTime.plusMinutes(10);
        Date expiresDate = Date.from(expires.toInstant(ZoneOffset.UTC));

        String token = JWT.create()
                .withExpiresAt(expiresDate)
                .sign(algorithm);

        var result = userConfirmationService.GetConfirmToken(token);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error some information needs to be checked or Token is invalid or Header is missing information");
    }

    @Test
    public void shouldGetConfirmTokenWithErrorUserNotFound(){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        LocalDateTime currentUtcDateTime = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime expires = currentUtcDateTime.plusMinutes(10);
        Date expiresDate = Date.from(expires.toInstant(ZoneOffset.UTC));

        String token = JWT.create()
                .withClaim("id", "a7f1d17f-67b2-4203-8af4-1f47cf929d50")
                .withExpiresAt(expiresDate)
                .sign(algorithm);

        when(cacheRedisUti.getString(any())).thenReturn(null);
        when(userRepository.getUserByIdCheckUserExists(any())).thenReturn(null);

        var result = userConfirmationService.GetConfirmToken(token);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "user not found");
    }

    @Test
    public void shouldGetConfirmTokenWithErrorTokenExpire(){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        LocalDateTime currentUtcDateTime = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime expires = currentUtcDateTime.minusMinutes(1);
        Date expiresDate = Date.from(expires.toInstant(ZoneOffset.UTC));

        String token = JWT.create()
                .withClaim("id", "a7f1d17f-67b2-4203-8af4-1f47cf929d50")
                .withExpiresAt(expiresDate)
                .sign(algorithm);

        when(cacheRedisUti.getString(any())).thenReturn(null);
        when(userRepository.getUserByIdCheckUserExists(any())).thenReturn(null);

        var result = userConfirmationService.GetConfirmToken(token);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "token has expired");
    }

    @Test
    public void shouldGetConfirmTokenWithErrorCacheRedisNotContainConfirmationTokenAndUserNotConfirmToken(){
        User user = new User(UUID.fromString("a7f1d17f-67b2-4203-8af4-1f47cf929d50"),
                null, "augustoinstagram@gmail.com", null, null, false);

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        LocalDateTime currentUtcDateTime = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime expires = currentUtcDateTime.plusMinutes(10);
        Date expiresDate = Date.from(expires.toInstant(ZoneOffset.UTC));

        String token = JWT.create()
                .withClaim("id", user.getId().toString())
                .withExpiresAt(expiresDate)
                .sign(algorithm);

        when(cacheRedisUti.getString(any())).thenReturn(null);
        when(userRepository.getUserByIdCheckUserExists(any())).thenReturn(user);

        var result = userConfirmationService.GetConfirmToken(token);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "redis has expired resend token");
    }

    @Test
    public void shouldGetConfirmTokenWithErrorCacheNullAlreadyVisualizedAndConfirmEmail(){
        User user = new User(UUID.fromString("a7f1d17f-67b2-4203-8af4-1f47cf929d50"),
                null, "augustoinstagram@gmail.com", null, null, true);

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        LocalDateTime currentUtcDateTime = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime expires = currentUtcDateTime.plusMinutes(10);
        Date expiresDate = Date.from(expires.toInstant(ZoneOffset.UTC));

        String token = JWT.create()
                .withClaim("id", user.getId().toString())
                .withExpiresAt(expiresDate)
                .sign(algorithm);

        when(cacheRedisUti.getString(any())).thenReturn(null);
        when(userRepository.getUserByIdCheckUserExists(any())).thenReturn(user);

        var result = userConfirmationService.GetConfirmToken(token);

        assertTrue(result.IsSuccess);
        assertEquals(result.Data.getTokenAlreadyVisualized(), 1);
        assertEquals(result.Data.getErroMessage(), "already viewed");
    }

    @Test
    public void shouldGetConfirmTokenWithErrorUpdateUser(){
        User user = new User(UUID.fromString("a7f1d17f-67b2-4203-8af4-1f47cf929d50"),
                null, "augustoinstagram@gmail.com", null, null, true);

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        LocalDateTime currentUtcDateTime = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime expires = currentUtcDateTime.plusMinutes(10);
        Date expiresDate = Date.from(expires.toInstant(ZoneOffset.UTC));

        String token = JWT.create()
                .withClaim("id", user.getId().toString())
                .withExpiresAt(expiresDate)
                .sign(algorithm);

        when(cacheRedisUti.getString(any())).thenReturn("redisRandom");
        when(userRepository.getUserByIdCheckUserExists(any())).thenReturn(user);
        when(userRepository.update(any())).thenReturn(null);

        var result = userConfirmationService.GetConfirmToken(token);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error updating user in database");
    }
}
