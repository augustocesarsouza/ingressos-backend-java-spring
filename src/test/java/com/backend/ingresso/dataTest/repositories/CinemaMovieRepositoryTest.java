package com.backend.ingresso.dataTest.repositories;

import com.backend.ingresso.application.dto.CinemaMovieDTO;
import com.backend.ingresso.data.context.CinemaMovieRepositoryJPA;
import com.backend.ingresso.data.repositories.CinemaMovieRepository;
import com.backend.ingresso.data.repositories.CinemaRepository;
import com.backend.ingresso.data.repositories.MovieRepository;
import com.backend.ingresso.domain.entities.CinemaMovie;
import com.backend.ingresso.domain.entities.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CinemaMovieRepositoryTest {
    private CinemaMovieRepository cinemaMovieRepository;
    @Mock
    private CinemaMovieRepositoryJPA cinemaMovieRepositoryJPA;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cinemaMovieRepository = new CinemaMovieRepository(cinemaMovieRepositoryJPA);
    }

    @Test
    public void test_GetByRegionCinemaIdAndMovieId_Exists() {
        String regionId = "78c8f7ea-bcd5-47e0-b272-343d2b01f1dd";
        String movieId = "e69fdc49-527f-4150-a44f-f446565185e2";

        when(cinemaMovieRepositoryJPA.getByRegionCinemaIdAndMovieId(any(), any()))
                .thenReturn(new ArrayList<>());

        List<CinemaMovieDTO> movie = cinemaMovieRepository.getByRegionCinemaIdAndMovieId(
                UUID.fromString(regionId),UUID.fromString(movieId));

        assertNotNull(movie);
    }

    @Test
    public void test_GetByRegionCinemaIdAndMovieId_ReturnNull() {
        String regionId = "78c8f7ea-bcd5-47e0-b272-343d2b01f1dd";
        String movieId = "e69fdc49-527f-4150-a44f-f446565185e2";

        when(cinemaMovieRepositoryJPA.getByRegionCinemaIdAndMovieId(any(), any()))
                .thenReturn(null);

        List<CinemaMovieDTO> movie = cinemaMovieRepository.getByRegionCinemaIdAndMovieId(
                UUID.fromString(regionId),UUID.fromString(movieId));

        assertNull(movie);
    }

    @Test
    public void test_Create_Exists() {
        CinemaMovie cinemaMovie = new CinemaMovie();

        when(cinemaMovieRepositoryJPA.save(any()))
                .thenReturn(new CinemaMovie());

        CinemaMovie movie = cinemaMovieRepository.create(cinemaMovie);

        assertNotNull(movie);
    }

    @Test
    public void test_Create_ObjNull() {
        CinemaMovie movie = cinemaMovieRepository.create(null);

        assertNull(movie);
    }

    @Test
    public void test_Create_SaveNullReturn() {
        CinemaMovie cinemaMovie = new CinemaMovie();

        when(cinemaMovieRepositoryJPA.save(any()))
                .thenReturn(null);

        CinemaMovie movie = cinemaMovieRepository.create(cinemaMovie);

        assertNull(movie);
    }
}
