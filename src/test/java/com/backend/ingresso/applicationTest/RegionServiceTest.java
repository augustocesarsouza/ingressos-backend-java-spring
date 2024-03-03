package com.backend.ingresso.applicationTest;

import com.backend.ingresso.application.ErrorValidation;
import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IRegionMapper;
import com.backend.ingresso.application.services.RegionService;
import com.backend.ingresso.domain.entities.Region;
import com.backend.ingresso.domain.repositories.IRegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class RegionServiceTest {
    @Mock
    private IRegionRepository regionRepository;
    @Mock
    private IValidateErrorsDTO validateErrorsDTO;
    @Mock
    private IRegionMapper regionMapper;

    private RegionService regionService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        regionService = new RegionService(
                regionRepository, regionMapper, validateErrorsDTO);
    }

    @Test
    public void should_GetRegionIdByCityName_WithoutErrors(){
        String city = "city1";

        when(regionRepository.getRegionIdByCityName(any())).thenReturn(new Region());

        // Act
        var result = regionService.getRegionIdByCityName(city);

        // Assert
        assertTrue(result.IsSuccess);
    }

    @Test
    public void should_GetRegionIdByCityName_With_Error_Null(){
        String city = "city1";

        when(regionRepository.getRegionIdByCityName(any())).thenReturn(null);

        // Act
        var result = regionService.getRegionIdByCityName(city);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "not found region");
    }

    @Test
    public void should_GetIdByNameState_WithoutErrors(){
        String state = "state1";

        when(regionRepository.getIdByNameState(any())).thenReturn(new Region());

        // Act
        var result = regionService.getIdByNameState(state);

        // Assert
        assertTrue(result.IsSuccess);
    }

    @Test
    public void should_GetIdByNameState_With_Error_Null(){
        String state = "state1";

        when(regionRepository.getIdByNameState(any())).thenReturn(null);

        // Act
        var result = regionService.getIdByNameState(state);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "not found region");
    }

    @Test
    public void should_Create_WithoutErrors(){
        RegionDTO regionDTO = new RegionDTO(null, null,
                null);

        var resultError = new BeanPropertyBindingResult(regionDTO, "regionDTO");

        when(regionRepository.create(any())).thenReturn(new Region());

        // Act
        var result = regionService.create(regionDTO, resultError);

        // Assert
        assertTrue(result.IsSuccess);
    }

    @Test
    public void should_Create_RegionDTONull(){
        // Act
        var result = regionService.create(null, null);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error DTO Null");
    }

    @Test
    public void should_Create_ErrorDtoValidate(){
        RegionDTO regionDTO = new RegionDTO(null, null,
                null);

        var resultError = new BeanPropertyBindingResult(regionDTO, "regionDTO");
        resultError.rejectValue("state", "NotEmpty",
                "state should not be empty");

        List<ErrorValidation> errors = new ArrayList<>();
        var errorValidation = new ErrorValidation("state", "state should not be empty");
        errors.add(errorValidation);

        when(regionRepository.create(any())).thenReturn(new Region());
        when(validateErrorsDTO.ValidateDTO(any())).thenReturn(errors);

        // Act
        var result = regionService.create(regionDTO, resultError);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Errors, errors);
        assertEquals(result.Message, "error validate DTO");
    }

    @Test
    public void should_Create_ErrorCreateRegion(){
        RegionDTO regionDTO = new RegionDTO(null, null,
                null);

        var resultError = new BeanPropertyBindingResult(regionDTO, "regionDTO");

        when(regionRepository.create(any())).thenReturn(null);

        // Act
        var result = regionService.create(regionDTO, resultError);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "not create region");
    }
}
