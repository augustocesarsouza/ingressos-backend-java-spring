package com.backend.ingresso.applicationTest;

import com.backend.ingresso.application.ErrorValidation;
import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.application.dto.MovieTheaterDTO;
import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.backend.ingresso.application.dto.validations.movieTheaterDTOs.MovieTheaterCreate;
import com.backend.ingresso.application.mappings.MappingClassInterface.IMovieTheaterMapper;
import com.backend.ingresso.application.services.MovieTheaterService;
import com.backend.ingresso.application.services.ResultService;
import com.backend.ingresso.application.services.interfaces.IMovieService;
import com.backend.ingresso.application.services.interfaces.IRegionService;
import com.backend.ingresso.domain.entities.MovieTheater;
import com.backend.ingresso.domain.repositories.IMovieTheaterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MovieTheaterServiceTest {
    @Mock
    private IMovieTheaterRepository movieTheaterRepository;
    @Mock
    private IValidateErrorsDTO validateErrorsDTO;
    @Mock
    private IMovieService movieService;
    @Mock
    private IRegionService regionService;
    @Mock
    private IMovieTheaterMapper movieTheaterMapper;

    private MovieTheaterService movieTheaterService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        movieTheaterService = new MovieTheaterService(movieTheaterRepository,
                movieTheaterMapper, validateErrorsDTO,
                movieService, regionService);
    }

    @Test
    public void should_Create_WithoutErrors(){
        MovieTheaterCreate movieTheaterCreate = new MovieTheaterCreate(
                "movieTitle1", "regionState1");

        var resultError = new BeanPropertyBindingResult(movieTheaterCreate, "movieTheaterCreate");

        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(UUID.fromString("adea4854-1a9c-4631-bb88-3af344a28c47"));

        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setId(UUID.fromString("ccda4a9c-6cfb-43a6-8e6e-5b7cd2763f29"));

        when(movieService.getIdMovieByTitle(any())).thenReturn(ResultService.Ok(movieDTO));
        when(regionService.getIdRegionByState(any())).thenReturn(ResultService.Ok(regionDTO));
        when(movieTheaterRepository.getMovieTheaterIfThereIs(any(), any()))
                .thenReturn(null);
        when(movieTheaterRepository.create(any()))
                .thenReturn(new MovieTheater());

        var result = movieTheaterService.create(movieTheaterCreate, resultError);

        assertTrue(result.IsSuccess);
    }

    @Test
    public void should_Create_Error_DTOInvalid(){
        MovieTheaterCreate movieTheaterCreate = new MovieTheaterCreate(
                "movieTitle1", "regionState1");

        var resultError = new BeanPropertyBindingResult(movieTheaterCreate, "movieTheaterCreate");
        resultError.rejectValue("movieTitle", "NotEmpty",
                "movieTitle should not be empty");

        List<ErrorValidation> errors = new ArrayList<>();
        var errorValidation = new ErrorValidation("movieTitle", "movieTitle should not be empty");
        errors.add(errorValidation);

        when(validateErrorsDTO.ValidateDTO(any())).thenReturn(errors);

        var result = movieTheaterService.create(movieTheaterCreate, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error validate DTO");
        assertEquals(result.Errors, errors);
    }

    @Test
    public void should_Create_Error_GetMovie(){
        MovieTheaterCreate movieTheaterCreate = new MovieTheaterCreate(
                "movieTitle1", "regionState1");

        var resultError = new BeanPropertyBindingResult(movieTheaterCreate, "movieTheaterCreate");

        when(movieService.getIdMovieByTitle(any())).thenReturn(
                ResultService.Fail("error find movie"));

        var result = movieTheaterService.create(movieTheaterCreate, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error find movie");
    }

    @Test
    public void should_Create_Error_GetRegion(){
        MovieTheaterCreate movieTheaterCreate = new MovieTheaterCreate(
                "movieTitle1", "regionState1");

        var resultError = new BeanPropertyBindingResult(movieTheaterCreate, "movieTheaterCreate");

        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(UUID.fromString("adea4854-1a9c-4631-bb88-3af344a28c47"));

        when(movieService.getIdMovieByTitle(any())).thenReturn(ResultService.Ok(movieDTO));
        when(regionService.getIdRegionByState(any())).thenReturn(
                ResultService.Fail("error find region"));

        var result = movieTheaterService.create(movieTheaterCreate, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error find region");
    }

    @Test
    public void should_Create_Error_JunctionAlreadyExists(){
        MovieTheaterCreate movieTheaterCreate = new MovieTheaterCreate(
                "movieTitle1", "regionState1");

        var resultError = new BeanPropertyBindingResult(movieTheaterCreate, "movieTheaterCreate");

        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(UUID.fromString("adea4854-1a9c-4631-bb88-3af344a28c47"));

        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setId(UUID.fromString("ccda4a9c-6cfb-43a6-8e6e-5b7cd2763f29"));

        when(movieService.getIdMovieByTitle(any())).thenReturn(ResultService.Ok(movieDTO));
        when(regionService.getIdRegionByState(any())).thenReturn(ResultService.Ok(regionDTO));
        when(movieTheaterRepository.getMovieTheaterIfThereIs(any(), any()))
                .thenReturn(new MovieTheater());

        var result = movieTheaterService.create(movieTheaterCreate, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error this junction already exists");
    }

    @Test
    public void should_Create_ErrorCreateMovieDB(){
        MovieTheaterCreate movieTheaterCreate = new MovieTheaterCreate(
                "movieTitle1", "regionState1");

        var resultError = new BeanPropertyBindingResult(movieTheaterCreate, "movieTheaterCreate");

        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(UUID.fromString("adea4854-1a9c-4631-bb88-3af344a28c47"));

        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setId(UUID.fromString("ccda4a9c-6cfb-43a6-8e6e-5b7cd2763f29"));

        when(movieService.getIdMovieByTitle(any())).thenReturn(ResultService.Ok(movieDTO));
        when(regionService.getIdRegionByState(any())).thenReturn(ResultService.Ok(regionDTO));
        when(movieTheaterRepository.getMovieTheaterIfThereIs(any(), any()))
                .thenReturn(null);
        when(movieTheaterRepository.create(any()))
                .thenReturn(null);

        var result = movieTheaterService.create(movieTheaterCreate, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error when create");
    }
}
