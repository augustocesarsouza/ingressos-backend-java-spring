package com.backend.ingresso.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_movie_region_tickets_purcheseds", schema = "public")
public class MovieRegionTicketsPurchesed {
    @Id
    @Column(name = "movie_region_tickets_purchesed_id")
    @JsonProperty("id")
    private UUID Id;
    @Column(name = "tickets_seats")
    private String TicketsSeats;
    @Column(name = "movie_id")
    @JsonProperty("movieId")
    private UUID MovieId;
    @ManyToOne
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    private Movie movie;
    @Column(name = "cinema_id")
    @JsonProperty("cinemaId")
    private UUID CinemaId;
    @ManyToOne
    @JoinColumn(name = "cinema_id", insertable = false, updatable = false)
    private Cinema cinema;

    public MovieRegionTicketsPurchesed(UUID id, String ticketsSeats, UUID movieId, Movie movie, UUID cinemaId, Cinema cinema) {
        Id = id;
        TicketsSeats = ticketsSeats;
        MovieId = movieId;
        this.movie = movie;
        CinemaId = cinemaId;
        this.cinema = cinema;
    }

    public MovieRegionTicketsPurchesed() {
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

    public Movie getMovie() {
        return movie;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setTicketsSeats(String ticketsSeats) {
        TicketsSeats = ticketsSeats;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public void setIdMovieIdCinemaId(UUID id, UUID movieId, UUID cinemaId){
        Id = id;
        MovieId = movieId;
        CinemaId = cinemaId;
    }

    public void setMovieIdCinemaIdTicketsSeats(UUID movieId, UUID cinemaId){
        MovieId = movieId;
        CinemaId = cinemaId;
    }

    public void ticketsSeatsValue(String ticketsSeats, String ticketsSeatsBank){
        if(ticketsSeatsBank.isEmpty()){
            TicketsSeats = ticketsSeats;
        }else if(ticketsSeats.isEmpty()){
            TicketsSeats = ticketsSeats;
        }else if(!ticketsSeatsBank.isEmpty()){
            TicketsSeats = String.format("%s,%s", ticketsSeatsBank, ticketsSeats);
        }
    }
}
