package com.backend.ingresso.application.dto.validations.movieRegionTicketsPurchesedDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public class MovieRegionTicketsPurchesedCreateDTO {
    @JsonProperty("id")
    private UUID Id;
    @NotEmpty(message = "movieId should not be empty")
    @JsonProperty("movieId")
    private String MovieId;
    @NotEmpty(message = "cinemaId should not be empty")
    @JsonProperty("cinemaId")
    private String CinemaId;

    public MovieRegionTicketsPurchesedCreateDTO(UUID id, String movieId, String cinemaId) {
        Id = id;
        MovieId = movieId;
        CinemaId = cinemaId;
    }

    public MovieRegionTicketsPurchesedCreateDTO(){
    }

    public UUID getId() {
        return Id;
    }

    public String getMovieId() {
        return MovieId;
    }

    public String getCinemaId() {
        return CinemaId;
    }
}
