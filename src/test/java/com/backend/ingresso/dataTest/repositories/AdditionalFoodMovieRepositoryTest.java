package com.backend.ingresso.dataTest.repositories;

import com.backend.ingresso.application.dto.AdditionalFoodMovieDTO;
import com.backend.ingresso.data.context.AdditionalFoodMovieRepositoryJPA;
import com.backend.ingresso.data.repositories.AdditionalFoodMovieRepository;
import com.backend.ingresso.domain.entities.AdditionalFoodMovie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AdditionalFoodMovieRepositoryTest {
    @Mock
    private AdditionalFoodMovieRepositoryJPA additionalFoodMovieRepositoryJPA;

    private AdditionalFoodMovieRepository additionalFoodMovieRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        additionalFoodMovieRepository = new AdditionalFoodMovieRepository(additionalFoodMovieRepositoryJPA);
    }

    @Test
    public void test_GetAllFoodMovie_Exists() throws ParseException {
        String movieId = "7ecc1a4a-950d-4555-8c4d-ac879f072c53";

        List<AdditionalFoodMovieDTO> additionalFoodMovieDTOS = new ArrayList<>();
        AdditionalFoodMovieDTO additionalFoodMovieDTO = new AdditionalFoodMovieDTO(
                null, "title1",
                "price1", "fee1",
                "imgUrl1", null,
                null, null
        );
        additionalFoodMovieDTOS.add(additionalFoodMovieDTO);

        when(additionalFoodMovieRepositoryJPA.getAllFoodMovie(any()))
                .thenReturn(additionalFoodMovieDTOS);

        List<AdditionalFoodMovieDTO> resultListAdd = additionalFoodMovieRepository
                .getAllFoodMovie(UUID.fromString(movieId));

        assertNotNull(resultListAdd);

        AdditionalFoodMovieDTO additionalFoodMovieDTO1 = resultListAdd.get(0);
        assertEquals(additionalFoodMovieDTO1.getTitle(), additionalFoodMovieDTO.getTitle());
        assertEquals(additionalFoodMovieDTO1.getPrice(), additionalFoodMovieDTO.getPrice());
        assertEquals(additionalFoodMovieDTO1.getFee(), additionalFoodMovieDTO.getFee());
        assertEquals(additionalFoodMovieDTO1.getImgUrl(), additionalFoodMovieDTO.getImgUrl());
    }

    @Test
    public void test_GetAllFoodMovie_ListEmpty() {
        String movieId = "7ecc1a4a-950d-4555-8c4d-ac879f072c53";

        List<AdditionalFoodMovieDTO> additionalFoodMovieDTOS = new ArrayList<>();

        when(additionalFoodMovieRepositoryJPA.getAllFoodMovie(any()))
                .thenReturn(additionalFoodMovieDTOS);

        List<AdditionalFoodMovieDTO> resultListAdd = additionalFoodMovieRepository
                .getAllFoodMovie(UUID.fromString(movieId));

        assertNotNull(resultListAdd);
        assertEquals(resultListAdd, additionalFoodMovieDTOS);
    }

    @Test
    public void test_Create_Successfully() {
        AdditionalFoodMovie additionalFoodMovie = new AdditionalFoodMovie(
                null, null,
                "price1", null,
                null, null,
                null, null
        );

        when(additionalFoodMovieRepositoryJPA.save(any()))
                .thenReturn(additionalFoodMovie);

        AdditionalFoodMovie resultAdd = additionalFoodMovieRepository
                .create(additionalFoodMovie);

        assertNotNull(resultAdd);
        assertEquals(additionalFoodMovie.getPrice(), resultAdd.getPrice());
    }

    @Test
    public void test_Create_ErrorAdditionalFoodMovieNull() {
        AdditionalFoodMovie resultAdd = additionalFoodMovieRepository
                .create(null);

        assertNull(resultAdd);
    }

    @Test
    public void test_Create_SaveNull() {
        AdditionalFoodMovie additionalFoodMovie = new AdditionalFoodMovie(
                null, null,
                "price1", null,
                null, null,
                null, null
        );

        when(additionalFoodMovieRepositoryJPA.save(any()))
                .thenReturn(null);

        AdditionalFoodMovie resultAdd = additionalFoodMovieRepository
                .create(additionalFoodMovie);

        assertNull(resultAdd);
    }
}
