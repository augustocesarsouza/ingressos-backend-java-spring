package com.backend.ingresso.domain.entities;

import com.backend.ingresso.domain.validations.DomainValidationException;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_users", schema = "public")
public class User implements UserDetails {
    @Id
    @Column(name = "user_id")
    @JsonProperty("id")
    private UUID Id;
    @Column(name = "name")
    @JsonProperty("name")
    private String Name;
    @Column(name = "email")
    @JsonProperty("email")
    private String Email;
    @Column(name = "cpf")
    @JsonProperty("cpf")
    private String Cpf;
    @Column(name = "password_hash")
    @JsonProperty("passwordHash")
    private String PasswordHash;
    @Column(name = "confirm_email")
    @JsonProperty("confirmEmail")
    private Boolean ConfirmEmail; // se 0 false, 1 true

    public User(UUID id, String name, String email, String cpf, String passwordHash, Boolean confirmEmail) {
        Id = id;
        Name = name;
        Email = email;
        Cpf = cpf;
        PasswordHash = passwordHash;
        ConfirmEmail = confirmEmail;
    }

    public User(UUID id, String name, String email, String cpf, String passwordHash) {
        Id = id;
        Name = name;
        Email = email;
        Cpf = cpf;
        PasswordHash = passwordHash;
    }

    //    u.Id, u.Name, u.Email, u.Cpf, u.PasswordHash

    public User() {
    }

    public UUID getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getCpf() {
        return Cpf;
    }

    public String getPasswordHash() {
        return PasswordHash;
    }

    public Boolean getConfirmEmail() {
        return ConfirmEmail;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setCpf(String cpf) {
        Cpf = cpf;
    }

    public void confirmEmail(boolean confirmEmail){
        ConfirmEmail = confirmEmail;
    }

    public void changeNameUser(String name) throws DomainValidationException {
        DomainValidationException.when(name == null, "name null");
        DomainValidationException.when(name.isEmpty(), "name user deve ser maior que zero");
        Name = name;
    }

    public void changePasswordHash(String passwordHash) throws DomainValidationException {
        DomainValidationException.when(passwordHash == null, "name null");
        DomainValidationException.when(passwordHash.isEmpty(), "passwordHash user deve ser maior que zero");
        PasswordHash = passwordHash;
    }

    public void addData(UUID id, String name, String email, String cpf, String passwordHash, Boolean confirmEmail){
        if(id != null){
            Id = id;
        }

        if(name != null){
            Name = name;
        }

        if(email != null){
            Email = email;
        }

        if(cpf != null){
            Cpf = cpf;
        }

        if(passwordHash != null){
            PasswordHash = passwordHash;
        }

        if(confirmEmail != null){
            ConfirmEmail = confirmEmail;
        }
    }

//    public void validateToken(String token) throws DomainValidationException {
//        DomainValidationException.when(token.isEmpty(), "Token was not generated successfully");
//        Token = token;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return getPasswordHash();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }
    // ver essas opção de baixo
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
