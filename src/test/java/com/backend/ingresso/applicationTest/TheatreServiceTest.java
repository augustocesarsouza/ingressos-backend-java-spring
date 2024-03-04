package com.backend.ingresso.applicationTest;

import com.backend.ingresso.application.ErrorValidation;
import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.backend.ingresso.application.dto.validations.theatreDTOs.TheatreCreateValidatorDTO;
import com.backend.ingresso.application.dto.validations.theatreDTOs.TheatreUpdateValidatorDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.ITheatreMapper;
import com.backend.ingresso.application.services.ResultService;
import com.backend.ingresso.application.services.TheatreService;
import com.backend.ingresso.application.services.interfaces.IRegionService;
import com.backend.ingresso.data.cloudinaryUtil.CloudinaryCreate;
import com.backend.ingresso.data.utilityExternal.Interface.ICloudinaryUti;
import com.backend.ingresso.domain.entities.Theatre;
import com.backend.ingresso.domain.repositories.ITheatreRepository;
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

public class TheatreServiceTest {
    @Mock
    private ITheatreRepository theatreRepository;
    @Mock
    private IRegionService regionService;
    @Mock
    private IValidateErrorsDTO validateErrorsDTO;
    @Mock
    private ICloudinaryUti cloudinaryUti;
    @Mock
    private ITheatreMapper theatreMapper;

