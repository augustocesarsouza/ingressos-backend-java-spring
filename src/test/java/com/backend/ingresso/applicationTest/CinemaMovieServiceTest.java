package com.backend.ingresso.applicationTest;

import com.backend.ingresso.application.ErrorValidation;
import com.backend.ingresso.application.dto.CinemaDTO;
import com.backend.ingresso.application.dto.CinemaMovieDTO;
import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.backend.ingresso.application.dto.validations.cinemaMovieDTOs.CinemaMovieCreate;
import com.backend.ingresso.application.mappings.MappingClassInterface.ICinemaMovieMapper;
import com.backend.ingresso.application.services.CinemaMovieService;
import com.backend.ingresso.application.services.ResultService;
import com.backend.ingresso.application.services.interfaces.ICinemaService;
import com.backend.ingresso.application.services.interfaces.IMovieService;
import com.backend.ingresso.application.services.interfaces.IRegionService;
import com.backend.ingresso.domain.entities.CinemaMovie;
import com.backend.ingresso.domain.repositories.ICinemaMovieRepository;
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

public class CinemaMovieServiceTest {
    @Mock
    private ICinemaMovieRepository cinemaMovieRepository;
    @Mock
    private IRegionService regionService;
    @Mock
    private IValidateErrorsDTO validateErrorsDTO;
    @Mock
    private IMovieService movieService;
    @Mock
    private ICinemaService cinemaService;
    @Mock
    private ICinemaMovieMapper cinemaMovieMapper;
    private CinemaMovieService cinemaMovieService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        cinemaMovieService = new CinemaMovieService(cinemaMovieRepository,
                regionService, validateErrorsDTO, movieService,
                cinemaService, cinemaMovieMapper);
    }

    @Test
    public void should_GetByRegionCinemaIdAndMovieId_WithoutErrors(){
        String region = "seila";
        String movieId = "a3935e05-2745-4f6d-98b2-caba8fc6f998";

        RegionDTO regionDTO = new RegionDTO(
                UUID.fromString("1096c140-b59e-482b-980b-0ffdd2a2c203"));

        List<CinemaMovieDTO> cinemaMovieDTOS = new ArrayList<>();
        CinemaDTO cinemaDTO = new CinemaDTO(UUID.fromString("7c69718f-3b80-49aa-8012-875d7effb49b"),
        "nameCinema1", "district1", "ranking1");
        CinemaMovieDTO cinemaMovieDTO = new CinemaMovieDTO(
                null,null,cinemaDTO,
                null, null,null,null,"17:00D"
        );

        cinemaMovieDTOS.add(cinemaMovieDTO);

        when(regionService.getRegionIdByCity(any())).thenReturn(
                ResultService.Ok(regionDTO));
        when(cinemaMovieRepository.getByRegionCinemaIdAndMovieId(any(), any()))
                .thenReturn(cinemaMovieDTOS);

        var result = cinemaMovieService.getByRegionCinemaIdAndMovieId(
                region, UUID.fromString(movieId));

        assertTrue(result.IsSuccess);

        CinemaMovieDTO cinemaMovieDTO1 = result.Data.get(0);

        assertEquals(cinemaMovieDTO1.getCinema().getId().toString(), "7c69718f-3b80-49aa-8012-875d7effb49b");
        assertEquals(cinemaMovieDTO1.getCinema().getNameCinema(), "nameCinema1");
        assertEquals(cinemaMovieDTO1.getCinema().getDistrict(), "district1");
        assertEquals(cinemaMovieDTO1.getCinema().getRanking(), "ranking1");
        assertEquals(cinemaMovieDTO1.getScreeningSchedule(), "17:00D");
    }

    @Test
    public void should_GetByRegionCinemaIdAndMovieId_ErrorGetRegion(){
        String region = "seila";
        String movieId = "a3935e05-2745-4f6d-98b2-caba8fc6f998";

        when(regionService.getRegionIdByCity(any())).thenReturn(
                ResultService.Fail("error get region"));

        var result = cinemaMovieService.getByRegionCinemaIdAndMovieId(
                region, UUID.fromString(movieId));

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error region not found");
    }

    @Test
    public void should_Create_WithoutErrors(){
        String regionId = "65476a5d-8e12-4b9c-8b97-235c580b76e4";
        String movieId = "bcb20109-820a-4088-9f8e-b2829f436264";
        String cinemaId = "e5dd3910-bdec-4ee6-9d77-fe776e10204c";

        CinemaMovieCreate cinemaMovieDTO = new CinemaMovieCreate(
                null, cinemaId, movieId, regionId,
                "17:00D"
        );

        var resultError = new BeanPropertyBindingResult(cinemaMovieDTO, "cinemaMovieDTO");

        CinemaMovieDTO cinemaMovieDTO1 = new CinemaMovieDTO(
                null, UUID.fromString(cinemaId),
                null, UUID.fromString(movieId),
                null, UUID.fromString(regionId), null,
                "17:00D"
        );

        when(movieService.getCheckIfMovieExistsById(any()))
                .thenReturn(ResultService.Ok("movie exists"));

        when(cinemaService.getCheckIfCinemaExistsById(any()))
                .thenReturn(ResultService.Ok("cinema exists"));

        when(regionService.getCheckIfRegionExistsById(any()))
                .thenReturn(ResultService.Ok("region exists"));

        when(cinemaMovieRepository.create(any()))
                .thenReturn(new CinemaMovie());

        when(cinemaMovieMapper.cinemaMovieToCinemaMovieDto(any()))
                .thenReturn(cinemaMovieDTO1);

        var result = cinemaMovieService.create(cinemaMovieDTO, resultError);

        assertTrue(result.IsSuccess);

        CinemaMovieDTO cinemaMovieDTOCreate = result.Data;

        assertEquals(cinemaMovieDTO1.getScreeningSchedule(), cinemaMovieDTOCreate.getScreeningSchedule());
        assertEquals(cinemaMovieDTO1.getCinemaId(), cinemaMovieDTOCreate.getCinemaId());
        assertEquals(cinemaMovieDTO1.getMovieId(), cinemaMovieDTOCreate.getMovieId());
        assertEquals(cinemaMovieDTO1.getRegionId(), cinemaMovieDTOCreate.getRegionId());
    }

    @Test
    public void should_Create_ErrorDTOCreateNull(){
        var result = cinemaMovieService.create(null, null);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error DTO Create Null");
    }

    @Test
    public void should_Create_ErrorValidateDTO(){
        String regionId = "65476a5d-8e12-4b9c-8b97-235c580b76e4";
        String movieId = "bcb20109-820a-4088-9f8e-b2829f436264";

        CinemaMovieCreate cinemaMovieDTO = new CinemaMovieCreate(
                null, "", movieId, regionId,
                "17:00D"
        );

        var resultError = new BeanPropertyBindingResult(cinemaMovieDTO, "cinemaMovieDTO");
        resultError.rejectValue("cinemaId", "NotEmpty",
                "cinemaId should not be empty");

        List<ErrorValidation> list = new ArrayList<>();
        ErrorValidation errorValidation = new ErrorValidation(
                "cinemaId", "cinemaId should not be empty"
        );
        list.add(errorValidation);

        when(validateErrorsDTO.ValidateDTO(any()))
                .thenReturn(list);

        var result = cinemaMovieService.create(cinemaMovieDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error validate DTO");

        List<ErrorValidation> errorValidationsReturnCreate = result.Errors;
        ErrorValidation errorValidationReturnCreate = errorValidationsReturnCreate.get(0);

        assertEquals(errorValidationReturnCreate.getField(), errorValidation.getField());
        assertEquals(errorValidationReturnCreate.getField(), errorValidation.getField());
    }

    @Test
    public void should_Create_ErrorValidateUUIDMovieId(){
        String regionId = "65476a5d-8e12-4b9c-8b97-235c580b76e4";
        String movieId = "bcb20109-820a-4088-9f8e-b2829f6264";
        String cinemaId = "e5dd3910-bdec-4ee6-9d77-fe776e10204c";

        CinemaMovieCreate cinemaMovieDTO = new CinemaMovieCreate(
                null, cinemaId, movieId, regionId,
                "17:00D"
        );

        var resultError = new BeanPropertyBindingResult(cinemaMovieDTO, "cinemaMovieDTO");

        var result = cinemaMovieService.create(cinemaMovieDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error movieId, not is UUID valid");
    }

    @Test
    public void should_Create_ErrorValidateUUIDCinemaId(){
        String regionId = "65476a5d-8e12-4b9c-8b97-235c580b76e4";
        String movieId = "bcb20109-820a-4088-9f8e-b2829f436264";
        String cinemaId = "e5dd3910-bdec-4ee6-9d77-fe77e10204c";

        CinemaMovieCreate cinemaMovieDTO = new CinemaMovieCreate(
                null, cinemaId, movieId, regionId,
                "17:00D"
        );

        var resultError = new BeanPropertyBindingResult(cinemaMovieDTO, "cinemaMovieDTO");

        var result = cinemaMovieService.create(cinemaMovieDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error cinemaId, not is UUID valid");
    }

    @Test
    public void should_Create_ErrorValidateUUIDRegionId(){
        String regionId = "65476a5d-8e12-4b9c-8b97-235c580b6e4";
        String movieId = "bcb20109-820a-4088-9f8e-b2829f436264";
        String cinemaId = "e5dd3910-bdec-4ee6-9d77-fe776e10204c";

        CinemaMovieCreate cinemaMovieDTO = new CinemaMovieCreate(
                null, cinemaId, movieId, regionId,
                "17:00D"
        );

        var resultError = new BeanPropertyBindingResult(cinemaMovieDTO, "cinemaMovieDTO");

        var result = cinemaMovieService.create(cinemaMovieDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error regionId, not is UUID valid");
    }

    @Test
    public void should_Create_ErrorGetMovieNotExist(){
        String regionId = "65476a5d-8e12-4b9c-8b97-235c580b76e4";
        String movieId = "bcb20109-820a-4088-9f8e-b2829f436264";
        String cinemaId = "e5dd3910-bdec-4ee6-9d77-fe776e10204c";

        CinemaMovieCreate cinemaMovieDTO = new CinemaMovieCreate(
                null, cinemaId, movieId, regionId,
                "17:00D"
        );

        var resultError = new BeanPropertyBindingResult(cinemaMovieDTO, "cinemaMovieDTO");

        when(movieService.getCheckIfMovieExistsById(any()))
                .thenReturn(ResultService.Fail("movie does not exist"));

        var result = cinemaMovieService.create(cinemaMovieDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error movie not exist");
    }

    @Test
    public void should_Create_ErrorGetCinemaNotExist(){
        String regionId = "65476a5d-8e12-4b9c-8b97-235c580b76e4";
        String movieId = "bcb20109-820a-4088-9f8e-b2829f436264";
        String cinemaId = "e5dd3910-bdec-4ee6-9d77-fe776e10204c";

        CinemaMovieCreate cinemaMovieDTO = new CinemaMovieCreate(
                null, cinemaId, movieId, regionId,
                "17:00D"
        );

        var resultError = new BeanPropertyBindingResult(cinemaMovieDTO, "cinemaMovieDTO");

        when(movieService.getCheckIfMovieExistsById(any()))
                .thenReturn(ResultService.Ok("movie exists"));

        when(cinemaService.getCheckIfCinemaExistsById(any()))
                .thenReturn(ResultService.Fail("cinema does not exist"));

        var result = cinemaMovieService.create(cinemaMovieDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error cinema not exist");
    }

    @Test
    public void should_Create_ErrorGetRegionNotExist(){
        String regionId = "65476a5d-8e12-4b9c-8b97-235c580b76e4";
        String movieId = "bcb20109-820a-4088-9f8e-b2829f436264";
        String cinemaId = "e5dd3910-bdec-4ee6-9d77-fe776e10204c";

        CinemaMovieCreate cinemaMovieDTO = new CinemaMovieCreate(
                null, cinemaId, movieId, regionId,
                "17:00D"
        );

        var resultError = new BeanPropertyBindingResult(cinemaMovieDTO, "cinemaMovieDTO");

        when(movieService.getCheckIfMovieExistsById(any()))
                .thenReturn(ResultService.Ok("movie exists"));

        when(cinemaService.getCheckIfCinemaExistsById(any()))
                .thenReturn(ResultService.Ok("cinema exists"));

        when(regionService.getCheckIfRegionExistsById(any()))
                .thenReturn(ResultService.Fail("region does not exist"));

        var result = cinemaMovieService.create(cinemaMovieDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error region not exist");
    }

    @Test
    public void should_Create_ErrorCreateRepository(){
        String regionId = "65476a5d-8e12-4b9c-8b97-235c580b76e4";
        String movieId = "bcb20109-820a-4088-9f8e-b2829f436264";
        String cinemaId = "e5dd3910-bdec-4ee6-9d77-fe776e10204c";

        CinemaMovieCreate cinemaMovieDTO = new CinemaMovieCreate(
                null, cinemaId, movieId, regionId,
                "17:00D"
        );

        var resultError = new BeanPropertyBindingResult(cinemaMovieDTO, "cinemaMovieDTO");

        when(movieService.getCheckIfMovieExistsById(any()))
                .thenReturn(ResultService.Ok("movie exists"));

        when(cinemaService.getCheckIfCinemaExistsById(any()))
                .thenReturn(ResultService.Ok("cinema exists"));

        when(regionService.getCheckIfRegionExistsById(any()))
                .thenReturn(ResultService.Ok("region exists"));

        when(cinemaMovieRepository.create(any()))
                .thenReturn(null);

        var result = cinemaMovieService.create(cinemaMovieDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error create repository");
    }
}
