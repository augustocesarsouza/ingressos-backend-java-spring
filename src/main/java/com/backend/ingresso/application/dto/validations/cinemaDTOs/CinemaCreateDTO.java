package com.backend.ingresso.application.dto.validations.cinemaDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public class CinemaCreateDTO {
    @JsonProperty("id")
    private UUID Id;
    @NotEmpty(message = "nameCinema should not be empty")
    @JsonProperty("nameCinema")
    private String NameCinema;
    @NotEmpty(message = "district should not be empty")
    @JsonProperty("district")
    private String District;
    @NotEmpty(message = "ranking should not be empty")
    @JsonProperty("ranking")
    private String Ranking;

    public CinemaCreateDTO(UUID id, String nameCinema, String district, String ranking) {
        Id = id;
        NameCinema = nameCinema;
        District = district;
        Ranking = ranking;
    }

    public CinemaCreateDTO() {
    }

    public UUID getId() {
        return Id;
    }

    public String getNameCinema() {
        return NameCinema;
    }

    public String getDistrict() {
        return District;
    }

    public String getRanking() {
        return Ranking;
    }

    public void setId(UUID id) {
        Id = id;
    }
}
