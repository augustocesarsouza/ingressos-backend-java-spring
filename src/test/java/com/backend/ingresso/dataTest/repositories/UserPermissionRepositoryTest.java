package com.backend.ingresso.dataTest.repositories;

import com.backend.ingresso.application.dto.UserPermissionDTO;
import com.backend.ingresso.data.context.UserPermissionRepositoryJPA;
import com.backend.ingresso.data.repositories.UserPermissionRepository;
import com.backend.ingresso.domain.entities.UserPermission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserPermissionRepositoryTest {
    private UserPermissionRepository userPermissionRepository;

    @Mock
    private UserPermissionRepositoryJPA userPermissionRepositoryJPA;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userPermissionRepository = new UserPermissionRepository(userPermissionRepositoryJPA);
    }

    @Test
    public void test_getAllPermissionUser_Exists() {
        when(userPermissionRepositoryJPA.getAllPermissionUser(any()))
                .thenReturn(new ArrayList<>());

        List<UserPermissionDTO> userPermissionList = userPermissionRepository.getAllPermissionUser(UUID.fromString("1f475e74-1a23-49a1-87e4-24eef0e4f8ab"));

        assertNotNull(userPermissionList);
    }

    @Test
    public void test_getAllPermissionUser_ReturnNull() {
        when(userPermissionRepositoryJPA.getAllPermissionUser(any()))
                .thenReturn(null);

        List<UserPermissionDTO> userPermissionList = userPermissionRepository.getAllPermissionUser(UUID.fromString("1f475e74-1a23-49a1-87e4-24eef0e4f8ab"));

        assertNull(userPermissionList);
    }
}
