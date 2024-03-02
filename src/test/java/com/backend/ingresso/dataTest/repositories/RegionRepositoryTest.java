package com.backend.ingresso.dataTest.repositories;

import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.data.context.RegionRepositoryJPA;
import com.backend.ingresso.data.repositories.RegionRepository;
import com.backend.ingresso.domain.entities.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class RegionRepositoryTest {
    private RegionRepository regionRepository;

    @Mock
    private RegionRepositoryJPA regionRepositoryJPA;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        regionRepository = new RegionRepository(regionRepositoryJPA);
    }

    @Test
    public void test_GetRegionIdByCityName_Exists() {
        String city = "city1";

        when(regionRepositoryJPA.getRegionIdByCityName(any())).thenReturn(new RegionDTO());

        RegionDTO regionDTO = regionRepository.getRegionIdByCityName(city);

        assertNotNull(regionDTO);
    }

    @Test
    public void test_GetRegionIdByCityName_Return_Null() {
        String city = "city1";

        when(regionRepositoryJPA.getRegionIdByCityName(any())).thenReturn(null);

        RegionDTO regionDTO = regionRepository.getRegionIdByCityName(city);

        assertNull(regionDTO);
    }

    @Test
    public void test_GetIdByNameState_Exists() {
        String state = "state1";

        when(regionRepositoryJPA.getIdByNameState(any())).thenReturn(new RegionDTO());

        RegionDTO regionDTO = regionRepository.getIdByNameState(state);

        assertNotNull(regionDTO);
    }

    @Test
    public void test_GetIdByNameState_Return_Null() {
        String state = "state1";

        when(regionRepositoryJPA.getIdByNameState(any())).thenReturn(null);

        RegionDTO regionDTO = regionRepository.getIdByNameState(state);

        assertNull(regionDTO);
    }

    @Test
    public void test_Create_Exists() {
        Region region = new Region(null, null, null);

        when(regionRepositoryJPA.save(any())).thenReturn(new Region());

        Region regionCreate = regionRepository.create(region);

        assertNotNull(regionCreate);
    }

    @Test
    public void test_Create_RegionIsNull() {
        Region regionCreate = regionRepository.create(null);

        assertNull(regionCreate);
    }

    @Test
    public void test_Create_ReturnNullSave() {
        Region region = new Region(null, null, null);

        when(regionRepositoryJPA.save(any())).thenReturn(null);

        Region regionCreate = regionRepository.create(region);

        assertNull(regionCreate);
    }
}
