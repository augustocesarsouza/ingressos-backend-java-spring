package com.backend.ingresso.dataTest.repositories;

import com.backend.ingresso.data.context.UserRepositoryJPA;
import com.backend.ingresso.data.repositories.UserRepository;
import com.backend.ingresso.domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    public void testCheckUserExists() {
        // Configurando o comportamento do mock
        String email = "test@example.com";
        String cpf = "12345678901";
        String queryResult = "ab088a19-95f7-4528-b31f-cfe0570300c6,augusto"; // Simulando o resultado da query

        when(userRepositoryJPA.checkUserExits(email, cpf)).thenReturn(queryResult);

        // Chamando o método a ser testado
        User user = userRepository.checkUserExits(email, cpf);

        // Verificando se o método retornou o resultado esperado
        // Aqui você precisa implementar a lógica para criar o objeto User com base no resultado da query
        // e verificar se o objeto retornado é o esperado
        // Exemplo: assertEquals(expectedUser, user);
        assertEquals(user.getId().toString(), "ab088a19-95f7-4528-b31f-cfe0570300c6");
        assertEquals(user.getName(), "augusto");
    }
}
