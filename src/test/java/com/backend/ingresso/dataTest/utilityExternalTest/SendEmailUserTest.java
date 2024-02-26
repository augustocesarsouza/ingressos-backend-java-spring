package com.backend.ingresso.dataTest.utilityExternalTest;

import com.backend.ingresso.data.utilityExternal.Interface.ICacheRedisUti;
import com.backend.ingresso.data.utilityExternal.Interface.ISendEmailBrevo;
import com.backend.ingresso.data.utilityExternal.SendEmailUser;
import com.backend.ingresso.domain.InfoErrors.InfoErrors;
import com.backend.ingresso.domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class SendEmailUserTest {
    @Mock
    private ISendEmailBrevo sendEmailBrevo;
    @Mock
    private ICacheRedisUti cacheRedisUti;
    private SendEmailUser sendEmailUser;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        this.sendEmailUser = new SendEmailUser(sendEmailBrevo, cacheRedisUti);

        ReflectionTestUtils.setField(sendEmailUser, "secretKey", "secretKey123456");
    }

    //sendEmail//
    @Test
    public void shouldSendEmailWithoutErrors(){
        User user = new User(UUID.fromString("8891dbe5-fe80-48ad-a86e-65a98c2b57d7"),
                null, null, null, null, null);

        when(cacheRedisUti.getString(any())).thenReturn("cache123");
        when(sendEmailBrevo.sendEmail(any(), any())).thenReturn(InfoErrors.Ok("all very well"));

        // Act
        var result = sendEmailUser.sendEmail(user);

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(result.Message, "tudo certo com o envio do email");
    }

    @Test
    public void shouldSendEmailErrorNullPointerException(){
        User user = new User(null,
                null, null, null, null, null);

        var result = sendEmailUser.sendEmail(user);

        assertFalse(result.IsSuccess);
    }

    @Test
    public void shouldSendEmailError(){
        User user = new User(UUID.fromString("8891dbe5-fe80-48ad-a86e-65a98c2b57d7"),
                null, null, null, null, null);

        when(cacheRedisUti.getString(any())).thenReturn("cache123");
        when(sendEmailBrevo.sendEmail(any(), any())).thenReturn(InfoErrors.Fail("error sending email to user"));

        var result = sendEmailUser.sendEmail(user);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error sending email to user");
    }
}
