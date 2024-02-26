package com.backend.ingresso.application.dto.validations.userValidationDTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCreateValidatorDTO {
    @JsonProperty("id")
    private UUID Id;
    @NotEmpty(message = "name should not be empty")
    @Size(min = 3, message = "name should have at last 3 characters")
    @JsonProperty("name")
    private String Name;
    @NotEmpty(message = "email should not be empty")
    @Size(min = 5, message = "email should have at last 5 characters")
    @JsonProperty("email")
    private String Email;
    @CPF
    @JsonProperty("cpf")
    private String Cpf;
    @JsonProperty("passwordHash")
    private String PasswordHash;
    @NotEmpty(message = "Password should not be empty")
    @Size(min = 8, message = "Password should have at last 8 characters")
    @JsonProperty("password")
    private String Password;
    @NotEmpty(message = "birthDateString should not be empty")
    //@Size(min = 10, message = "birthDateString should have at last 10 characters")
    @JsonProperty("birthDateString")
    private String BirthDateString;
    @JsonProperty("emailSendSuccessfully")
    private Boolean EmailSendSuccessfully;
    @JsonProperty("token")
    private String Token;
    @JsonProperty("confirmEmail")
    private Boolean ConfirmEmail; // se 0 false, 1 true
    @JsonProperty("birthDate")
    private Date BirthDate;
    @JsonProperty("gender")
    private String Gender;
    @JsonProperty("phone")
    private String Phone;
    @JsonProperty("cep")
    private String Cep;
    @JsonProperty("logradouro")
    private String Logradouro;
    @JsonProperty("numero")
    private String Numero;
    @JsonProperty("complemento")
    private String Complemento;
    @JsonProperty("referencia")
    private String Referencia;
    @JsonProperty("bairro")
    private String Bairro;
    @JsonProperty("estado")
    private String Estado;
    @JsonProperty("cidade")
    private String Cidade;

    public UserCreateValidatorDTO() {
    }

    public UserCreateValidatorDTO(String name, String email,String cpf, String passwordHash, String password,
                                  Boolean confirmEmail, Date birthDate, String token, String gender,
                                  String phone, String cep, String logradouro, String numero,
                                  String complemento, String referencia, String bairro, String estado, String cidade) {
        Name = name;
        Email = email;
        Cpf = cpf;
        PasswordHash = passwordHash;
        Password = password;
        ConfirmEmail = confirmEmail;
        BirthDate = birthDate;
        Token = token;
        Gender = gender;
        Phone = phone;
        Cep = cep;
        Logradouro = logradouro;
        Numero = numero;
        Complemento = complemento;
        Referencia = referencia;
        Bairro = bairro;
        Estado = estado;
        Cidade = cidade;
    }

    public UserCreateValidatorDTO(String name, String email, String cpf, String passwordHash, String password, Boolean confirmEmail) {
        Name = name;
        Email = email;
        Cpf = cpf;
        PasswordHash = passwordHash;
        Password = password;
        ConfirmEmail = confirmEmail;
    }

    public UserCreateValidatorDTO(String name, String email, String cpf, String passwordHash, String password, Boolean confirmEmail, Boolean emailSendSuccessfully) {
        Name = name;
        Email = email;
        Cpf = cpf;
        PasswordHash = passwordHash;
        Password = password;
        ConfirmEmail = confirmEmail;
        EmailSendSuccessfully = emailSendSuccessfully;
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

    public String getPassword() {
        return Password;
    }

    public Boolean getConfirmEmail() {
        return ConfirmEmail;
    }

    public Date getBirthDate() {
        return BirthDate;
    }
    public String getToken() {
        return Token;
    }
    public String getGender() {
        return Gender;
    }

    public String getPhone() {
        return Phone;
    }

    public String getCep() {
        return Cep;
    }

    public String getLogradouro() {
        return Logradouro;
    }

    public String getNumero() {
        return Numero;
    }

    public String getComplemento() {
        return Complemento;
    }

    public String getReferencia() {
        return Referencia;
    }

    public String getBairro() {
        return Bairro;
    }

    public String getEstado() {
        return Estado;
    }

    public String getCidade() {
        return Cidade;
    }

    public String getBirthDateString() {
        return BirthDateString;
    }

    public void setIdPasswordHashConfirmEmail(UUID id, String passwordHash, Boolean confirmEmail){
        Id = id;
        PasswordHash = passwordHash;
        ConfirmEmail = confirmEmail;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setBirthDateString(String birthDateString) {
        BirthDateString = birthDateString;
    }

    public void setCpf(String cpf) {
        Cpf = cpf;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setToken(String token) {
        Token = token;
    }

    public void setEmailSendSuccessfully(Boolean emailSendSuccessfully) {
        EmailSendSuccessfully = emailSendSuccessfully;
    }
}
