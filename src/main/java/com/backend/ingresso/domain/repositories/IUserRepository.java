package com.backend.ingresso.domain.repositories;

import com.backend.ingresso.application.dto.UserDTO;
import com.backend.ingresso.domain.entities.User;

import java.util.UUID;

public interface IUserRepository {
    public User checkUserExits(String email, String cpf);
    public User getUserByEmail(String email);
    public User getUserByCpf(String cpf);
    public User getByIdOnlyEmailOrCpfId(UUID guidId);
    public User getUserByIdInfoEmailPasswordHash(UUID guidId);
    public User getUserByIdCheckUserExists(UUID guidId);
    public User getUserByEmailInfoEmailPasswordHash(String email);
    public User getByUserIdOnlyPasswordHash(UUID userId);
    public User create(User user);
    public User update(User user);
}
