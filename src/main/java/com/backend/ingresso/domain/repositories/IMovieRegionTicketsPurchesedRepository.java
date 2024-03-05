package com.backend.ingresso.domain.repositories;

import com.backend.ingresso.domain.entities.MovieRegionTicketsPurchesed;

import java.util.UUID;

public interface IMovieRegionTicketsPurchesedRepository {
    MovieRegionTicketsPurchesed getByMovieIdAndCinemaId(UUID movieId, UUID cinemaId);
    MovieRegionTicketsPurchesed getByMovieIdAndCinemaIdAndIdTicketsSeats(UUID movieId, UUID cinemaId);
    MovieRegionTicketsPurchesed create(MovieRegionTicketsPurchesed movieRegionTicketsPurchesed);
    MovieRegionTicketsPurchesed updateTicketsSeats(MovieRegionTicketsPurchesed movieRegionTicketsPurchesed);

}
