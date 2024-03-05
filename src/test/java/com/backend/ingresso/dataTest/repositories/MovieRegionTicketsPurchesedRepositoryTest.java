package com.backend.ingresso.dataTest.repositories;

import com.backend.ingresso.data.context.MovieRegionTicketsPurchesedJPA;
import com.backend.ingresso.data.repositories.MovieRegionTicketsPurchesedRepository;
import com.backend.ingresso.data.repositories.MovieRepository;
import com.backend.ingresso.domain.entities.Movie;
import com.backend.ingresso.domain.entities.MovieRegionTicketsPurchesed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MovieRegionTicketsPurchesedRepositoryTest {
    private MovieRegionTicketsPurchesedRepository movieRegionTicketsPurchesedRepository;

    @Mock
    private MovieRegionTicketsPurchesedJPA movieRegionTicketsPurchesedJPA;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        movieRegionTicketsPurchesedRepository = new MovieRegionTicketsPurchesedRepository(
                movieRegionTicketsPurchesedJPA);
    }

    @Test
    public void test_GetByMovieIdAndCinemaId_Exists() {
        String movieId = "e69fdc49-527f-4150-a44f-f446565185e2";
        String cinemaId = "223604d4-2f87-44bb-96b2-38a3389db176";

        when(movieRegionTicketsPurchesedJPA.
                getByMovieIdAndCinemaId(any(), any())).thenReturn(new MovieRegionTicketsPurchesed());

        MovieRegionTicketsPurchesed movie = movieRegionTicketsPurchesedRepository.
                getByMovieIdAndCinemaId(UUID.fromString(movieId), UUID.fromString(cinemaId));

        assertNotNull(movie);
    }

    @Test
    public void test_GetByMovieIdAndCinemaId_ReturnNullGet() {
        String movieId = "e69fdc49-527f-4150-a44f-f446565185e2";
        String cinemaId = "223604d4-2f87-44bb-96b2-38a3389db176";

        when(movieRegionTicketsPurchesedJPA.
                getByMovieIdAndCinemaId(any(), any())).thenReturn(null);

        MovieRegionTicketsPurchesed movie = movieRegionTicketsPurchesedRepository.
                getByMovieIdAndCinemaId(UUID.fromString(movieId), UUID.fromString(cinemaId));

        assertNull(movie);
    }

    @Test
    public void test_Create_Successfully() {
        MovieRegionTicketsPurchesed movieRegionTicketsPurchesed =
                new MovieRegionTicketsPurchesed(
                        null,null,
                        UUID.fromString("e69fdc49-527f-4150-a44f-f446565185e2"),null,
                        UUID.fromString("223604d4-2f87-44bb-96b2-38a3389db176"),null
                );

        when(movieRegionTicketsPurchesedJPA.
                save(any())).thenReturn(movieRegionTicketsPurchesed);

        MovieRegionTicketsPurchesed movie = movieRegionTicketsPurchesedRepository.
                create(movieRegionTicketsPurchesed);

        assertNotNull(movie);
        assertEquals(movie.getMovieId(), movieRegionTicketsPurchesed.getMovieId());
        assertEquals(movie.getCinemaId(), movieRegionTicketsPurchesed.getCinemaId());
    }

    @Test
    public void test_Create_ParameterNull() {
        MovieRegionTicketsPurchesed movie = movieRegionTicketsPurchesedRepository.
                create(null);

        assertNull(movie);
    }

    @Test
    public void test_Create_SaveNull() {
        MovieRegionTicketsPurchesed movieRegionTicketsPurchesed =
                new MovieRegionTicketsPurchesed(
                        null,null,
                        UUID.fromString("e69fdc49-527f-4150-a44f-f446565185e2"),null,
                        UUID.fromString("223604d4-2f87-44bb-96b2-38a3389db176"),null
                );

        when(movieRegionTicketsPurchesedJPA.
                save(any())).thenReturn(null);

        MovieRegionTicketsPurchesed movie = movieRegionTicketsPurchesedRepository.
                create(movieRegionTicketsPurchesed);

        assertNull(movie);
    }

    @Test
    public void test_UpdateTicketsSeats_Successfully() {
        MovieRegionTicketsPurchesed movieRegionTicketsPurchesed =
                new MovieRegionTicketsPurchesed(
                        null,"A2,A3",
                        UUID.fromString("e69fdc49-527f-4150-a44f-f446565185e2"),null,
                        UUID.fromString("223604d4-2f87-44bb-96b2-38a3389db176"),null
                );

        MovieRegionTicketsPurchesed movieRegionTicketsPurchesedFind =
                new MovieRegionTicketsPurchesed(
                        null,"A2,A3",
                        null,null,
                        null,null
                );

        when(movieRegionTicketsPurchesedJPA.
                getByMovieIdAndCinemaIdOnlyId(any(), any())).thenReturn(movieRegionTicketsPurchesed);

        when(movieRegionTicketsPurchesedJPA.
                findById(any())).thenReturn(Optional.of(movieRegionTicketsPurchesedFind));

        when(movieRegionTicketsPurchesedJPA.
                save(any())).thenReturn(movieRegionTicketsPurchesedFind);

        MovieRegionTicketsPurchesed movie = movieRegionTicketsPurchesedRepository.
                create(movieRegionTicketsPurchesed);

        assertNotNull(movie);
        assertEquals(movie.getTicketsSeats(), "A2,A3");
    }

    @Test
    public void test_UpdateTicketsSeats_ParameterNull() {
        MovieRegionTicketsPurchesed movie = movieRegionTicketsPurchesedRepository.
                create(null);

        assertNull(movie);
    }

    @Test
    public void test_UpdateTicketsSeats_GetIdMovieRegionTickets() {
        MovieRegionTicketsPurchesed movieRegionTicketsPurchesed =
                new MovieRegionTicketsPurchesed(
                        null,"A2,A3",
                        UUID.fromString("e69fdc49-527f-4150-a44f-f446565185e2"),null,
                        UUID.fromString("223604d4-2f87-44bb-96b2-38a3389db176"),null
                );

        when(movieRegionTicketsPurchesedJPA.
                getByMovieIdAndCinemaIdOnlyId(any(), any())).thenReturn(null);

        MovieRegionTicketsPurchesed movie = movieRegionTicketsPurchesedRepository.
                create(movieRegionTicketsPurchesed);

        assertNull(movie);
    }

    @Test
    public void test_UpdateTicketsSeats_FindByIdNull() {
        MovieRegionTicketsPurchesed movieRegionTicketsPurchesed =
                new MovieRegionTicketsPurchesed(
                        null,"A2,A3",
                        UUID.fromString("e69fdc49-527f-4150-a44f-f446565185e2"),null,
                        UUID.fromString("223604d4-2f87-44bb-96b2-38a3389db176"),null
                );

        when(movieRegionTicketsPurchesedJPA.
                getByMovieIdAndCinemaIdOnlyId(any(), any())).thenReturn(movieRegionTicketsPurchesed);

        when(movieRegionTicketsPurchesedJPA.
                findById(any())).thenReturn(Optional.empty());

        MovieRegionTicketsPurchesed movie = movieRegionTicketsPurchesedRepository.
                create(movieRegionTicketsPurchesed);

        assertNull(movie);
    }

    @Test
    public void test_UpdateTicketsSeats_SaveNull() {
        MovieRegionTicketsPurchesed movieRegionTicketsPurchesed =
                new MovieRegionTicketsPurchesed(
                        null,"A2,A3",
                        UUID.fromString("e69fdc49-527f-4150-a44f-f446565185e2"),null,
                        UUID.fromString("223604d4-2f87-44bb-96b2-38a3389db176"),null
                );

        when(movieRegionTicketsPurchesedJPA.
                getByMovieIdAndCinemaIdOnlyId(any(), any())).thenReturn(movieRegionTicketsPurchesed);

        when(movieRegionTicketsPurchesedJPA.
                findById(any())).thenReturn(Optional.of(new MovieRegionTicketsPurchesed()));

        when(movieRegionTicketsPurchesedJPA.
                save(any())).thenReturn(null);

        MovieRegionTicketsPurchesed movie = movieRegionTicketsPurchesedRepository.
                create(movieRegionTicketsPurchesed);

        assertNull(movie);
    }
}
