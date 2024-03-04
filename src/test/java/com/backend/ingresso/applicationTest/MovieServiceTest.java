package com.backend.ingresso.applicationTest;

import com.backend.ingresso.application.ErrorValidation;
import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.backend.ingresso.application.dto.validations.movieValidationDTOs.MovieCreateValidatorDTO;
import com.backend.ingresso.application.dto.validations.movieValidationDTOs.MovieUpdateValidatorDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IMovieMapper;
import com.backend.ingresso.application.services.MovieService;
import com.backend.ingresso.application.services.ResultService;
import com.backend.ingresso.application.services.interfaces.IRegionService;
import com.backend.ingresso.data.cloudinaryUtil.CloudinaryCreate;
import com.backend.ingresso.data.utilityExternal.Interface.ICloudinaryUti;
import com.backend.ingresso.domain.entities.Movie;
import com.backend.ingresso.domain.repositories.IMovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class MovieServiceTest {
    @Mock
    private IMovieRepository movieRepository;
    @Mock
    private IRegionService regionService;
    @Mock
    private IValidateErrorsDTO validateErrorsDTO;
    @Mock
    private ICloudinaryUti cloudinaryUti;
    @Mock
    private IMovieMapper movieMapper;

    private MovieService movieService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        movieService = new MovieService(movieRepository, regionService,
                validateErrorsDTO, cloudinaryUti, movieMapper);
    }

    @Test
    public void should_GetAllMovieByRegionId_WithoutErrors(){
        String region = "regionTest";
        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setId(UUID.fromString("08b74c56-af65-4018-919f-07c42267b6fa"));

        when(regionService.getIdByNameState(any())).thenReturn(ResultService.Ok(regionDTO));
        when(movieRepository.getAllMovieByRegionId(any())).thenReturn(new ArrayList<>());

        // Act
        var result = movieService.getAllMovieByRegionId(region);

        // Assert
        assertTrue(result.IsSuccess);
    }

    @Test
    public void should_GetAllMovieByRegionId_WithErrorGetRegionId(){
        String region = "regionTest";

        when(regionService.getIdByNameState(any())).thenReturn(ResultService.Fail("error getIdByNameState"));

        // Act
        var result = movieService.getAllMovieByRegionId(region);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error getIdByNameState");
    }

    @Test
    public void should_GetAllMovieByRegionId_WithError_getAllMovieByRegionId_Null(){
        String region = "regionTest";
        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setId(UUID.fromString("08b74c56-af65-4018-919f-07c42267b6fa"));

        when(regionService.getIdByNameState(any())).thenReturn(ResultService.Ok(regionDTO));
        when(movieRepository.getAllMovieByRegionId(any())).thenReturn(null);

        // Act
        var result = movieService.getAllMovieByRegionId(region);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error get all movies by regionId");
    }

    @Test
    public void should_GetInfoMoviesById_WithoutErrors(){
        String movieId = "08b74c56-af65-4018-919f-07c42267b6fa";

        when(movieRepository.getInfoMoviesById(any())).thenReturn(new Movie());

        // Act
        var result = movieService.getInfoMoviesById(UUID.fromString(movieId));

        // Assert
        assertTrue(result.IsSuccess);
    }
    @Test
    public void should_GetInfoMoviesById_WithError_MovieDTO(){
        String movieId = "08b74c56-af65-4018-919f-07c42267b6fa";

        when(movieRepository.getInfoMoviesById(any())).thenReturn(null);

        // Act
        var result = movieService.getInfoMoviesById(UUID.fromString(movieId));

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error get info movies by id");
    }

    @Test
    public void should_GetStatusMovie_WithoutErrors(){
        String statusMovie = "seila";

        when(movieRepository.getStatusMovie(any())).thenReturn(new Movie());

        // Act
        var result = movieService.getStatusMovie(statusMovie);

        // Assert
        assertTrue(result.IsSuccess);
    }

    @Test
    public void should_GetStatusMovie_WithError_GetStatusMovie_Null(){
        String statusMovie = "seila";

        when(movieRepository.getStatusMovie(any())).thenReturn(null);

        // Act
        var result = movieService.getStatusMovie(statusMovie);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "statusMovie not found");
    }

    @Test
    public void should_Create_WithoutErrors(){
        MovieCreateValidatorDTO movieCreateValidatorDTO = new MovieCreateValidatorDTO("title1",
                "description1", "gender1", "2h30",
                12, "data:image/webp;base64,sdvsdvsdv", "statusMovie1");

        var resultError = new BeanPropertyBindingResult(movieCreateValidatorDTO, "movieCreateValidatorDTO");

        when(cloudinaryUti.CreateImg(any(), anyInt(), anyInt())).thenReturn(ResultService.Ok
                (new CloudinaryCreate("publicId1", "imgUrl1")));

        when(movieRepository.create(any())).thenReturn(new Movie());

        // Act
        var result = movieService.create(movieCreateValidatorDTO, resultError);

        // Assert
        assertTrue(result.IsSuccess);
    }

    @Test
    public void should_Create_WithError_Validate_DTO(){
        MovieCreateValidatorDTO movieCreateValidatorDTO = new MovieCreateValidatorDTO("title1",
                "description1", "gender1", "2h30",
                12, "data:image/webp;base64,sdvsdvsdv", "statusMovie1");

        var resultError = new BeanPropertyBindingResult(movieCreateValidatorDTO, "movieCreateValidatorDTO");
        resultError.rejectValue("title", "NotEmpty", "title should not be empty");

        List<ErrorValidation> errors = new ArrayList<>();
        var errorValidation = new ErrorValidation("title", "title should not be empty");
        errors.add(errorValidation);

        when(validateErrorsDTO.ValidateDTO(anyList())).thenReturn(errors);

        // Act
        var result = movieService.create(movieCreateValidatorDTO, resultError);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error validate DTO");
        assertEquals(result.Errors, errors);
    }

    @Test
    public void should_Create_WithError_ErrorCreateFirstCloudinaryImg(){
        MovieCreateValidatorDTO movieCreateValidatorDTO = new MovieCreateValidatorDTO("title1",
                "description1", "gender1", "2h30",
                12, "data:image/webp;base64,sdvsdvsdv", "statusMovie1");

        var resultError = new BeanPropertyBindingResult(movieCreateValidatorDTO, "movieCreateValidatorDTO");

        when(cloudinaryUti.CreateImg(any(), anyInt(), anyInt())).thenReturn(
                ResultService.Fail("error create img cloudinary"));

        // Act
        var result = movieService.create(movieCreateValidatorDTO, resultError);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error create img cloudinary");
    }

    @Test
    public void should_Create_WithError_ErrorCreateSecondCloudinaryImg(){
        MovieCreateValidatorDTO movieCreateValidatorDTO = new MovieCreateValidatorDTO("title1",
                "description1", "gender1", "2h30",
                12, "data:image/webp;base64,sdvsdvsdv", "statusMovie1");

        var resultError = new BeanPropertyBindingResult(movieCreateValidatorDTO, "movieCreateValidatorDTO");

        when(cloudinaryUti.CreateImg(any(), anyInt(), anyInt())).thenReturn(ResultService.Ok
                (new CloudinaryCreate("publicId1", "imgUrl1")));

        when(cloudinaryUti.CreateImg(any(), anyInt(), anyInt())).thenReturn(
                ResultService.Fail("error create img cloudinary"));

        when(movieRepository.create(any())).thenReturn(new Movie());

        // Act
        var result = movieService.create(movieCreateValidatorDTO, resultError);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error create img cloudinary");
    }

    @Test
    public void should_Create_WithError_MovieRepository_Create_Null(){
        MovieCreateValidatorDTO movieCreateValidatorDTO = new MovieCreateValidatorDTO("title1",
                "description1", "gender1", "2h30",
                12, "data:image/webp;base64,sdvsdvsdv", "statusMovie1");

        var resultError = new BeanPropertyBindingResult(movieCreateValidatorDTO, "movieCreateValidatorDTO");

        when(cloudinaryUti.CreateImg(any(), anyInt(), anyInt())).thenReturn(ResultService.Ok
                (new CloudinaryCreate("publicId1", "imgUrl1")));

        when(movieRepository.create(any())).thenReturn(null);

        // Act
        var result = movieService.create(movieCreateValidatorDTO, resultError);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error when creating movie");
    }

    @Test
    public void should_DeleteMovie_WithoutErrors(){
        String movieDeleteId = "08b74c56-af65-4018-919f-07c42267b6fa";

        Movie movie = new Movie(null, null,
                null, null, null, null,
                null,"publicId1", null,
                "publicIdImgBackground1", null);

        when(movieRepository.getMovieByIdForDelete(any())).thenReturn(movie);
        when(cloudinaryUti.deleteImg(any())).thenReturn(
                ResultService.Ok("create cloudinary img successfully"));
        when(movieRepository.delete(any())).thenReturn(new Movie());

        // Act
        var result = movieService.deleteMovie(UUID.fromString(movieDeleteId));

        // Assert
        assertTrue(result.IsSuccess);
    }

    @Test
    public void should_DeleteMovie_WithError_getMovieByIdForDelete_Null(){
        String movieDeleteId = "08b74c56-af65-4018-919f-07c42267b6fa";

        when(movieRepository.getMovieByIdForDelete(any())).thenReturn(null);

        // Act
        var result = movieService.deleteMovie(UUID.fromString(movieDeleteId));

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "not found movie");
    }

    @Test
    public void should_DeleteMovie_ErrorCreateCloudinaryImg(){
        String movieDeleteId = "08b74c56-af65-4018-919f-07c42267b6fa";

        Movie movie = new Movie(null, null,
                null, null, null, null,
                null,"publicId1", null,
                "publicIdImgBackground1", null);

        when(movieRepository.getMovieByIdForDelete(any())).thenReturn(movie);
        when(cloudinaryUti.deleteImg(any())).thenReturn(
                ResultService.Fail("error when create cloudinary img"));

        // Act
        var result = movieService.deleteMovie(UUID.fromString(movieDeleteId));

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error when create cloudinary img");
    }

    @Test
    public void should_DeleteMovie_ErrorDeleteRepositoryMovie(){
        String movieDeleteId = "08b74c56-af65-4018-919f-07c42267b6fa";

        Movie movie = new Movie(null, null,
                null, null, null, null,
                null,"publicId1", null,
                "publicIdImgBackground1", null);

        when(movieRepository.getMovieByIdForDelete(any())).thenReturn(movie);
        when(cloudinaryUti.deleteImg(any())).thenReturn(
                ResultService.Ok("create cloudinary img successfully"));
        when(movieRepository.delete(any())).thenReturn(null);

        // Act
        var result = movieService.deleteMovie(UUID.fromString(movieDeleteId));

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error found when deleting movie in repository");
    }

    @Test
    public void should_UpdateMovieImg_WithoutErrors(){
        MovieUpdateValidatorDTO movieUpdateValidatorDTO = new MovieUpdateValidatorDTO(
                "ce11364b-3411-405b-93f3-c4ee2e3ece89", null,
                null, null, null,
                null
        );

        var resultError = new BeanPropertyBindingResult(movieUpdateValidatorDTO, "movieUpdateValidatorDTO");

        Movie movie = new Movie(null, null,
                null, null, null, null,
                null,"publicId1", null,
                "publicIdImgBackground1", null);

        when(movieRepository.getMovieByIdForDelete(any())).thenReturn(movie);
        when(cloudinaryUti.deleteImg(any())).thenReturn(ResultService.Ok("delete successfully"));
        when(cloudinaryUti.CreateImg(any(), anyInt(), anyInt())).thenReturn(
                ResultService.Ok(new CloudinaryCreate("publicId1", "imgUrl1")));
        when(movieRepository.updateImg(any())).thenReturn(
                new Movie());

        // Act
        var result = movieService.updateMovieImg(movieUpdateValidatorDTO, resultError);

        // Assert
        assertTrue(result.IsSuccess);
    }

    @Test
    public void should_UpdateMovieImg_WithError_Validate_DTO(){
        MovieUpdateValidatorDTO movieUpdateValidatorDTO = new MovieUpdateValidatorDTO(
                "ce11364b-3411-405b-93f3-c4ee2e3ece8", null,
                null, null, null,
                null
        );

        var resultError = new BeanPropertyBindingResult(movieUpdateValidatorDTO, "movieUpdateValidatorDTO");
        resultError.rejectValue("id", "Size", "Id should not be empty");

        List<ErrorValidation> errors = new ArrayList<>();
        var errorValidation = new ErrorValidation("title", "title should not be empty");
        errors.add(errorValidation);

        when(validateErrorsDTO.ValidateDTO(anyList())).thenReturn(errors);

        // Act
        var result = movieService.updateMovieImg(movieUpdateValidatorDTO, resultError);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error validate DTO");
        assertEquals(result.Errors, errors);
    }

    @Test
    public void should_UpdateMovieImg_ErrorValidateIdMovie(){
        MovieUpdateValidatorDTO movieUpdateValidatorDTO = new MovieUpdateValidatorDTO(
                "ce11364b-3411-405b-93f3-c4ee2e3ece8", null,
                null, null, null,
                null
        );

        var resultError = new BeanPropertyBindingResult(movieUpdateValidatorDTO, "movieUpdateValidatorDTO");

        // Act
        var result = movieService.updateMovieImg(movieUpdateValidatorDTO, resultError);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error is not a uuid valid");
    }

    @Test
    public void should_UpdateMovieImg_Error_getMovieByIdForDelete(){
        MovieUpdateValidatorDTO movieUpdateValidatorDTO = new MovieUpdateValidatorDTO(
                "ce11364b-3411-405b-93f3-c4ee2e3ece89", null,
                null, null, null,
                null
        );

        var resultError = new BeanPropertyBindingResult(movieUpdateValidatorDTO, "movieUpdateValidatorDTO");

        when(movieRepository.getMovieByIdForDelete(any())).thenReturn(null);

        // Act
        var result = movieService.updateMovieImg(movieUpdateValidatorDTO, resultError);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "not found movie");
    }

    @Test
    public void should_UpdateMovieImg_ErrorDeleteCloudinaryImg(){
        MovieUpdateValidatorDTO movieUpdateValidatorDTO = new MovieUpdateValidatorDTO(
                "ce11364b-3411-405b-93f3-c4ee2e3ece89", null,
                null, null, null,
                null
        );

        var resultError = new BeanPropertyBindingResult(movieUpdateValidatorDTO, "movieUpdateValidatorDTO");

        Movie movie = new Movie(null, null,
                null, null, null, null,
                null,"publicId1", null,
                "publicIdImgBackground1", null);

        when(movieRepository.getMovieByIdForDelete(any())).thenReturn(movie);
        when(cloudinaryUti.deleteImg(any())).thenReturn(
                ResultService.Fail("error delete cloudinary img"));

        // Act
        var result = movieService.updateMovieImg(movieUpdateValidatorDTO, resultError);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error delete cloudinary img");
    }

    @Test
    public void should_UpdateMovieImg_ErrorCreateCloudinaryImg(){
        MovieUpdateValidatorDTO movieUpdateValidatorDTO = new MovieUpdateValidatorDTO(
                "ce11364b-3411-405b-93f3-c4ee2e3ece89", null,
                null, null, null,
                null
        );

        var resultError = new BeanPropertyBindingResult(movieUpdateValidatorDTO, "movieUpdateValidatorDTO");

        Movie movie = new Movie(null, null,
                null, null, null, null,
                null,"publicId1", null,
                "publicIdImgBackground1", null);

        when(movieRepository.getMovieByIdForDelete(any())).thenReturn(movie);
        when(cloudinaryUti.deleteImg(any())).thenReturn(ResultService.Ok("delete successfully"));
        when(cloudinaryUti.CreateImg(any(), anyInt(), anyInt())).thenReturn(
                ResultService.Fail("error create cloudinary img"));

        // Act
        var result = movieService.updateMovieImg(movieUpdateValidatorDTO, resultError);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error create cloudinary img");
    }

    @Test
    public void should_UpdateMovieImg_ErrorRepositoryUpdateImg(){
        MovieUpdateValidatorDTO movieUpdateValidatorDTO = new MovieUpdateValidatorDTO(
                "ce11364b-3411-405b-93f3-c4ee2e3ece89", null,
                null, null, null,
                null
        );

        var resultError = new BeanPropertyBindingResult(movieUpdateValidatorDTO, "movieUpdateValidatorDTO");

        Movie movie = new Movie(null, null,
                null, null, null, null,
                null,"publicId1", null,
                "publicIdImgBackground1", null);

        when(movieRepository.getMovieByIdForDelete(any())).thenReturn(movie);
        when(cloudinaryUti.deleteImg(any())).thenReturn(ResultService.Ok("delete successfully"));
        when(cloudinaryUti.CreateImg(any(), anyInt(), anyInt())).thenReturn(
                ResultService.Ok(new CloudinaryCreate("publicId1", "imgUrl1")));
        when(movieRepository.updateImg(any())).thenReturn(
                null);

        // Act
        var result = movieService.updateMovieImg(movieUpdateValidatorDTO, resultError);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error when update movie");
    }
}