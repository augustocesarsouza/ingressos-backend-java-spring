package com.backend.ingresso.data.repositories;

import com.backend.ingresso.application.dto.UserDTO;
import com.backend.ingresso.data.context.UserRepositoryJPA;
import com.backend.ingresso.domain.entities.User;
import com.backend.ingresso.domain.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepository implements IUserRepository {
    private final UserRepositoryJPA userRepositoryJPA;

    @Autowired
    public UserRepository(UserRepositoryJPA userRepositoryJPA) {
        this.userRepositoryJPA = userRepositoryJPA;
    }
    @Override
    public User checkUserExits(String email, String cpf) {
        return userRepositoryJPA.checkUserExits(email, cpf);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepositoryJPA.getUserByEmail(email);
    }

    @Override
    public User getUserByCpf(String cpf) {
        return userRepositoryJPA.GetUserByCpf(cpf);
    }

    @Override
    public User getByIdOnlyEmailOrCpfId(UUID guidId) {
        return userRepositoryJPA.getByIdOnlyEmailOrCpfId(guidId);
        //@Query("SELECT u.Id, u.Email, u.Cpf FROM User AS u WHERE u.Id = :userId")
    }

    @Override
    public User getUserByIdInfoEmailPasswordHash(UUID guidId) {
        return userRepositoryJPA.getUserByIdInfoEmailPasswordHash(guidId);
        //@Query("SELECT u.Email, u.PasswordHash FROM User AS u WHERE u.Id = :userId")
    }

    @Override
    public User getUserByIdCheckUserExists(UUID guidId) {
        return userRepositoryJPA.getUserByIdCheckUserExists(guidId);
        //@Query("SELECT u.Id, u.ConfirmEmail FROM User AS u WHERE u.Id = :userId")
    }

    @Override
    public User getUserByEmailInfoEmailPasswordHash(String email) {
        return userRepositoryJPA.getUserByEmailInfoEmailPasswordHash(email);
        //@Query("SELECT u.Id, u.Email, u.PasswordHash FROM User AS u WHERE u.Email = :email")
    }


    @Override
    public User getByUserIdOnlyPasswordHash(UUID userId) {
        return userRepositoryJPA.getByUserIdOnlyPasswordHash(userId);
        //@Query("SELECT u.PasswordHash FROM User AS u WHERE u.Id = :userId")
    }

    @Override
    public User create(User user) {
        if(user == null)
            return null;
        return userRepositoryJPA.save(user);
    }

    @Override
    public User update(User user) {
        if(user == null)
            return null;

        User userToUpdate = userRepositoryJPA.findById(user.getId()).orElse(null);

        if(userToUpdate == null)
            return null;

        userToUpdate.addData(user.getId(), user.getName(), user.getEmail(), user.getCpf(),user.getPasswordHash(), user.getConfirmEmail());

        return userRepositoryJPA.save(userToUpdate);
    }
}
