package com.backend.ingresso.application.dto.validations.movieRegionTicketsPurchesedDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public class MovieRegionTicketsPurchesedUpdateDTO {
    @NotEmpty(message = "ticketsSeats should not be empty")
    @JsonProperty("ticketsSeats")
    private String TicketsSeats;
    @NotEmpty(message = "movieId should not be empty")
    @JsonProperty("movieId")
    private String MovieId;
    @NotEmpty(message = "cinemaId should not be empty")
    @JsonProperty("cinemaId")
    private String CinemaId;

    public MovieRegionTicketsPurchesedUpdateDTO(String ticketsSeats, String movieId, String cinemaId) {
        TicketsSeats = ticketsSeats;
        MovieId = movieId;
        CinemaId = cinemaId;
    }

    public MovieRegionTicketsPurchesedUpdateDTO() {
    }

    public String getTicketsSeats() {
        return TicketsSeats;
    }

    public String getMovieId() {
        return MovieId;
    }

    public String getCinemaId() {
        return CinemaId;
    }
}
