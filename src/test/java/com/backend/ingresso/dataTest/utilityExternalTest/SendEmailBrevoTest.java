package com.backend.ingresso.dataTest.utilityExternalTest;

import com.backend.ingresso.data.utilityExternal.SendEmailBrevo;
import com.backend.ingresso.domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import resources.ConfigurationProperties;

import static org.junit.jupiter.api.Assertions.*;

public class SendEmailBrevoTest {
    private SendEmailBrevo sendEmailBrevo;
    @BeforeEach
    public void setup(){
        this.sendEmailBrevo = new SendEmailBrevo();
    }
    //sendEmail//
    @Disabled("disable when you need to test again")
    @Test
    public void shouldSendEmailWithoutErrors(){
        User user = new User(null, "augusto", "augustotesteinstagram@gmail.com",
                null, null, null);
        ReflectionTestUtils.setField(sendEmailBrevo, "brevoSecretKey", ConfigurationProperties.brevoSecretKey);

        var result = sendEmailBrevo.sendEmail(user, "seilarurlseilarurlseilarurl");

        assertTrue(result.IsSuccess);
        assertEquals(result.Message, "Everything ok with sending the email");
    }

    @Test
    public void shouldSendEmailWithErrorKeyInvalid(){
        User user = new User(null, "augusto", "augustotesteinstagram@gmail.com",
                null, null, null);
        ReflectionTestUtils.setField(sendEmailBrevo, "brevoSecretKey", "wrongKey");

        var result = sendEmailBrevo.sendEmail(user, "seilarurlseilarurlseilarurl");

        assertFalse(result.IsSuccess);
    }
}
