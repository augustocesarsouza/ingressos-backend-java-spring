package com.backend.ingresso.data.repositories;

import com.backend.ingresso.data.context.MovieRegionTicketsPurchesedJPA;
import com.backend.ingresso.domain.entities.MovieRegionTicketsPurchesed;
import com.backend.ingresso.domain.repositories.IMovieRegionTicketsPurchesedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MovieRegionTicketsPurchesedRepository implements IMovieRegionTicketsPurchesedRepository {
    private final MovieRegionTicketsPurchesedJPA movieRegionTicketsPurchesedJPA;

    @Autowired
    public MovieRegionTicketsPurchesedRepository(MovieRegionTicketsPurchesedJPA movieRegionTicketsPurchesedJPA) {
        this.movieRegionTicketsPurchesedJPA = movieRegionTicketsPurchesedJPA;
    }

    @Override
    public MovieRegionTicketsPurchesed getByMovieIdAndCinemaId(UUID movieId, UUID cinemaId) {
        return movieRegionTicketsPurchesedJPA.getByMovieIdAndCinemaId(movieId, cinemaId).stream().findFirst().orElse(null);
    }

    @Override
    public MovieRegionTicketsPurchesed getByMovieIdAndCinemaIdAndIdTicketsSeats(UUID movieId, UUID cinemaId){
        return movieRegionTicketsPurchesedJPA.getByMovieIdAndCinemaIdAndIdTicketsSeats(movieId, cinemaId).stream().findFirst().orElse(null);
    }

    @Override
    public MovieRegionTicketsPurchesed create(MovieRegionTicketsPurchesed movieRegionTicketsPurchesed) {
        if(movieRegionTicketsPurchesed == null)
            return null;

        return movieRegionTicketsPurchesedJPA.save(movieRegionTicketsPurchesed);
    }

    @Override
    public MovieRegionTicketsPurchesed updateTicketsSeats(MovieRegionTicketsPurchesed movieRegionTicketsPurchesed) {
        if(movieRegionTicketsPurchesed == null)
            return null;

        MovieRegionTicketsPurchesed ticketsPurchesedForUpdate = movieRegionTicketsPurchesedJPA.findById(
                movieRegionTicketsPurchesed.getId()).orElse(null);

        if(ticketsPurchesedForUpdate == null)
            return null;

        ticketsPurchesedForUpdate.setTicketsSeats(movieRegionTicketsPurchesed.getTicketsSeats());
        return movieRegionTicketsPurchesedJPA.save(ticketsPurchesedForUpdate);
    }
}
