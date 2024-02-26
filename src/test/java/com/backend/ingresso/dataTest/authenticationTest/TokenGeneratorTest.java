package com.backend.ingresso.dataTest.authenticationTest;

import com.backend.ingresso.data.authentication.TokenGenerator;
import com.backend.ingresso.domain.Authentication.TokenOutValue;
import com.backend.ingresso.domain.InfoErrors.InfoErrors;
import com.backend.ingresso.domain.entities.Permission;
import com.backend.ingresso.domain.entities.User;
import com.backend.ingresso.domain.entities.UserPermission;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TokenGeneratorTest {
    private final TokenGenerator tokenGenerator;

    public TokenGeneratorTest() {
        this.tokenGenerator = new TokenGenerator();

        ReflectionTestUtils.setField(tokenGenerator, "secretKey", "secretKey123456");
    }

    //GeneratorByEmail//
    @Test
    public void shouldGeneratorTokenByEmail(){
        User user = new User(UUID.fromString("8891dbe5-fe80-48ad-a86e-65a98c2b57d7"),
                null, null, null, null, null);
        List<UserPermission> listUserPermission = new ArrayList<>();

        Permission permission1 = new Permission(null, null,
                "admin");
        Permission permission2 = new Permission(null, null,
                "mod");
        UserPermission userPermission1 = new UserPermission(null, null, null,
                permission1);
        UserPermission userPermission2 = new UserPermission(null, null, null,
                permission1);
        listUserPermission.add(userPermission1);
        listUserPermission.add(userPermission2);

        InfoErrors<TokenOutValue> resultToken = tokenGenerator.generatorByEmail
                (user, listUserPermission, "Email");

        assertTrue(resultToken.IsSuccess);
    }

    @Test
    public void shouldGeneratorErrorUserNull(){
        List<UserPermission> listUserPermission = new ArrayList<>();

        Permission permission1 = new Permission(null, null,
                "admin");
        UserPermission userPermission1 = new UserPermission(null, null, null,
                permission1);
        listUserPermission.add(userPermission1);

        InfoErrors<TokenOutValue> resultToken = tokenGenerator.generatorByEmail
                (null, listUserPermission, "Email");

        assertFalse(resultToken.IsSuccess);
    }
}
