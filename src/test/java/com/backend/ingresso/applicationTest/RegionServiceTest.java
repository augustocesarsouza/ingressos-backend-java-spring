package com.backend.ingresso.applicationTest;

import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IRegionMapper;
import com.backend.ingresso.application.services.RegionService;
import com.backend.ingresso.domain.repositories.IRegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class RegionServiceTest {
    @Mock
    private IRegionRepository regionRepository;
    @Mock
    private IRegionMapper regionMapper;

    private RegionService regionService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        regionService = new RegionService(regionRepository,
                regionMapper);
    }

    @Test
    public void should_GetRegionIdByCityName_WithoutErrors(){
        String city = "city1";

        when(regionRepository.getRegionIdByCityName(any())).thenReturn(new RegionDTO());

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

        when(regionRepository.getIdByNameState(any())).thenReturn(new RegionDTO());

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
}
