package com.backend.ingresso.dataTest.repositories;

import com.backend.ingresso.application.dto.TheatreDTO;
import com.backend.ingresso.data.context.TheatreRepositoryJPA;
import com.backend.ingresso.data.repositories.TheatreRepository;
import com.backend.ingresso.domain.entities.Theatre;
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

public class TheatreRepositoryTest {
    private TheatreRepository theatreRepository;
    @Mock
    private TheatreRepositoryJPA theatreRepositoryJPA;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        theatreRepository = new TheatreRepository(theatreRepositoryJPA);
    }

    @Test
    public void test_GetById_ReturnTheatre(){
        String theatreId = "1b8a2a80-b7e6-406a-8744-8aa67f83e6c6";

        when(theatreRepositoryJPA.getByTheatreId(any())).thenReturn(new Theatre());

        Theatre result = theatreRepository.getById(UUID.fromString(theatreId));

        assertNotNull(result);
    }

    @Test
    public void test_GetById_ReturnNull(){
        String theatreId = "1b8a2a80-b7e6-406a-8744-8aa67f83e6c6";

        when(theatreRepositoryJPA.getByTheatreId(any())).thenReturn(null);

        Theatre result = theatreRepository.getById(UUID.fromString(theatreId));

        assertNull(result);
    }

    @Test
    public void test_GetById_ReturnNullParameterId(){
        Theatre result = theatreRepository.getById(null);

        assertNull(result);
    }

    @Test
    public void test_GetByTheatreIdIdPublicId_ReturnTheatre(){
        String theatreId = "1b8a2a80-b7e6-406a-8744-8aa67f83e6c6";

        when(theatreRepositoryJPA.getByTheatreIdIdPublicId(any())).thenReturn(new Theatre());

        Theatre result = theatreRepository.getByTheatreIdIdPublicId(UUID.fromString(theatreId));

        assertNotNull(result);
    }

    @Test
    public void test_GetByTheatreIdIdPublicId_ReturnNull(){
        String theatreId = "1b8a2a80-b7e6-406a-8744-8aa67f83e6c6";

        when(theatreRepositoryJPA.getByTheatreIdIdPublicId(any())).thenReturn(null);

        Theatre result = theatreRepository.getByTheatreIdIdPublicId(UUID.fromString(theatreId));

        assertNull(result);
    }

    @Test
    public void test_GetByTheatreIdIdPublicId_ReturnNullParameterId(){
        Theatre result = theatreRepository.getByTheatreIdIdPublicId(null);

        assertNull(result);
    }

    @Test
    public void test_GetByIdAllProps_ReturnTheatre(){
        String theatreId = "1b8a2a80-b7e6-406a-8744-8aa67f83e6c6";

        when(theatreRepositoryJPA.findById(any())).thenReturn(Optional.of(new Theatre()));

        Theatre result = theatreRepository.getByIdAllProps(UUID.fromString(theatreId));

        assertNotNull(result);
    }

    @Test
    public void test_GetByIdAllProps_ReturnNull(){
        String theatreId = "1b8a2a80-b7e6-406a-8744-8aa67f83e6c6";

        when(theatreRepositoryJPA.findById(any())).thenReturn(Optional.empty());

        Theatre result = theatreRepository.getByIdAllProps(UUID.fromString(theatreId));

        assertNull(result);
    }

    @Test
    public void test_GetByIdAllProps_ReturnNullParameterId(){
        Theatre result = theatreRepository.getByIdAllProps(null);

        assertNull(result);
    }

    @Test
    public void test_GetAllTheatreByRegionId_ReturnTheatre(){
        String theatreId = "1b8a2a80-b7e6-406a-8744-8aa67f83e6c6";

        List<TheatreDTO> theatreDTOS = new ArrayList<>();

        when(theatreRepositoryJPA.getAllTheatreByRegionId(any())).thenReturn(theatreDTOS);

        List<TheatreDTO> result = theatreRepository.getAllTheatreByRegionId(UUID.fromString(theatreId));

        assertNotNull(result);
    }

    @Test
    public void test_GetAllTheatreByRegionId_ReturnNull(){
        String theatreId = "1b8a2a80-b7e6-406a-8744-8aa67f83e6c6";

        when(theatreRepositoryJPA.getAllTheatreByRegionId(any())).thenReturn(null);

        List<TheatreDTO> result = theatreRepository.getAllTheatreByRegionId(UUID.fromString(theatreId));

        assertNull(result);
    }

    @Test
    public void test_GetAllTheatreByRegionId_ReturnNullParameterId(){
        List<TheatreDTO> result = theatreRepository.getAllTheatreByRegionId(null);

        assertNull(result);
    }

    @Test
    public void test_Create_ReturnTheatreCreate(){
        Theatre theatre = new Theatre();

        when(theatreRepositoryJPA.save(any())).thenReturn(theatre);

        Theatre result = theatreRepository.create(theatre);

        assertNotNull(result);
    }

    @Test
    public void test_Create_ReturnNullObjParameter(){
        Theatre result = theatreRepository.create(null);

        assertNull(result);
    }

    @Test
    public void test_Create_ReturnNullSaveObj(){
        Theatre theatre = new Theatre();

        when(theatreRepositoryJPA.save(any())).thenReturn(null);

        Theatre result = theatreRepository.create(theatre);

        assertNull(result);
    }

    @Test
    public void test_UpdateImg_ReturnTheatreUpdateImg(){
        Theatre theatre = new Theatre(UUID.fromString("1b8a2a80-b7e6-406a-8744-8aa67f83e6c6"),
                null, null, null, null,
                null, null, "publicId1", "imgUrl1");

        when(theatreRepositoryJPA.findById(any())).thenReturn(Optional.of(new Theatre()));
        when(theatreRepositoryJPA.save(any())).thenReturn(theatre);

        Theatre result = theatreRepository.updateImg(theatre);

        assertNotNull(result);
        assertEquals(result.getPublicId(), "publicId1");
        assertEquals(result.getImgUrl(), "imgUrl1");
    }

    @Test
    public void test_UpdateImg_ReturnNullObjParameter(){
        Theatre result = theatreRepository.updateImg(null);

        assertNull(result);
    }

    @Test
    public void test_UpdateImg_ReturnNullFindById(){
        Theatre theatre = new Theatre(UUID.fromString("1b8a2a80-b7e6-406a-8744-8aa67f83e6c6"),
                null, null, null, null,
                null, null, "publicId1", "imgUrl1");

        when(theatreRepositoryJPA.findById(any())).thenReturn(Optional.empty());

        Theatre result = theatreRepository.updateImg(theatre);

        assertNull(result);
    }

    @Test
    public void test_UpdateImg_ReturnNullSaveTheatreUpdate(){
        Theatre theatre = new Theatre(UUID.fromString("1b8a2a80-b7e6-406a-8744-8aa67f83e6c6"),
                null, null, null, null,
                null, null, "publicId1", "imgUrl1");

        when(theatreRepositoryJPA.findById(any())).thenReturn(Optional.of(new Theatre()));
        when(theatreRepositoryJPA.save(any())).thenReturn(null);

        Theatre result = theatreRepository.updateImg(theatre);

        assertNull(result);
    }

    @Test
    public void test_Delete_ReturnTheatreUpdateImg(){
        String theatreId = "86e2289b-c874-42ff-b537-6d089dee2629";

        when(theatreRepositoryJPA.findById(any())).thenReturn(Optional.of(new Theatre()));

        Theatre result = theatreRepository.delete(UUID.fromString(theatreId));

        assertNotNull(result);
    }

    @Test
    public void test_Delete_ReturnNullFindById(){
        String theatreId = "86e2289b-c874-42ff-b537-6d089dee2629";

        when(theatreRepositoryJPA.findById(any())).thenReturn(Optional.empty());

        Theatre result = theatreRepository.delete(UUID.fromString(theatreId));

        assertNull(result);
    }

    @Test
    public void test_Delete_ReturnNullParameterId(){
        Theatre result = theatreRepository.delete(null);

        assertNull(result);
    }
}
