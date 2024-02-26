package domainTest.entitiesTest;

import com.backend.ingresso.domain.entities.User;
import com.backend.ingresso.domain.validations.DomainValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    //confirmEmail//
    @Test
    public void shouldConfirmEmailTrue(){
        User user = new User(null, "augusto", "augustotesteinstagram@gmail.com",
                null, null, null);
        user.confirmEmail(true);

        assertTrue(user.getConfirmEmail());
    }

    //changeNameUser//
    @Test
    public void shouldChangeNameUserWithoutErrors() throws DomainValidationException {
        User user = new User(null, null, null,
                null, null, null);

        user.changeNameUser("augusto");

        assertEquals(user.getName(), "augusto");
    }

    @Test
    public void shouldChangeNameUserWithExceptionNull(){
        User user = new User(null, null, null,
                null, null, null);

        assertThrows(DomainValidationException.class, () -> {
            user.changeNameUser(null);
        });
    }

    @Test
    public void shouldChangeNameUserWithExceptionNameEmpty(){
        User user = new User(null, null, null,
                null, null, null);

        assertThrows(DomainValidationException.class, () -> {
            user.changeNameUser("");
        });
    }

    //changePasswordHash//
    @Test
    public void shouldChangePasswordHashWithoutErrors() throws DomainValidationException {
        User user = new User(null, null, null,
                null, null, null);

        user.changePasswordHash("augusto11augusto11augusto");

        assertEquals(user.getPasswordHash(), "augusto11augusto11augusto");
    }

    @Test
    public void shouldChangePasswordHashWithExceptionNull() {
        User user = new User(null, null, null,
                null, null, null);

        assertThrows(DomainValidationException.class, () -> {
            user.changePasswordHash(null);
        });
    }

    @Test
    public void shouldChangePasswordHashWithExceptionEmpty() {
        User user = new User(null, null, null,
                null, null, null);

        assertThrows(DomainValidationException.class, () -> {
            user.changePasswordHash("");
        });
    }
}
