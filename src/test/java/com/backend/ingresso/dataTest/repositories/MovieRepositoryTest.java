package com.backend.ingresso.dataTest.repositories;

import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.data.context.MovieRepositoryJPA;
import com.backend.ingresso.data.repositories.MovieRepository;
import com.backend.ingresso.domain.entities.Movie;
import com.backend.ingresso.domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MovieRepositoryTest {
    private MovieRepository movieRepository;

    @Mock
    private MovieRepositoryJPA movieRepositoryJPA;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        movieRepository = new MovieRepository(movieRepositoryJPA);
    }

    @Test
    public void test_GetById_Exists() {
        String movieId = "e69fdc49-527f-4150-a44f-f446565185e2";

        when(movieRepositoryJPA.getMovieById_Info_Id_Title(any())).thenReturn(new Movie());

        Movie movie = movieRepository.getById(UUID.fromString(movieId));

        assertNotNull(movie);
    }

    @Test
    public void test_GetById_ReturnNull_Query() {
        String movieId = "e69fdc49-527f-4150-a44f-f446565185e2";

        when(movieRepositoryJPA.getMovieById_Info_Id_Title(any())).thenReturn(null);

        Movie movie = movieRepository.getById(UUID.fromString(movieId));

        assertNull(movie);
    }

    @Test
    public void test_GetInfoMoviesById_Exists() {
        String movieId = "e69fdc49-527f-4150-a44f-f446565185e2";

        when(movieRepositoryJPA.getMovieById(any())).thenReturn(new Movie());

        Movie movie = movieRepository.getInfoMoviesById(UUID.fromString(movieId));

        assertNotNull(movie);
    }

    @Test
    public void test_GetInfoMoviesById_ReturnNull_Query() {
        String movieId = "e69fdc49-527f-4150-a44f-f446565185e2";

        when(movieRepositoryJPA.getMovieById(any())).thenReturn(null);

        Movie movie = movieRepository.getInfoMoviesById(UUID.fromString(movieId));

        assertNull(movie);
    }
    @Test
    public void test_GetStatusMovie_Exists() {
        when(movieRepositoryJPA.getMovieByStatusMovie(any())).thenReturn(new Movie());

        Movie movie = movieRepository.getStatusMovie("seila");

        assertNotNull(movie);

    }

    @Test
    public void test_GetStatusMovie_ReturnNull_Query() {
        when(movieRepositoryJPA.getMovieByStatusMovie(any())).thenReturn(null);

        Movie movie = movieRepository.getStatusMovie("seila");

        assertNull(movie);
    }

    @Test
    public void test_GetMovieByIdForDelete_Exists() {
        String movieId = "e69fdc49-527f-4150-a44f-f446565185e2";

        when(movieRepositoryJPA.getMovieByIdForDelete(any())).thenReturn(new Movie());

        Movie movie = movieRepository.getMovieByIdForDelete(UUID.fromString(movieId));

        assertNotNull(movie);
    }

    @Test
    public void test_GetByIdAllProps_ReturnNull() {
        String movieId = "e69fdc49-527f-4150-a44f-f446565185e2";

        when(movieRepositoryJPA.getMovieByIdForDelete(any())).thenReturn(null);

        Movie movie = movieRepository.getMovieByIdForDelete(UUID.fromString(movieId));

        assertNull(movie);
    }

    @Test
    public void test_GetAllMovieByRegionId_Exists() {
        String regionId = "e69fdc49-527f-4150-a44f-f446565185e2";

        List<MovieDTO> listMovieDto = new ArrayList<>();
        listMovieDto.add(new MovieDTO());

        when(movieRepositoryJPA.getMovieByRegionId_Info_All(any())).thenReturn(listMovieDto);

        List<MovieDTO> listMovieDTOS = movieRepository.getAllMovieByRegionId(UUID.fromString(regionId));

        assertNotNull(listMovieDTOS);
    }

    @Test
    public void test_GetAllMovieByRegionId_ReturnNull() {
        String regionId = "e69fdc49-527f-4150-a44f-f446565185e2";

        when(movieRepositoryJPA.getMovieByRegionId_Info_All(any())).thenReturn(new ArrayList<>());

        List<MovieDTO> listMovieDTOS = movieRepository.getAllMovieByRegionId(UUID.fromString(regionId));

        assertNull(listMovieDTOS);
    }

    @Test
    public void test_Create_Successfully() {
        Movie movie = new Movie();

        when(movieRepositoryJPA.save(any())).thenReturn(movie);

        Movie movieCreate = movieRepository.create(movie);

        assertNotNull(movieCreate);
        assertEquals(movie, movieCreate);
    }

    @Test
    public void test_Create_ReturnNull() {
        Movie movieCreate = movieRepository.create(null);

        assertNull(movieCreate);
    }

    @Test
    public void test_Update_Successfully() {
        Movie movie = new Movie(UUID.fromString("786a283b-ee9a-495e-9d7d-bd730dbf19d4"),
                null, null,
                null, null, null, "imgUrlSeila", "dsfvbdfbdfb",
                "imgUrlBackgroundseila", "ojikj9uiiuo", null);

        Movie movieUpdate = new Movie(null, "seilatitle", "descricaoseila",
                null, null, null, "seilaimgUrl", null,
                null, null, null);

        when(movieRepositoryJPA.findById(any())).thenReturn(Optional.of(movieUpdate));
        when(movieRepositoryJPA.save(any())).thenReturn(movieUpdate);

        Movie movieUpda = movieRepository.updateImg(movie);

        assertNotNull(movieUpda);
    }

    @Test
    public void test_Update_ReturnNullUser() {
        Movie movie = movieRepository.updateImg(null);

        assertNull(movie);
    }

    @Test
    public void test_Update_ReturnNull_FindById() {
        Movie movie = new Movie(null, "Leão", "seilaseila",
                null, null, null, null, null, null,
                null, null);

        when(movieRepositoryJPA.findById(any())).thenReturn(Optional.empty());

        Movie movieUpdateNotFound = movieRepository.updateImg(movie);

        assertNull(movieUpdateNotFound);
    }

    @Test
    public void test_Delete_Successfully() {
        String movieId = "de2adbd9-de53-492c-adb9-b5c1c97614cf";
        when(movieRepositoryJPA.findById(any())).thenReturn(Optional.of(new Movie()));

        Movie deleteMovie = movieRepository.delete(UUID.fromString(movieId));

        assertNotNull(deleteMovie);
    }

    @Test
    public void test_Delete_ReturnNull_Movie() {
        String movieId = "de2adbd9-de53-492c-adb9-b5c1c97614cf";
        when(movieRepositoryJPA.findById(any())).thenReturn(Optional.empty());

        Movie deleteMovie = movieRepository.delete(UUID.fromString(movieId));

        assertNull(deleteMovie);
    }
}
