package com.backend.ingresso.dataTest.repositories;

import com.backend.ingresso.application.dto.MovieTheaterDTO;
import com.backend.ingresso.data.context.MovieTheaterRepositoryJPA;
import com.backend.ingresso.data.repositories.MovieTheaterRepository;
import com.backend.ingresso.domain.entities.MovieTheater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MovieTheaterRepositoryTest {
    private MovieTheaterRepository movieTheaterRepository;
    @Mock
    private MovieTheaterRepositoryJPA movieTheaterRepositoryJPA;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        movieTheaterRepository = new MovieTheaterRepository(movieTheaterRepositoryJPA);
    }

    @Test
    public void test_GetMovieTheaterIfThereIs_Exists() {
        String movieId = "e69fdc49-527f-4150-a44f-f446565185e2";
        String regionId = "f243693b-a3ce-496b-94fa-1e04236b1752";

        when(movieTheaterRepositoryJPA.getMovieTheaterIfThereIs(any(), any()))
                .thenReturn(new MovieTheater());

        MovieTheater movieTheater = movieTheaterRepository.getMovieTheaterIfThereIs(
                UUID.fromString(movieId), UUID.fromString(regionId));

        assertNotNull(movieTheater);
    }

    @Test
    public void test_GetMovieTheaterIfThereIs_ReturnNull() {
        String movieId = "e69fdc49-527f-4150-a44f-f446565185e2";
        String regionId = "f243693b-a3ce-496b-94fa-1e04236b1752";

        when(movieTheaterRepositoryJPA.getMovieTheaterIfThereIs(any(), any()))
                .thenReturn(null);

        MovieTheater movieTheater = movieTheaterRepository.getMovieTheaterIfThereIs(
                UUID.fromString(movieId), UUID.fromString(regionId));

        assertNull(movieTheater);
    }

    @Test
    public void test_Create_Exists() {
        MovieTheater movieTheater = new MovieTheater(
                UUID.fromString("fbf25a83-265a-409c-97f8-b6173c6cf935"),
                UUID.fromString("e69fdc49-527f-4150-a44f-f446565185e2"),
                UUID.fromString("f243693b-a3ce-496b-94fa-1e04236b1752"));

        when(movieTheaterRepositoryJPA.save(any()))
                .thenReturn(movieTheater);

        MovieTheater movieTheaterCreate = movieTheaterRepository.create(movieTheater);

        assertNotNull(movieTheaterCreate);
        assertEquals(movieTheater.getId(), movieTheater.getId());
    }

    @Test
    public void test_Create_ReturnNull() {
        MovieTheater movieTheaterCreate = movieTheaterRepository.create(null);

        assertNull(movieTheaterCreate);
    }
}