    private TheatreService theatreService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        theatreService = new TheatreService(theatreRepository,
                theatreMapper, validateErrorsDTO,
                cloudinaryUti, regionService);
    }

    @Test
    public void test_GetAllTheatreByRegionId_WithoutErrors(){
        String region = "region1";

        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setId(UUID.fromString("55866691-4de2-409f-8d4f-45ab0495d3ce"));

        when(regionService.getIdByNameState(any())).thenReturn(ResultService.Ok(regionDTO));
        when(theatreRepository.getAllTheatreByRegionId(any())).thenReturn(new ArrayList<>());

        var result = theatreService.getAllTheatreByRegionId(region);

        assertTrue(result.IsSuccess);
    }

    @Test
    public void test_GetAllTheatreByRegionId_ErrorGetObjRegionObj(){
        String region = "region1";

        when(regionService.getIdByNameState(any())).thenReturn(
                ResultService.Fail("error get region OBJ"));

        var result = theatreService.getAllTheatreByRegionId(region);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error get region OBJ");
    }

    @Test
    public void test_GetAllTheatreByRegionId_ErrorGetListTheatre(){
        String region = "region1";

        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setId(UUID.fromString("55866691-4de2-409f-8d4f-45ab0495d3ce"));

        when(regionService.getIdByNameState(any())).thenReturn(ResultService.Ok(regionDTO));
        when(theatreRepository.getAllTheatreByRegionId(any())).thenReturn(null);

        var result = theatreService.getAllTheatreByRegionId(region);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error get all theatre null");

    }

    @Test
    public void test_Create_WithoutErrors(){
        TheatreCreateValidatorDTO theatreCreateValidatorDTO = new TheatreCreateValidatorDTO(
                null, "title1", "description1", null, "location1",
                "action", "category", null,
                null, "05/10/1999 19:00","base64test"
        );

        var resultError = new BeanPropertyBindingResult(theatreCreateValidatorDTO, "theatreCreateValidatorDTO");

        CloudinaryCreate cloudinaryCreate = new CloudinaryCreate(
                "publicId1", "imgUrl1"
        );
        when(cloudinaryUti.CreateImg(any(), anyInt(), anyInt()))
                .thenReturn(ResultService.Ok(cloudinaryCreate));
        when(theatreRepository.create(any())).thenReturn(new Theatre());

        var result = theatreService.create(theatreCreateValidatorDTO, resultError);

        assertTrue(result.IsSuccess);
    }

    @Test
    public void test_Create_ErrorValidateDTO(){
        TheatreCreateValidatorDTO theatreCreateValidatorDTO = new TheatreCreateValidatorDTO(
                null, "title1", "description1", null, "location1",
                "action", "category", null,
                null, "05/10/1999 19:00","base64test"
        );

        var resultError = new BeanPropertyBindingResult(theatreCreateValidatorDTO, "theatreCreateValidatorDTO");
        resultError.rejectValue("title", "NotEmpty", "title should not be empty");

        List<ErrorValidation> errorValidations = new ArrayList<>();
        errorValidations.add(new ErrorValidation("title", "title should not be empty"));

        when(validateErrorsDTO.ValidateDTO(any()))
                .thenReturn(errorValidations);

        var result = theatreService.create(theatreCreateValidatorDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error validate DTO");
        assertEquals(result.Errors, errorValidations);
    }

    @Test
    public void test_Create_ErrorDateNull(){
        TheatreCreateValidatorDTO theatreCreateValidatorDTO = new TheatreCreateValidatorDTO(
                null, "title1", "description1", null, "location1",
                "action", "category", null,
                null, null,"base64test"
        );

        var resultError = new BeanPropertyBindingResult(theatreCreateValidatorDTO, "theatreCreateValidatorDTO");

        var result = theatreService.create(theatreCreateValidatorDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error dateString is null");
    }

    @Test
    public void test_Create_DateStringNotMatchesPattern(){
        TheatreCreateValidatorDTO theatreCreateValidatorDTO = new TheatreCreateValidatorDTO(
                null, "title1", "description1", null, "location1",
                "action", "category", null,
                null, "05/10/1999 19:","base64test"
        );

        var resultError = new BeanPropertyBindingResult(theatreCreateValidatorDTO, "theatreCreateValidatorDTO");


        var result = theatreService.create(theatreCreateValidatorDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error data should be in this format dd/mm/AAAA mm:HH");
    }

    @Test
    public void test_Create_ErrorCreateImgCloudinary(){
        TheatreCreateValidatorDTO theatreCreateValidatorDTO = new TheatreCreateValidatorDTO(
                null, "title1", "description1", null, "location1",
                "action", "category", null,
                null, "05/10/1999 19:00","base64test"
        );

        var resultError = new BeanPropertyBindingResult(theatreCreateValidatorDTO, "theatreCreateValidatorDTO");

        when(cloudinaryUti.CreateImg(any(), anyInt(), anyInt()))
            .thenReturn(ResultService.Fail("error create img cloudinary"));

        var result = theatreService.create(theatreCreateValidatorDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error create img cloudinary");
    }

    @Test
    public void test_Create_ErrorNullCreateRepository(){
        TheatreCreateValidatorDTO theatreCreateValidatorDTO = new TheatreCreateValidatorDTO(
                null, "title1", "description1", null, "location1",
                "action", "category", null,
                null, "05/10/1999 19:00","base64test"
        );

        var resultError = new BeanPropertyBindingResult(theatreCreateValidatorDTO, "theatreCreateValidatorDTO");

        CloudinaryCreate cloudinaryCreate = new CloudinaryCreate(
                "publicId1", "imgUrl1"
        );
        when(cloudinaryUti.CreateImg(any(), anyInt(), anyInt()))
                .thenReturn(ResultService.Ok(cloudinaryCreate));
        when(theatreRepository.create(any())).thenReturn(null);

        var result = theatreService.create(theatreCreateValidatorDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error create Theatre");
    }

    @Test
    public void test_DeleteTheatre_WithoutErrors(){
        String theatreId = "ec9b8999-052c-474d-b13b-ec2402df3bc0";

        Theatre theatre = new Theatre(null, null,
                null, null, null,
                null, null, "publicId1", null);

        when(theatreRepository.getByTheatreIdIdPublicId(any()))
                .thenReturn(theatre);
        when(cloudinaryUti.deleteImg(any())).thenReturn(
                ResultService.Ok("")
        );
        when(theatreRepository.delete(any()))
                .thenReturn(new Theatre());

        var result = theatreService.deleteTheatre(theatreId);

        assertTrue(result.IsSuccess);
    }

    @Test
    public void test_DeleteTheatre_ErrorIdIsNotValid(){
        String theatreId = "ec9b8999-02c-474d-b13b-ec2402df3bc0";

        var result = theatreService.deleteTheatre(theatreId);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error theatreId informed It is not valid");
    }

    @Test
    public void test_DeleteTheatre_ErrorNotFoundTheatre(){
        String theatreId = "ec9b8999-052c-474d-b13b-ec2402df3bc0";

        when(theatreRepository.getByTheatreIdIdPublicId(any()))
                .thenReturn(null);

        var result = theatreService.deleteTheatre(theatreId);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "not found theatre");
    }

    @Test
    public void test_DeleteTheatre_ErrorCreateCloudinaryImg(){
        String theatreId = "ec9b8999-052c-474d-b13b-ec2402df3bc0";

        Theatre theatre = new Theatre(null, null,
                null, null, null,
                null, null, "publicId1", null);

        when(theatreRepository.getByTheatreIdIdPublicId(any()))
                .thenReturn(theatre);
        when(cloudinaryUti.deleteImg(any())).thenReturn(
                ResultService.Fail("error delete img cloudinary")
        );

        var result = theatreService.deleteTheatre(theatreId);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error delete img cloudinary");
    }

    @Test
    public void test_DeleteTheatre_ErrorDeleteRepository(){
        String theatreId = "ec9b8999-052c-474d-b13b-ec2402df3bc0";

        Theatre theatre = new Theatre(null, null,
                null, null, null,
                null, null, "publicId1", null);

        when(theatreRepository.getByTheatreIdIdPublicId(any()))
                .thenReturn(theatre);
        when(cloudinaryUti.deleteImg(any())).thenReturn(
                ResultService.Ok("")
        );
        when(theatreRepository.delete(any()))
                .thenReturn(null);

        var result = theatreService.deleteTheatre(theatreId);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error found when deleting theatre in repository");
    }

    @Test
    public void test_UpdateImgTheatre_WithoutErrors(){
        TheatreUpdateValidatorDTO theatreUpdateValidatorDTO = new TheatreUpdateValidatorDTO(
                "0571e38a-b983-465c-a568-04d6894c8fde", "base64Img"
        );

        var resultError = new BeanPropertyBindingResult(theatreUpdateValidatorDTO, "theatreUpdateValidatorDTO");

        Theatre theatre = new Theatre(null, null,
                null, null, null,
                null, null, "publicId1", null);

        CloudinaryCreate cloudinaryCreate = new CloudinaryCreate(
                "publicId1", "imgUrl1"
        );

        when(theatreRepository.getByTheatreIdIdPublicId(any()))
                .thenReturn(theatre);
        when(cloudinaryUti.deleteImg(any())).thenReturn(
                ResultService.Ok("")
        );
        when(cloudinaryUti.CreateImg(any(), anyInt(), anyInt()))
                .thenReturn(ResultService.Ok(cloudinaryCreate));
        when(theatreRepository.updateImg(any()))
                .thenReturn(new Theatre());

        var result = theatreService.updateImgTheatre(theatreUpdateValidatorDTO, resultError);

        assertTrue(result.IsSuccess);
    }

    @Test
    public void test_UpdateImgTheatre_ErrorValidateDTO(){
        TheatreUpdateValidatorDTO theatreUpdateValidatorDTO = new TheatreUpdateValidatorDTO(
                "0571e38a-b983-465c-a568-04d6894c8fde", "base64Img"
        );

        var resultError = new BeanPropertyBindingResult(theatreUpdateValidatorDTO, "theatreUpdateValidatorDTO");
        resultError.rejectValue("id", "NotEmpty", "id should not be empty");

        List<ErrorValidation> errorValidations = new ArrayList<>();
        errorValidations.add(new ErrorValidation("id", "id should not be empty"));

        when(validateErrorsDTO.ValidateDTO(any()))
                .thenReturn(errorValidations);

        var result = theatreService.updateImgTheatre(theatreUpdateValidatorDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error validate DTO");
        assertEquals(result.Errors, errorValidations);
    }

    @Test
    public void test_UpdateImgTheatre_ErrorValidateId(){
        TheatreUpdateValidatorDTO theatreUpdateValidatorDTO = new TheatreUpdateValidatorDTO(
                "0571e38a-b983-65c-a568-04d6894c8fde", "base64Img"
        );

        var resultError = new BeanPropertyBindingResult(theatreUpdateValidatorDTO, "theatreUpdateValidatorDTO");

        var result = theatreService.updateImgTheatre(theatreUpdateValidatorDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error theatreId informed is not ID valid");
    }

    @Test
    public void test_UpdateImgTheatre_ErrorTheatreNotFound(){
        TheatreUpdateValidatorDTO theatreUpdateValidatorDTO = new TheatreUpdateValidatorDTO(
                "0571e38a-b983-465c-a568-04d6894c8fde", "base64Img"
        );

        var resultError = new BeanPropertyBindingResult(theatreUpdateValidatorDTO, "theatreUpdateValidatorDTO");

        when(theatreRepository.getByTheatreIdIdPublicId(any()))
                .thenReturn(null);

        var result = theatreService.updateImgTheatre(theatreUpdateValidatorDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "not found theatre to update");
    }

    @Test
    public void test_UpdateImgTheatre_ErrorDeleteImgCloudinary(){
        TheatreUpdateValidatorDTO theatreUpdateValidatorDTO = new TheatreUpdateValidatorDTO(
                "0571e38a-b983-465c-a568-04d6894c8fde", "base64Img"
        );

        var resultError = new BeanPropertyBindingResult(theatreUpdateValidatorDTO, "theatreUpdateValidatorDTO");

        Theatre theatre = new Theatre(null, null,
                null, null, null,
                null, null, "publicId1", null);

        when(theatreRepository.getByTheatreIdIdPublicId(any()))
                .thenReturn(theatre);
        when(cloudinaryUti.deleteImg(any())).thenReturn(
                ResultService.Fail("error delete img cloudinary")
        );

        var result = theatreService.updateImgTheatre(theatreUpdateValidatorDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error delete img cloudinary");
    }

    @Test
    public void test_UpdateImgTheatre_ErrorCreateImgCloudinary(){
        TheatreUpdateValidatorDTO theatreUpdateValidatorDTO = new TheatreUpdateValidatorDTO(
                "0571e38a-b983-465c-a568-04d6894c8fde", "base64Img"
        );

        var resultError = new BeanPropertyBindingResult(theatreUpdateValidatorDTO, "theatreUpdateValidatorDTO");

        Theatre theatre = new Theatre(null, null,
                null, null, null,
                null, null, "publicId1", null);

        CloudinaryCreate cloudinaryCreate = new CloudinaryCreate(
                "publicId1", "imgUrl1"
        );

        when(theatreRepository.getByTheatreIdIdPublicId(any()))
                .thenReturn(theatre);
        when(cloudinaryUti.deleteImg(any())).thenReturn(
                ResultService.Ok("")
        );
        when(cloudinaryUti.CreateImg(any(), anyInt(), anyInt()))
                .thenReturn(ResultService.Fail("error create img cloudinary"));

        var result = theatreService.updateImgTheatre(theatreUpdateValidatorDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error create img cloudinary");
    }

    @Test
    public void test_UpdateImgTheatre_ErrorUpdateImgRepository(){
        TheatreUpdateValidatorDTO theatreUpdateValidatorDTO = new TheatreUpdateValidatorDTO(
                "0571e38a-b983-465c-a568-04d6894c8fde", "base64Img"
        );

        var resultError = new BeanPropertyBindingResult(theatreUpdateValidatorDTO, "theatreUpdateValidatorDTO");

        Theatre theatre = new Theatre(null, null,
                null, null, null,
                null, null, "publicId1", null);

        CloudinaryCreate cloudinaryCreate = new CloudinaryCreate(
                "publicId1", "imgUrl1"
        );

        when(theatreRepository.getByTheatreIdIdPublicId(any()))
                .thenReturn(theatre);
        when(cloudinaryUti.deleteImg(any())).thenReturn(
                ResultService.Ok("")
        );
        when(cloudinaryUti.CreateImg(any(), anyInt(), anyInt()))
                .thenReturn(ResultService.Ok(cloudinaryCreate));
        when(theatreRepository.updateImg(any()))
                .thenReturn(null);

        var result = theatreService.updateImgTheatre(theatreUpdateValidatorDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error update theatre");
    }
}
