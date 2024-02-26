package com.backend.ingresso.application.services.interfaces;

import com.backend.ingresso.application.dto.UserPermissionDTO;
import com.backend.ingresso.application.services.ResultService;

import java.util.List;
import java.util.UUID;

public interface IUserPermissionService {
    ResultService<List<UserPermissionDTO>> getAllPermissionUser(UUID idUser);
}
