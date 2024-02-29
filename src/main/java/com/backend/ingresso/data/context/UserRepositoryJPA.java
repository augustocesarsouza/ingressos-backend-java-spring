package com.backend.ingresso.data.context;

import com.backend.ingresso.application.dto.UserDTO;
import com.backend.ingresso.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepositoryJPA extends JpaRepository<User, UUID> {
    // você também pode adicionar métodos de consulta personalizados aqui, se necessário

    // quando criar @Query criar olhando para entidade da class
    // que representa a tabela no banco
    @Query("SELECT u.Id, u.Name FROM User AS u WHERE u.Email = :email AND u.Cpf = :cpf")
    String checkUserExits(String email, String cpf);

    @Query("SELECT u.Id, u.Name, u.Email, u.Cpf, u.PasswordHash FROM User AS u WHERE u.Email = :email")
    String getUserByEmail(String email);

//    @Query("SELECT new com.backend.ingresso.application.dto.UserDTO" +
//            "(u.Id, u.Name, u.Email, u.Cpf, u.PasswordHash) " +
//            "FROM User AS u WHERE u.Email = :email")
//    UserDTO getUserByEmailObj(String email);

    @Query("SELECT u.Id, u.Name, u.Email, u.Cpf, u.PasswordHash FROM User AS u WHERE u.Cpf = :cpf")
    String GetUserByCpf(String cpf);

    @Query("SELECT u.Id, u.Email, u.Cpf FROM User AS u WHERE u.Id = :userId")
    String getByIdOnlyEmailOrCpfId(UUID userId);

    @Query("SELECT u.Email, u.PasswordHash FROM User AS u WHERE u.Id = :userId")
    String getUserByIdInfoEmailPasswordHash(UUID userId);

    @Query("SELECT u.Id, u.ConfirmEmail FROM User AS u WHERE u.Id = :userId")
    String getUserByIdCheckUserExists(UUID userId);

    @Query("SELECT u.Id, u.Email, u.PasswordHash FROM User AS u WHERE u.Email = :email")
    String getUserByEmailInfoEmailPasswordHash(String email);

    @Query("SELECT u.PasswordHash FROM User AS u WHERE u.Id = :userId")
    String getByUserIdOnlyPasswordHash(UUID userId);
}
