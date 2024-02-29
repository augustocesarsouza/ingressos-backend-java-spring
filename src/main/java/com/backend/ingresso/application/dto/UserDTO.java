package com.backend.ingresso.application.dto;

import com.backend.ingresso.domain.validations.DomainValidationException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    @JsonProperty("id")
    private UUID Id;
    @JsonProperty("name")
    private String Name;
    @JsonProperty("email")
    private String Email;
    @JsonProperty("cpf")
    private String Cpf;
    @JsonProperty("passwordHash")
    private String PasswordHash;
    @JsonProperty("confirmEmail")
    private Boolean ConfirmEmail; // se 0 false, 1 true
    @JsonProperty("token")
    private String Token;
    @JsonProperty("codeSentSuccessfullyEmail")
    private Boolean EmailSendSuccessfully;
//    @JsonProperty("code")//só para teste
//    private int Code;

    public UserDTO(UUID id, String name, String email, String cpf, String passwordHash, Boolean confirmEmail) {
        Id = id;
        Name = name;
        Email = email;
        Cpf = cpf;
        PasswordHash = passwordHash;
        ConfirmEmail = confirmEmail;
    }

    public UserDTO(UUID id, String name, String email, String cpf, String passwordHash) {
        Id = id;
        Name = name;
        Email = email;
        Cpf = cpf;
        PasswordHash = passwordHash;
    }

    public UserDTO() {
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

    public String getToken() {
        return Token;
    }

    public void setToken(String token) throws DomainValidationException {
        DomainValidationException.when(token.isEmpty(), "Token was not generated successfully");
        Token = token;
    }
    public void setEmailSendSuccessfully(Boolean emailSendSuccessfully) {
        EmailSendSuccessfully = emailSendSuccessfully;
    }

//    public void setCode(int code) {
//        Code = code;
//    }
}
