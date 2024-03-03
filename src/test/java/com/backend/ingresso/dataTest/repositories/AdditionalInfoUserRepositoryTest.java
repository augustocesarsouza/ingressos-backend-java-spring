package com.backend.ingresso.dataTest.repositories;

import com.backend.ingresso.data.context.AdditionalInfoUserRepositoryJPA;
import com.backend.ingresso.data.repositories.AdditionalInfoUserRepository;
import com.backend.ingresso.data.repositories.UserPermissionRepository;
import com.backend.ingresso.domain.entities.AdditionalInfoUser;
import com.backend.ingresso.domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AdditionalInfoUserRepositoryTest {
    private AdditionalInfoUserRepository userPermissionRepository;

    @Mock
    private AdditionalInfoUserRepositoryJPA additionalInfoUserRepositoryJPA;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userPermissionRepository = new AdditionalInfoUserRepository(additionalInfoUserRepositoryJPA);
    }

    @Test
    public void test_GetInfoUser_Exists() throws ParseException {
        String idGuid = "7ecc1a4a-950d-4555-8c4d-ac879f072c53";

        when(additionalInfoUserRepositoryJPA.getInfoUser(any())).thenReturn(new AdditionalInfoUser());

        AdditionalInfoUser additionalInfoUser = userPermissionRepository.getInfoUser(UUID.fromString(idGuid));

        assertNotNull(additionalInfoUser);
    }

    @Test
    public void test_GetInfoUser_ReturnNull_Query() throws ParseException {
        String idGuid = "7ecc1a4a-950d-4555-8c4d-ac879f072c53";

        when(additionalInfoUserRepositoryJPA.getInfoUser(any())).thenReturn(null);

        AdditionalInfoUser additionalInfoUser = userPermissionRepository.getInfoUser(UUID.fromString(idGuid));

        assertNull(additionalInfoUser);
    }

    @Test
    public void test_GetByIdGuidUser_Exists() {
        when(additionalInfoUserRepositoryJPA.getByIdGuidUser(any())).thenReturn(new AdditionalInfoUser());

        AdditionalInfoUser additionalInfoUser = userPermissionRepository.getByIdGuidUser(UUID.fromString("7ecc1a4a-950d-4555-8c4d-ac879f072c53"));

        assertNotNull(additionalInfoUser);
    }

    @Test
    public void test_GetByIdGuidUser_ReturnNull_Query() {
        when(additionalInfoUserRepositoryJPA.getByIdGuidUser(any())).thenReturn(null);

        AdditionalInfoUser additionalInfoUser = userPermissionRepository.getByIdGuidUser(UUID.fromString("7ecc1a4a-950d-4555-8c4d-ac879f072c53"));

        assertNull(additionalInfoUser);
    }

    @Test
    public void test_Create_Successfully() {
        AdditionalInfoUser additionalInfoUser = new AdditionalInfoUser(null, null, null,
                "Masculino", null,null, "Rua Cajazeira", null, null,
                null,null,null, null);

        when(additionalInfoUserRepositoryJPA.save(any())).thenReturn(additionalInfoUser);

        AdditionalInfoUser userCreate = userPermissionRepository.create(additionalInfoUser);

        assertNotNull(userCreate);
        assertEquals(userCreate.getGender(), "Masculino");
        assertEquals(userCreate.getLogradouro(), "Rua Cajazeira");
    }

    @Test
    public void test_Create_ReturnNull() {
        when(additionalInfoUserRepositoryJPA.save(any())).thenReturn(null);

        AdditionalInfoUser userCreate = userPermissionRepository.create(null);

        assertNull(userCreate);
    }

    @Test
    public void test_Update_Successfully() {
        AdditionalInfoUser additionalInfoUser = new AdditionalInfoUser(null, null, null,
                "Masculino", null,null, "Rua Cajazeira", null, null,
                null,null,"MS", null);
        AdditionalInfoUser additionalInfoUserUpdate1 = new AdditionalInfoUser(null, null, null,
                "Masculino", null,null, "Rua Cajazeira", null, null,
                null,null,null, null);

        when(additionalInfoUserRepositoryJPA.findById(any())).thenReturn(Optional.of(additionalInfoUserUpdate1));
        when(additionalInfoUserRepositoryJPA.save(any())).thenReturn(additionalInfoUserUpdate1);


        AdditionalInfoUser additionalInfoUserUpdate = userPermissionRepository.update(additionalInfoUser);

        assertNotNull(additionalInfoUserUpdate);
        assertEquals(additionalInfoUserUpdate.getEstado(), "MS");
    }

    @Test
    public void test_Update_FindById_ReturnNull() {

        when(additionalInfoUserRepositoryJPA.findById(any())).thenReturn(Optional.empty());

        AdditionalInfoUser additionalInfoUserUpdate = userPermissionRepository.update(new AdditionalInfoUser());

        assertNull(additionalInfoUserUpdate);
    }
}
