package com.backend.ingresso.dataTest.repositories;

import com.backend.ingresso.data.context.UserRepositoryJPA;
import com.backend.ingresso.data.repositories.UserRepository;
import com.backend.ingresso.domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {

    private UserRepository userRepository;

    @Mock
    private UserRepositoryJPA userRepositoryJPA;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userRepository = new UserRepository(userRepositoryJPA);
    }

    @Test
    public void test_CheckUser_Exists() {
        String email = "test@example.com";
        String cpf = "12345678901";
        String queryResult = "ab088a19-95f7-4528-b31f-cfe0570300c6,augusto"; // Simulando o resultado da query

        when(userRepositoryJPA.checkUserExits(email, cpf)).thenReturn(queryResult);

        User user = userRepository.checkUserExits(email, cpf);

        assertEquals(user.getId().toString(), "ab088a19-95f7-4528-b31f-cfe0570300c6");
        assertEquals(user.getName(), "augusto");
    }

    @Test
    public void test_CheckUserNotExists_Null() {
        String email = "test@example.com";
        String cpf = "12345678901";
        String queryResult = "ab088a19-95f7-4528-b31f-cfe0570300c6,augusto"; // Simulando o resultado da query

        when(userRepositoryJPA.checkUserExits(email, cpf)).thenReturn(null);

        User user = userRepository.checkUserExits(email, cpf);

        assertNull(user);
    }

    @Test
    public void test_GetUserByEmail_Successfully() {
        String email = "test@example.com";
        String queryResult = "ab088a19-95f7-4528-b31f-cfe0570300c6," +
                "augusto,augusto@gmail.com,345.246.321-19,a@!sc#asc$asc%a#sca%sca"; // Simulando o resultado da query

        when(userRepositoryJPA.getUserByEmail(email)).thenReturn(queryResult);

        User user = userRepository.getUserByEmail(email);

        assertEquals(user.getId().toString(), "ab088a19-95f7-4528-b31f-cfe0570300c6");
        assertEquals(user.getName(), "augusto");
        assertEquals(user.getEmail(), "augusto@gmail.com");
        assertEquals(user.getCpf(), "345.246.321-19");
        assertEquals(user.getPasswordHash(), "a@!sc#asc$asc%a#sca%sca");
    }

    @Test
    public void test_GetUserByEmail_ReturnNull() {
        String email = "test@example.com";

        when(userRepositoryJPA.getUserByEmail(email)).thenReturn(null);

        User user = userRepository.getUserByEmail(email);

        assertNull(user);
    }

    @Test
    public void test_GetUserByCpf_Successfully() {
        String cpf = "345.246.321-19";
        String queryResult = "ab088a19-95f7-4528-b31f-cfe0570300c6," +
                "augusto,augusto@gmail.com,345.246.321-19,a@!sc#asc$asc%a#sca%sca"; // Simulando o resultado da query

        when(userRepositoryJPA.getUserByEmail(cpf)).thenReturn(queryResult);

        User user = userRepository.getUserByEmail(cpf);

        assertEquals(user.getId().toString(), "ab088a19-95f7-4528-b31f-cfe0570300c6");
        assertEquals(user.getName(), "augusto");
        assertEquals(user.getEmail(), "augusto@gmail.com");
        assertEquals(user.getCpf(), "345.246.321-19");
        assertEquals(user.getPasswordHash(), "a@!sc#asc$asc%a#sca%sca");
    }

    @Test
    public void test_GetUserByCpf_ReturnNull() {
        String cpf = "345.246.321-19";

        when(userRepositoryJPA.getUserByEmail(cpf)).thenReturn(null);

        User user = userRepository.getUserByEmail(cpf);

        assertNull(user);
    }

    @Test
    public void test_GetByIdOnlyEmailOrCpfId_Successfully() {
        UUID guidId = UUID.fromString("ab088a19-95f7-4528-b31f-cfe0570300c6");
        String queryResult = "ab088a19-95f7-4528-b31f-cfe0570300c6," +
                "augusto@gmail.com,345.246.321-19"; // Simulando o resultado da query

        when(userRepositoryJPA.getByIdOnlyEmailOrCpfId(guidId)).thenReturn(queryResult);

        User user = userRepository.getByIdOnlyEmailOrCpfId(guidId);

        assertEquals(user.getId().toString(), "ab088a19-95f7-4528-b31f-cfe0570300c6");
        assertEquals(user.getEmail(), "augusto@gmail.com");
        assertEquals(user.getCpf(), "345.246.321-19");
    }

    @Test
    public void test_GetByIdOnlyEmailOrCpfId_ReturnNull() {
        UUID guidId = UUID.fromString("ab088a19-95f7-4528-b31f-cfe0570300c6");

        when(userRepositoryJPA.getByIdOnlyEmailOrCpfId(guidId)).thenReturn(null);

        User user = userRepository.getByIdOnlyEmailOrCpfId(guidId);

        assertNull(user);
    }

    @Test
    public void test_GetUserByIdInfoEmailPasswordHash_Successfully() {
        UUID guidId = UUID.fromString("ab088a19-95f7-4528-b31f-cfe0570300c6");
        String queryResult = "augusto@gmail.com,a@!sc#asc$asc%a#sca%sca";

        when(userRepositoryJPA.getUserByIdInfoEmailPasswordHash(guidId)).thenReturn(queryResult);

        User user = userRepository.getUserByIdInfoEmailPasswordHash(guidId);

        assertEquals(user.getEmail(), "augusto@gmail.com");
        assertEquals(user.getPasswordHash(), "a@!sc#asc$asc%a#sca%sca");
    }

    @Test
    public void test_GetUserByIdInfoEmailPasswordHash_ReturnNull() {
        UUID guidId = UUID.fromString("ab088a19-95f7-4528-b31f-cfe0570300c6");

        when(userRepositoryJPA.getUserByIdInfoEmailPasswordHash(guidId)).thenReturn(null);

        User user = userRepository.getUserByIdInfoEmailPasswordHash(guidId);

        assertNull(user);
    }

    @Test
    public void test_GetUserByIdCheckUserExists_Successfully() {
        UUID guidId = UUID.fromString("ab088a19-95f7-4528-b31f-cfe0570300c6");
        String queryResult = "ab088a19-95f7-4528-b31f-cfe0570300c6," +
                "true";

        when(userRepositoryJPA.getUserByIdCheckUserExists(guidId)).thenReturn(queryResult);

        User user = userRepository.getUserByIdCheckUserExists(guidId);

        assertEquals(user.getId().toString(), "ab088a19-95f7-4528-b31f-cfe0570300c6");
        assertTrue(user.getConfirmEmail());
    }

    @Test
    public void test_GetUserByIdCheckUserExists_ReturnNull() {
        UUID guidId = UUID.fromString("ab088a19-95f7-4528-b31f-cfe0570300c6");

        when(userRepositoryJPA.getUserByIdCheckUserExists(guidId)).thenReturn(null);

        User user = userRepository.getUserByIdCheckUserExists(guidId);

        assertNull(user);
    }

    @Test
    public void test_GetUserByEmailInfoEmailPasswordHash_Successfully() {
        String email = "test@example.com";
        String queryResult = "ab088a19-95f7-4528-b31f-cfe0570300c6," +
                "augusto@gmail.com,a@!sc#asc$asc%a#sca%sca";

        when(userRepositoryJPA.getUserByEmailInfoEmailPasswordHash(email)).thenReturn(queryResult);

        User user = userRepository.getUserByEmailInfoEmailPasswordHash(email);

        assertEquals(user.getId().toString(), "ab088a19-95f7-4528-b31f-cfe0570300c6");
        assertEquals(user.getEmail(), "augusto@gmail.com");
        assertEquals(user.getPasswordHash(), "a@!sc#asc$asc%a#sca%sca");
    }

    @Test
    public void test_GetUserByEmailInfoEmailPasswordHash_ReturnNull() {
        String email = "test@example.com";

        when(userRepositoryJPA.getUserByEmailInfoEmailPasswordHash(email)).thenReturn(null);

        User user = userRepository.getUserByEmailInfoEmailPasswordHash(email);

        assertNull(user);
    }

    @Test
    public void test_getByUserIdOnlyPasswordHash_Successfully() {
        UUID guidId = UUID.fromString("ab088a19-95f7-4528-b31f-cfe0570300c6");
        String queryResult = "a@!sc#asc$asc%a#sca%sca";

        when(userRepositoryJPA.getByUserIdOnlyPasswordHash(guidId)).thenReturn(queryResult);

        User user = userRepository.getByUserIdOnlyPasswordHash(guidId);

        assertEquals(user.getPasswordHash(), "a@!sc#asc$asc%a#sca%sca");
    }

    @Test
    public void test_getByUserIdOnlyPasswordHash_ReturnNull() {
        UUID guidId = UUID.fromString("ab088a19-95f7-4528-b31f-cfe0570300c6");

        when(userRepositoryJPA.getByUserIdOnlyPasswordHash(guidId)).thenReturn(null);

        User user = userRepository.getByUserIdOnlyPasswordHash(guidId);

        assertNull(user);
    }

    @Test
    public void test_Create_Successfully() {
        User user = new User(UUID.fromString("67a24a7b-e5f3-46af-94ac-4eaea60fef23"),
                "augusto", "augusto@gmail.com",null,
                null, null);

        when(userRepositoryJPA.save(user)).thenReturn(user);

        User userCreate = userRepository.create(user);

        assertEquals(userCreate.getId().toString(), "67a24a7b-e5f3-46af-94ac-4eaea60fef23");
        assertEquals(userCreate.getName(), "augusto");
        assertEquals(userCreate.getEmail(), "augusto@gmail.com");
    }

    @Test
    public void test_Create_ReturnNull() {
        when(userRepositoryJPA.save(any())).thenReturn(null);

        User userCreate = userRepository.create(null);

        assertNull(userCreate);
    }

    @Test
    public void test_Update_Successfully() {
        User user = new User(UUID.fromString("67a24a7b-e5f3-46af-94ac-4eaea60fef23"),
                "augusto", "augusto@gmail.com",null,
                null, null);

        User userUpdate = new User(null,"augusto", "augusto@gmail.com",
                null, "s#d$vsdvs%dv$#sdascas@#*", null);

        when(userRepositoryJPA.findById(any())).thenReturn(Optional.of(userUpdate));
        when(userRepositoryJPA.save(any())).thenReturn(userUpdate);

        User userCreate = userRepository.update(user);

        assertNotNull(userCreate);
    }

    @Test
    public void test_Update_ReturnNullUser() {
        User userCreate = userRepository.update(null);

        assertNull(userCreate);
    }

    @Test
    public void test_Update_ReturnNullFindById() {
        User user = new User(UUID.fromString("67a24a7b-e5f3-46af-94ac-4eaea60fef23"),
                "augusto", "augusto@gmail.com",null,
                null, null);

        when(userRepositoryJPA.findById(any())).thenReturn(Optional.empty());

        User userCreate = userRepository.update(user);

        assertNull(userCreate);
    }
}
