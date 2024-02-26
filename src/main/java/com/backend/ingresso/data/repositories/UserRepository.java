package com.backend.ingresso.data.repositories;

import com.backend.ingresso.data.context.UserRepositoryJPA;
import com.backend.ingresso.domain.entities.User;
import com.backend.ingresso.domain.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        String query = userRepositoryJPA.checkUserExits(email, cpf);

        if(query == null){
            return null;
        }
        // @Query("SELECT u.Id, u.Name FROM User AS u WHERE u.Email = :email AND u.Cpf = :cpf")
        String[] stringSplit = query.split(",");
        return new User(UUID.fromString(stringSplit[0]), stringSplit[1], null, null, null, null);
    }

    @Override
    public User getUserByEmail(String email) {
        String query = userRepositoryJPA.getUserByEmail(email);

        if(query == null){
            return null;
        }

        String[] stringSplit = query.split(",");
        return new User(UUID.fromString(stringSplit[0]), stringSplit[1], stringSplit[2], stringSplit[3], stringSplit[4], null);
    }

    @Override
    public User getUserByCpf(String cpf) {
        String query = userRepositoryJPA.GetUserByCpf(cpf);

        if(query == null){
            return null;
        }

        String[] stringSplit = query.split(",");
        return new User(UUID.fromString(stringSplit[0]), stringSplit[1], stringSplit[2], stringSplit[3], stringSplit[4], null);
    }

    @Override
    public User getByIdOnlyEmailOrCpfId(UUID guidId) {
        String query = userRepositoryJPA.getByIdOnlyEmailOrCpfId(guidId);

        if(query == null){
            return null;
        }

        String[] stringSplit = query.split(",");
        return new User(UUID.fromString(stringSplit[0]), null, stringSplit[1], stringSplit[2], null, null);
    }

    @Override
    public User getUserByIdInfoEmailPasswordHash(UUID guidId) {
        String query = userRepositoryJPA.getUserByIdInfoEmailPasswordHash(guidId);

        if(query == null){
            return null;
        }

        String[] stringSplit = query.split(",");
        return new User(null, null, stringSplit[0], null, stringSplit[1], null);
    }

    @Override
    public User getUserByIdCheckUserExists(UUID guidId) {
        String query = userRepositoryJPA.getUserByIdCheckUserExists(guidId);

        if(query == null){
            return null;
        }

        String[] stringSplit = query.split(",");
        return new User(UUID.fromString(stringSplit[0]), null, null, null, null, Boolean.parseBoolean(stringSplit[1]));
    }

    @Override
    public User getUserByEmailInfoEmailPasswordHash(String email) {
        String query = userRepositoryJPA.getUserByEmailInfoEmailPasswordHash(email);

        if(query == null){
            return null;
        }

        String[] stringSplit = query.split(",");
        return new User(UUID.fromString(stringSplit[0]), null, stringSplit[1], null, stringSplit[2], null);
    }


    @Override
    public User getByUserIdOnlyPasswordHash(UUID userId) {
        String query = userRepositoryJPA.getByUserIdOnlyPasswordHash(userId);

        if(query == null){
            return null;
        }

        User user =  new User(null, null, null, null, query, null);
        return user;
    }

    @Override
    public User create(User user) {
        return userRepositoryJPA.save(user);
    }

    @Override
    public User update(User user) {
        User userToUpdate = userRepositoryJPA.findById(user.getId()).orElse(null);

        if(userToUpdate == null)
            return null;

        userToUpdate.addData(user.getId(), user.getName(), user.getEmail(), user.getCpf(),user.getPasswordHash(), user.getConfirmEmail());

        return userRepositoryJPA.save(userToUpdate);
    }
}
