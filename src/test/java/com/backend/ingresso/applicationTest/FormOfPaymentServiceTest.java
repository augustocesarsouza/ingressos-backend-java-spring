package com.backend.ingresso.applicationTest;

import com.backend.ingresso.application.ErrorValidation;
import com.backend.ingresso.application.dto.*;
import com.backend.ingresso.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.backend.ingresso.application.dto.validations.formOfPaymentDTOs.FormOfPaymentCreate;
import com.backend.ingresso.application.mappings.MappingClassInterface.IFormOfPaymentMapper;
import com.backend.ingresso.application.services.FormOfPaymentService;
import com.backend.ingresso.application.services.ResultService;
import com.backend.ingresso.application.services.interfaces.IMovieService;
import com.backend.ingresso.domain.entities.FormOfPayment;
import com.backend.ingresso.domain.repositories.IFormOfPaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class FormOfPaymentServiceTest {
    @Mock
    private IFormOfPaymentRepository formOfPaymentRepository;
    @Mock
    private IFormOfPaymentMapper formOfPaymentMapper;
    @Mock
    private IMovieService movieService;
    @Mock
    private IValidateErrorsDTO validateErrorsDTO;

    private FormOfPaymentService formOfPaymentService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        formOfPaymentService = new FormOfPaymentService(formOfPaymentRepository,
                formOfPaymentMapper, movieService,
                validateErrorsDTO);
    }

    @Test
    public void should_GetMovieIdInfo_WithoutErrors(){
        String movieId = "a3935e05-2745-4f6d-98b2-caba8fc6f998";

        List<FormOfPaymentDTO> cinemaMovieDTOS = new ArrayList<>();

        FormOfPaymentDTO formOfPaymentDTO = new FormOfPaymentDTO(
                null, "formName1", "price1",
                null, null
        );
        cinemaMovieDTOS.add(formOfPaymentDTO);

        when(formOfPaymentRepository.getMovieIDInfo(any())).thenReturn(
                cinemaMovieDTOS);

        var result = formOfPaymentService.getMovieIdInfo(
                UUID.fromString(movieId));

        assertTrue(result.IsSuccess);

        FormOfPaymentDTO formOfPaymentDTO1 = result.Data.get(0);

        assertEquals(formOfPaymentDTO1.getFormName(), formOfPaymentDTO.getFormName());
        assertEquals(formOfPaymentDTO1.getPrice(), formOfPaymentDTO.getPrice());
    }

    @Test
    public void should_GetMovieIdInfo_ListEmpty(){
        String movieId = "a3935e05-2745-4f6d-98b2-caba8fc6f998";

        List<FormOfPaymentDTO> cinemaMovieDTOS = new ArrayList<>();

        when(formOfPaymentRepository.getMovieIDInfo(any())).thenReturn(
                cinemaMovieDTOS);

        var result = formOfPaymentService.getMovieIdInfo(
                UUID.fromString(movieId));

        assertTrue(result.IsSuccess);
        assertEquals(result.Data, cinemaMovieDTOS);
    }

    @Test
    public void should_Create_WithoutErrors(){
        FormOfPaymentCreate formOfPaymentCreate = new FormOfPaymentCreate(
                "formName1", "price1", "a3935e05-2745-4f6d-98b2-caba8fc6f998"
        );

        FormOfPaymentDTO formOfPaymentRepoCreate = new FormOfPaymentDTO(
                null, "formName1", "price1",
                UUID.fromString("a3935e05-2745-4f6d-98b2-caba8fc6f998"), null
        );

        var resultError = new BeanPropertyBindingResult(formOfPaymentCreate, "formOfPaymentCreate");

        when(movieService.getCheckIfMovieExistsById(any()))
                .thenReturn(ResultService.Ok(new MovieDTO()));

        when(formOfPaymentRepository.create(any()))
                .thenReturn(new FormOfPayment());

        when(formOfPaymentMapper.formOfPaymentToFormOfPaymentDto(any()))
                .thenReturn(formOfPaymentRepoCreate);

        var result = formOfPaymentService.create(
                formOfPaymentCreate, resultError);

        assertTrue(result.IsSuccess);

        FormOfPaymentDTO formOfPaymentDTO1 = result.Data;

        assertEquals(formOfPaymentDTO1.getFormName(), formOfPaymentRepoCreate.getFormName());
        assertEquals(formOfPaymentDTO1.getPrice(), formOfPaymentRepoCreate.getPrice());
    }

    @Test
    public void should_Create_ErrorDTO(){
        var result = formOfPaymentService.create(
                null, null);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error DTO Create null");
    }

    @Test
    public void should_Create_ErrorValidateDTOCreate(){
        FormOfPaymentCreate formOfPaymentCreate = new FormOfPaymentCreate(
                "formName1", "price1", "a3935e05-2745-4f6d-98b2-caba8fc6f998"
        );

        var resultError = new BeanPropertyBindingResult(formOfPaymentCreate, "formOfPaymentCreate");
        resultError.rejectValue("formName", "NotEmpty", "formName should not be empty");

        List<ErrorValidation> errorValidations = new ArrayList<>();
        ErrorValidation errorValidation = new ErrorValidation(
                "formName", "formName should not be empty"
        );
        errorValidations.add(errorValidation);

        when(validateErrorsDTO.ValidateDTO(any()))
                .thenReturn(errorValidations);

        var result = formOfPaymentService.create(
                formOfPaymentCreate, resultError);

        assertFalse(result.IsSuccess);

        ErrorValidation errorValidationReturn = result.Errors.get(0);

        assertEquals(errorValidation.getField(), errorValidationReturn.getField());
        assertEquals(errorValidation.getMessage(), errorValidationReturn.getMessage());
    }

    @Test
    public void should_Create_ErrorMovieIdInvalid(){
        FormOfPaymentCreate formOfPaymentCreate = new FormOfPaymentCreate(
                "formName1", "price1", "a3935e05-2745-4f6d-98b2-caba8f6f998"
        );

        var resultError = new BeanPropertyBindingResult(formOfPaymentCreate, "formOfPaymentCreate");

        var result = formOfPaymentService.create(
                formOfPaymentCreate, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error movieId, not is UUID valid");
    }

    @Test
    public void should_Create_ErrorFindMovieNotExist(){
        FormOfPaymentCreate formOfPaymentCreate = new FormOfPaymentCreate(
                "formName1", "price1", "a3935e05-2745-4f6d-98b2-caba8fc6f998"
        );

        var resultError = new BeanPropertyBindingResult(formOfPaymentCreate, "formOfPaymentCreate");

        when(movieService.getCheckIfMovieExistsById(any()))
                .thenReturn(ResultService.Fail("error get movie"));

        var result = formOfPaymentService.create(
                formOfPaymentCreate, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error movie not exist");
    }

    @Test
    public void should_Create_ErrorCreateRepository(){
        FormOfPaymentCreate formOfPaymentCreate = new FormOfPaymentCreate(
                "formName1", "price1", "a3935e05-2745-4f6d-98b2-caba8fc6f998"
        );

        var resultError = new BeanPropertyBindingResult(formOfPaymentCreate, "formOfPaymentCreate");

        when(movieService.getCheckIfMovieExistsById(any()))
                .thenReturn(ResultService.Ok(new MovieDTO()));

        when(formOfPaymentRepository.create(any()))
                .thenReturn(null);

        var result = formOfPaymentService.create(
                formOfPaymentCreate, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error create repository");
    }
}
