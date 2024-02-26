package com.backend.ingresso.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tb_additional_info_users", schema = "public")
public class AdditionalInfoUser {
    @Id
    @Column(name = "additional_info_user_id")
    @JsonProperty("id")
    private UUID Id;
    @Column(name = "user_id")
    @JsonProperty("userId")
    private UUID UserId;
    @Column(name = "birth_date")
    @JsonProperty("birthDate")
    private Timestamp BirthDate;
    @Column(name = "gender")
    @JsonProperty("gender")
    private String Gender;
    @Column(name = "phone")
    @JsonProperty("phone")
    private String Phone;
    @Column(name = "cep")
    @JsonProperty("cep")
    private String Cep;
    @Column(name = "logradouro")
    @JsonProperty("logradouro")
    private String Logradouro;
    @Column(name = "numero")
    @JsonProperty("numero")
    private String Numero;
    @Column(name = "complemento")
    @JsonProperty("complemento")
    private String Complemento;
    @Column(name = "referencia")
    @JsonProperty("referencia")
    private String Referencia;
    @Column(name = "bairro")
    @JsonProperty("bairro")
    private String Bairro;
    @Column(name = "estado")
    @JsonProperty("estado")
    private String Estado;
    @Column(name = "cidade")
    @JsonProperty("cidade")
    private String Cidade;

    public AdditionalInfoUser(UUID id, UUID userId, Timestamp birthDate,
                              String gender, String phone, String cep,
                              String logradouro, String numero, String complemento,
                              String referencia, String bairro, String estado, String cidade) {
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

    public AdditionalInfoUser() {
    }

    public void addData(Timestamp birthDate,
                        String gender, String phone, String cep,
                        String logradouro, String numero, String complemento,
                        String referencia, String bairro, String estado, String cidade, UUID userId){
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
        UserId = userId;
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

    public void setId(UUID id) {
        Id = id;
    }
}
