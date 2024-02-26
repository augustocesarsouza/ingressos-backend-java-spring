package com.backend.ingresso.data.repositories;

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
    public List<UserPermission> getAllPermissionUser(UUID idUser) {
        List<Object[]> listObj = userPermissionRepositoryJPA.getAllPermissionUser(idUser);
        if(listObj == null)
            return null;

        List<UserPermission> userPermissionList = new ArrayList<>();

        listObj.forEach((obj) -> {
            Permission permission = new Permission(null, (String) obj[2], (String) obj[3]);
            UserPermission userPermission = new UserPermission((UUID) obj[0], (UUID) obj[1], null, permission);
            userPermissionList.add(userPermission);
        });

        return userPermissionList;
    }
}
