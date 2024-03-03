package com.backend.ingresso.data.context;

import com.backend.ingresso.application.dto.UserDTO;
import com.backend.ingresso.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepositoryJPA extends JpaRepository<User, UUID> {
    //User(UUID id, String name, String email, String cpf, String passwordHash, Boolean confirmEmail)
    //User(u.Id, u.Name, u.Email, u.Cpf, u.PasswordHash, u.ConfirmEmail)

    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "User(u.Id, u.Name, null, null, null, null) " +
            "FROM User AS u " +
            "WHERE u.Email = :email AND u.Cpf = :cpf")
    User checkUserExits(String email, String cpf);

    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "User(u.Id, u.Name, u.Email, u.Cpf, u.PasswordHash, null) " +
            "FROM User AS u " +
            "WHERE u.Email = :email")
    User getUserByEmail(String email);

    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "User(u.Id, u.Name, u.Email, u.Cpf, u.PasswordHash, null) " +
            "FROM User AS u " +
            "WHERE u.Cpf = :cpf")
    User GetUserByCpf(String cpf);

    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "User(u.Id, null, u.Email, u.Cpf, null, null) " +
            "FROM User AS u " +
            "WHERE u.Id = :userId")
    User getByIdOnlyEmailOrCpfId(UUID userId);

    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "User(null, null, u.Email, null, u.PasswordHash, null) " +
            "FROM User AS u " +
            "WHERE u.Id = :userId")
    User getUserByIdInfoEmailPasswordHash(UUID userId);

    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "User(u.Id, null, null, null, null, u.ConfirmEmail) " +
            "FROM User AS u " +
            "WHERE u.Id = :userId")
    User getUserByIdCheckUserExists(UUID userId);

    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "User(u.Id, null, u.Email, null, u.PasswordHash, null) " +
            "FROM User AS u " +
            "WHERE u.Email = :email")
    User getUserByEmailInfoEmailPasswordHash(String email);

    //u.PasswordHash
    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "User(null, null, null, null, u.PasswordHash, null) " +
            "FROM User AS u " +
            "WHERE u.Id = :userId")
    User getByUserIdOnlyPasswordHash(UUID userId);
}
