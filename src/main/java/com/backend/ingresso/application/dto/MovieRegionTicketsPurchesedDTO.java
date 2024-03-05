package com.backend.ingresso.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieRegionTicketsPurchesedDTO {
    @JsonProperty("id")
    private UUID Id;
    @JsonProperty("ticketsSeats")
    private String TicketsSeats;
    @JsonProperty("movieId")
    private UUID MovieId;
    private MovieDTO movieDTO;
    @JsonProperty("cinemaId")
    private UUID CinemaId;
    private CinemaDTO cinemaDTO;

    public MovieRegionTicketsPurchesedDTO(UUID id, String ticketsSeats, UUID movieId, MovieDTO movieDTO, UUID cinemaId, CinemaDTO cinemaDTO) {
        Id = id;
        TicketsSeats = ticketsSeats;
        MovieId = movieId;
        this.movieDTO = movieDTO;
        CinemaId = cinemaId;
        this.cinemaDTO = cinemaDTO;
    }

    public MovieRegionTicketsPurchesedDTO() {
    }

    public UUID getId() {
        return Id;
    }

    public String getTicketsSeats() {
        return TicketsSeats;
    }

    public UUID getMovieId() {
        return MovieId;
    }

    public UUID getCinemaId() {
        return CinemaId;
    }

    public MovieDTO getMovieDTO() {
        return movieDTO;
    }

    public CinemaDTO getCinemaDTO() {
        return cinemaDTO;
    }
}
