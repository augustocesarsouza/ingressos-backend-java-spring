package com.backend.ingresso.data.context;

import com.backend.ingresso.domain.entities.MovieRegionTicketsPurchesed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovieRegionTicketsPurchesedJPA extends JpaRepository<MovieRegionTicketsPurchesed, UUID> {
    //MovieRegionTicketsPurchesed(UUID id, String ticketsSeats, UUID movieId, Movie movie, UUID cinemaId, Cinema cinema)

    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "MovieRegionTicketsPurchesed(mt.Id, mt.TicketsSeats, mt.MovieId, null, mt.CinemaId, null) " +
            "FROM MovieRegionTicketsPurchesed AS mt WHERE mt.MovieId = :movieId AND mt.CinemaId = :cinemaId")
    List<MovieRegionTicketsPurchesed> getByMovieIdAndCinemaId(UUID movieId, UUID cinemaId);
    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "MovieRegionTicketsPurchesed(mt.Id, null, null, null, null, null) " +
            "FROM MovieRegionTicketsPurchesed AS mt WHERE mt.MovieId = :movieId AND mt.CinemaId = :cinemaId")
    List<MovieRegionTicketsPurchesed> getByMovieIdAndCinemaIdOnlyId(UUID movieId, UUID cinemaId);
    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "MovieRegionTicketsPurchesed(mt.Id, mt.TicketsSeats, null, null, null, null) " +
            "FROM MovieRegionTicketsPurchesed AS mt WHERE mt.MovieId = :movieId AND mt.CinemaId = :cinemaId")
    List<MovieRegionTicketsPurchesed> getByMovieIdAndCinemaIdAndIdTicketsSeats(UUID movieId, UUID cinemaId);
}
