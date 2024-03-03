package com.backend.ingresso.domain.repositories;

import com.backend.ingresso.application.dto.UserPermissionDTO;
import com.backend.ingresso.domain.entities.UserPermission;

import java.util.List;
import java.util.UUID;

public interface IUserPermissionRepository {
    List<UserPermissionDTO> getAllPermissionUser(UUID idUser);
}
