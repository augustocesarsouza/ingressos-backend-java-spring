package com.backend.ingresso.data.repositories;

import com.backend.ingresso.application.dto.UserPermissionDTO;
import com.backend.ingresso.data.context.UserPermissionRepositoryJPA;
import com.backend.ingresso.domain.entities.Permission;
import com.backend.ingresso.domain.entities.UserPermission;
import com.backend.ingresso.domain.repositories.IUserPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserPermissionRepository implements IUserPermissionRepository {
    private final UserPermissionRepositoryJPA userPermissionRepositoryJPA;

    @Autowired
    public UserPermissionRepository(UserPermissionRepositoryJPA userPermissionRepositoryJPA) {
        this.userPermissionRepositoryJPA = userPermissionRepositoryJPA;
    }
    @Override
    public List<UserPermissionDTO> getAllPermissionUser(UUID idUser) {
        return userPermissionRepositoryJPA.getAllPermissionUser(idUser);
    }
}
