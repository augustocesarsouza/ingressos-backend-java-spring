package com.backend.ingresso.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CinemaMovieDTO {
    @JsonProperty("id")
    private UUID Id;
    @JsonProperty("cinemaId")
    private UUID CinemaId;
    @JsonProperty("cinemaDTO")
    private CinemaDTO cinemaDTO;
    @JsonProperty("movieId")
    private UUID MovieId;
    @JsonProperty("movieDTO")
    private MovieDTO movieDTO;
    @JsonProperty("regionId")
    private UUID RegionId;
    @JsonProperty("regionDTO")
    private RegionDTO regionDTO;
    @Column(name = "screeningSchedule")
    private String ScreeningSchedule;

    public CinemaMovieDTO(UUID id, UUID cinemaId, CinemaDTO cinema, UUID movieId, MovieDTO movie, UUID regionId, RegionDTO region, String screeningSchedule) {
        Id = id;
        CinemaId = cinemaId;
        this.cinemaDTO = cinema;
        MovieId = movieId;
        this.movieDTO = movie;
        RegionId = regionId;
        this.regionDTO = region;
        ScreeningSchedule = screeningSchedule;
    }

    public CinemaMovieDTO() {
    }

    public UUID getId() {
        return Id;
    }

    public UUID getCinemaId() {
        return CinemaId;
    }

    public CinemaDTO getCinema() {
        return cinemaDTO;
    }

    public UUID getMovieId() {
        return MovieId;
    }

    public MovieDTO getMovie() {
        return movieDTO;
    }

    public UUID getRegionId() {
        return RegionId;
    }

    public RegionDTO getRegion() {
        return regionDTO;
    }

    public String getScreeningSchedule() {
        return ScreeningSchedule;
    }
}
