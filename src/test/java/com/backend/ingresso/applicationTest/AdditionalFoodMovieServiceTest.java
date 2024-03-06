package com.backend.ingresso.applicationTest;

import com.backend.ingresso.application.ErrorValidation;
import com.backend.ingresso.application.dto.AdditionalFoodMovieDTO;
import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.backend.ingresso.application.dto.validations.additionalFoodMovieDTOs.AdditionalFoodMovieCreate;
import com.backend.ingresso.application.mappings.MappingClassInterface.IAdditionalFoodMovieMapper;
import com.backend.ingresso.application.services.AdditionalFoodMovieService;
import com.backend.ingresso.application.services.ResultService;
import com.backend.ingresso.application.services.interfaces.IMovieService;
import com.backend.ingresso.data.cloudinaryUtil.CloudinaryCreate;
import com.backend.ingresso.data.utilityExternal.Interface.ICloudinaryUti;
import com.backend.ingresso.domain.entities.AdditionalFoodMovie;
import com.backend.ingresso.domain.repositories.IAdditionalFoodMovieRepository;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class AdditionalFoodMovieServiceTest {
    @Mock
    private IAdditionalFoodMovieRepository additionalFoodMovieRepository;
    @Mock
    private IValidateErrorsDTO validateErrorsDTO;
    @Mock
    private IMovieService movieService;
    @Mock
    private ICloudinaryUti cloudinaryUti;
    @Mock
    private IAdditionalFoodMovieMapper additionalFoodMovieMapper;

    private AdditionalFoodMovieService additionalFoodMovieService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        additionalFoodMovieService = new AdditionalFoodMovieService(additionalFoodMovieRepository,
                validateErrorsDTO, movieService,
                cloudinaryUti,additionalFoodMovieMapper);
    }

    @Test
    public void should_GetAllFoodMovie_WithoutErrors(){
        String movieId = "a3935e05-2745-4f6d-98b2-caba8fc6f998";

        List<AdditionalFoodMovieDTO> additionalFoodMovieDTOS = new ArrayList<>();
        AdditionalFoodMovieDTO additionalFoodMovieDTO = new AdditionalFoodMovieDTO(
                null, "title1",
                "price1", "fee1",
                "imgUrl1", null,
                null, null
        );
        additionalFoodMovieDTOS.add(additionalFoodMovieDTO);

        when(additionalFoodMovieRepository.getAllFoodMovie(any())).thenReturn(
                additionalFoodMovieDTOS);

        var result = additionalFoodMovieService.getAllFoodMovie(
                UUID.fromString(movieId));

        assertTrue(result.IsSuccess);

        AdditionalFoodMovieDTO additionalFoodMovieDTO1 = result.Data.get(0);

        assertEquals(additionalFoodMovieDTO1.getTitle(), additionalFoodMovieDTO.getTitle());
        assertEquals(additionalFoodMovieDTO1.getPrice(), additionalFoodMovieDTO.getPrice());
        assertEquals(additionalFoodMovieDTO1.getFee(), additionalFoodMovieDTO.getFee());
        assertEquals(additionalFoodMovieDTO1.getImgUrl(), additionalFoodMovieDTO.getImgUrl());
    }

    @Test
    public void should_GetAllFoodMovie_ListEmpty(){
        String movieId = "a3935e05-2745-4f6d-98b2-caba8fc6f998";

        List<AdditionalFoodMovieDTO> additionalFoodMovieDTOS = new ArrayList<>();

        when(additionalFoodMovieRepository.getAllFoodMovie(any())).thenReturn(
                additionalFoodMovieDTOS);

        var result = additionalFoodMovieService.getAllFoodMovie(
                UUID.fromString(movieId));

        assertTrue(result.IsSuccess);
        assertEquals(result.Data, additionalFoodMovieDTOS);
    }

    @Test
    public void should_Create_WithoutErrors(){
        AdditionalFoodMovieCreate additionalFoodMovieCreate = new AdditionalFoodMovieCreate(
                "title1", "price1",
                "fee1", "a3935e05-2745-4f6d-98b2-caba8fc6f998",
                "base64Img1"
        );

        var resultError = new BeanPropertyBindingResult(additionalFoodMovieCreate, "additionalFoodMovieCreate");

        CloudinaryCreate cloudinaryCreate = new CloudinaryCreate(
                "publicId1", "imgUrl1"
        );

        AdditionalFoodMovieDTO additionalFoodMovieDTO = new AdditionalFoodMovieDTO(
                UUID.fromString("f8d095e2-b7a9-4d39-a592-c7d24e516061"),
                "title1", "price1",
                "fee1", "imgUrl1", "publicId1",
                UUID.fromString("a3935e05-2745-4f6d-98b2-caba8fc6f998"), null
        );

        when(movieService.getCheckIfMovieExistsById(any())).thenReturn(
                ResultService.Ok(new MovieDTO()));

        when(cloudinaryUti.CreateImg(any(), anyInt(), anyInt())).thenReturn(
                ResultService.Ok(cloudinaryCreate));

        when(additionalFoodMovieRepository.create(any())).thenReturn(
                new AdditionalFoodMovie());

        when(additionalFoodMovieMapper.additionalFoodMovieToAdditionalFoodMovieDto(any()))
                .thenReturn(additionalFoodMovieDTO);

        var result = additionalFoodMovieService.create(
                additionalFoodMovieCreate, resultError);

        assertTrue(result.IsSuccess);

        AdditionalFoodMovieDTO resultCrete = result.Data;

        assertEquals(additionalFoodMovieDTO.getId(), resultCrete.getId());
        assertEquals(additionalFoodMovieDTO.getTitle(), resultCrete.getTitle());
        assertEquals(additionalFoodMovieDTO.getPrice(), resultCrete.getPrice());
        assertEquals(additionalFoodMovieDTO.getFee(), resultCrete.getFee());
        assertEquals(additionalFoodMovieDTO.getImgUrl(), resultCrete.getImgUrl());
        assertEquals(additionalFoodMovieDTO.getPublicId(), resultCrete.getPublicId());
        assertEquals(additionalFoodMovieDTO.getMovieId(), resultCrete.getMovieId());
    }

    @Test
    public void should_Create_DtoCreateNull(){
        var result = additionalFoodMovieService.create(
                null, null);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error DTO Create Null");
    }

    @Test
    public void should_Create_ErrorValidateDTO(){
        AdditionalFoodMovieCreate additionalFoodMovieCreate = new AdditionalFoodMovieCreate(
                "title1", "price1",
                "fee1", "a3935e05-2745-4f6d-98b2-caba8fc6f998",
                "base64Img1"
        );

        var resultError = new BeanPropertyBindingResult(additionalFoodMovieCreate, "additionalFoodMovieCreate");
        resultError.rejectValue("title", "NotEmpty",
                "title should be not empty");

        List<ErrorValidation> errorValidations = new ArrayList<>();
        ErrorValidation errorValidation = new ErrorValidation(
                "title", "title should be not empty"
        );
        errorValidations.add(errorValidation);

        when(validateErrorsDTO.ValidateDTO(any()))
                .thenReturn(errorValidations);

        var result = additionalFoodMovieService.create(
                additionalFoodMovieCreate, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error validate DTO");

        ErrorValidation errorValidation1 = result.Errors.get(0);

        assertEquals(errorValidation.getMessage(), errorValidation1.getMessage());
        assertEquals(errorValidation.getField(), errorValidation1.getField());
    }

    @Test
    public void should_Create_ErrorMovieIdNotIsUUIDValid(){
        AdditionalFoodMovieCreate additionalFoodMovieCreate = new AdditionalFoodMovieCreate(
                "title1", "price1",
                "fee1", "a3935e05-2745-4f6d-98b2-caba8f6f998",
                "base64Img1"
        );

        var resultError = new BeanPropertyBindingResult(additionalFoodMovieCreate, "additionalFoodMovieCreate");

        var result = additionalFoodMovieService.create(
                additionalFoodMovieCreate, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error movieId, not is UUID valid");
    }

    @Test
    public void should_Create_ErrorMovieNotExist(){
        AdditionalFoodMovieCreate additionalFoodMovieCreate = new AdditionalFoodMovieCreate(
                "title1", "price1",
                "fee1", "a3935e05-2745-4f6d-98b2-caba8fc6f998",
                "base64Img1"
        );

        var resultError = new BeanPropertyBindingResult(additionalFoodMovieCreate, "additionalFoodMovieCreate");

        when(movieService.getCheckIfMovieExistsById(any())).thenReturn(
                ResultService.Fail("error movie not exist"));

        var result = additionalFoodMovieService.create(
                additionalFoodMovieCreate, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error movie not exist");
    }

    @Test
    public void should_Create_ErrorCreateCloudinaryImg(){
        AdditionalFoodMovieCreate additionalFoodMovieCreate = new AdditionalFoodMovieCreate(
                "title1", "price1",
                "fee1", "a3935e05-2745-4f6d-98b2-caba8fc6f998",
                "base64Img1"
        );

        var resultError = new BeanPropertyBindingResult(additionalFoodMovieCreate, "additionalFoodMovieCreate");

        CloudinaryCreate cloudinaryCreate = new CloudinaryCreate(
                "publicId1", "imgUrl1"
        );

        when(movieService.getCheckIfMovieExistsById(any())).thenReturn(
                ResultService.Ok(new MovieDTO()));

        when(cloudinaryUti.CreateImg(any(), anyInt(), anyInt())).thenReturn(
                ResultService.Fail("error create img cloudinary"));

        var result = additionalFoodMovieService.create(
                additionalFoodMovieCreate, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error create img cloudinary");
    }

    @Test
    public void should_Create_ErrorCreateRepository(){
        AdditionalFoodMovieCreate additionalFoodMovieCreate = new AdditionalFoodMovieCreate(
                "title1", "price1",
                "fee1", "a3935e05-2745-4f6d-98b2-caba8fc6f998",
                "base64Img1"
        );

        var resultError = new BeanPropertyBindingResult(additionalFoodMovieCreate, "additionalFoodMovieCreate");

        CloudinaryCreate cloudinaryCreate = new CloudinaryCreate(
                "publicId1", "imgUrl1"
        );

        when(movieService.getCheckIfMovieExistsById(any())).thenReturn(
                ResultService.Ok(new MovieDTO()));

        when(cloudinaryUti.CreateImg(any(), anyInt(), anyInt())).thenReturn(
                ResultService.Ok(cloudinaryCreate));

        when(additionalFoodMovieRepository.create(any())).thenReturn(
                null);

        var result = additionalFoodMovieService.create(
                additionalFoodMovieCreate, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error create repository additional");
    }
}
