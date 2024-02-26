package com.backend.ingresso.application.dto.validations.AdditionalInfoUserValidationDTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdditionalInfoUserCreateDTO {
    @JsonProperty("id")
    private UUID Id;
    @NotEmpty(message = "UserId should not be empty")
    @Size(min = 36, message = "UserId should have at last 36 characters")
    @JsonProperty("userId")
    private UUID UserId;
    @JsonProperty("birthDate")
    private Timestamp BirthDate;
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
    @JsonProperty("birthDateString")
    private String BirthDateString;

    public AdditionalInfoUserCreateDTO(UUID id, UUID userId, Timestamp birthDate, String gender,
                                       String phone, String cep, String logradouro, String numero,
                                       String complemento, String referencia, String bairro, String estado, String cidade) {
        Id = id;
        UserId = userId;
        BirthDate = birthDate;
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

    public UUID getId() {
        return Id;
    }

    public UUID getUserId() {
        return UserId;
    }

    public Timestamp getBirthDate() {
        return BirthDate;
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
}
