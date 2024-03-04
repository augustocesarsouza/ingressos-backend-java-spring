package com.backend.ingresso.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CinemaDTO {
    @JsonProperty("id")
    private UUID Id;
    @JsonProperty("nameCinema")
    private String NameCinema;
    @JsonProperty("district")
    private String District;
    @JsonProperty("ranking")
    private String Ranking;

    public CinemaDTO(UUID id, String nameCinema, String district, String ranking) {
        Id = id;
        NameCinema = nameCinema;
        District = district;
        Ranking = ranking;
    }

    public CinemaDTO() {
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
}
