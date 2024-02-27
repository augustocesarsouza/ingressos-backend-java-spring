package com.backend.ingresso.dataTest.repositories;

import com.backend.ingresso.data.context.UserPermissionRepositoryJPA;
import com.backend.ingresso.data.context.UserRepositoryJPA;
import com.backend.ingresso.data.repositories.UserPermissionRepository;
import com.backend.ingresso.data.repositories.UserRepository;
import com.backend.ingresso.domain.entities.User;
import com.backend.ingresso.domain.entities.UserPermission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
        List<Object[]> listObj = new ArrayList<>();

        Object[] firstObj = new Object[]{UUID.fromString("ab088a19-95f7-4528-b31f-cfe0570300c6"),
        UUID.fromString("1f475e74-1a23-49a1-87e4-24eef0e4f8ab"), "administrator", "admin"};
        listObj.add(firstObj);

        Object[] firstObj2 = new Object[]{UUID.fromString("8cd81843-d1a4-4b3c-8cd9-d6a6c977ceef"),
                UUID.fromString("19614d54-9f44-43c0-b92f-ea477f97a484"), "moderador", "mod"};
        listObj.add(firstObj2);

        when(userPermissionRepositoryJPA.getAllPermissionUser(any()))
                .thenReturn(listObj);

        List<UserPermission> userPermissionList = userPermissionRepository.getAllPermissionUser(UUID.fromString("1f475e74-1a23-49a1-87e4-24eef0e4f8ab"));

        UserPermission userPermission1 = userPermissionList.get(0);
        UserPermission userPermission2 = userPermissionList.get(1);

        assertEquals(userPermission1.getId(), firstObj[0]);
        assertEquals(userPermission1.getUserId(), firstObj[1]);
        assertEquals(userPermission1.getPermission().getVisualName(), firstObj[2]);
        assertEquals(userPermission1.getPermission().getPermissionName(), firstObj[3]);

        assertEquals(userPermission2.getId(), firstObj2[0]);
        assertEquals(userPermission2.getUserId(), firstObj2[1]);
        assertEquals(userPermission2.getPermission().getVisualName(), firstObj2[2]);
        assertEquals(userPermission2.getPermission().getPermissionName(), firstObj2[3]);
    }

    @Test
    public void test_getAllPermissionUser_ReturnNull() {
        when(userPermissionRepositoryJPA.getAllPermissionUser(any()))
                .thenReturn(null);

        List<UserPermission> userPermissionList = userPermissionRepository.getAllPermissionUser(UUID.fromString("1f475e74-1a23-49a1-87e4-24eef0e4f8ab"));

        assertNull(userPermissionList);
    }
}
