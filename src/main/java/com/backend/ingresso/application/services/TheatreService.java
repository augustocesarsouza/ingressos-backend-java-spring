package com.backend.ingresso.application.services;

import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.application.dto.TheatreDTO;
import com.backend.ingresso.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.backend.ingresso.application.dto.validations.theatreDTOs.TheatreCreateValidatorDTO;
import com.backend.ingresso.application.dto.validations.theatreDTOs.TheatreUpdateValidatorDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.ITheatreMapper;
import com.backend.ingresso.application.services.interfaces.IRegionService;
import com.backend.ingresso.application.services.interfaces.ITheatreService;
import com.backend.ingresso.application.util.ValidateUUID;
import com.backend.ingresso.data.cloudinaryUtil.CloudinaryCreate;
import com.backend.ingresso.data.utilityExternal.Interface.ICloudinaryUti;
import com.backend.ingresso.domain.entities.Theatre;
import com.backend.ingresso.domain.repositories.ITheatreRepository;
import jakarta.transaction.Transactional;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class TheatreService implements ITheatreService {
    private final ITheatreRepository theatreRepository;
    private final IRegionService regionService;
    private final IValidateErrorsDTO validateErrorsDTO;
    private final ICloudinaryUti cloudinaryUti;
    private final ITheatreMapper theatreMapper;

    @Autowired
    public TheatreService(ITheatreRepository theatreRepository, ITheatreMapper theatreMapper,
                          IValidateErrorsDTO validateErrorsDTO, ICloudinaryUti cloudinaryUti,
                          IRegionService regionService) {
        this.theatreRepository = theatreRepository;
        this.theatreMapper = theatreMapper;
        this.validateErrorsDTO = validateErrorsDTO;
        this.cloudinaryUti = cloudinaryUti;
        this.regionService = regionService;
    }

    @Override
    public ResultService<List<TheatreDTO>> getAllTheatreByRegionId(String region) {
        var regionResult = regionService.getIdByNameState(region);
        if(!regionResult.IsSuccess || regionResult.Data == null)
            return ResultService.Fail(regionResult.Message);

        RegionDTO regionDTO = regionResult.Data;

        var resultTheatreAll = theatreRepository.getAllTheatreByRegionId(regionDTO.getId());

        return ResultService.Ok(resultTheatreAll);
    }

    @Override
    @Transactional
    public ResultService<TheatreDTO> create(TheatreCreateValidatorDTO theatreCreateValidatorDTO, BindingResult result) {
        if(result.hasErrors()){
            var errorsDTO = result.getAllErrors();
            var errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        try {
            UUID theatreId = UUID.randomUUID();
            String dateString = theatreCreateValidatorDTO.getDataString();

            if(dateString == null)
                return ResultService.Fail("error dateString is null");

            String regex = "\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}";
            Pattern pattern = Pattern.compile(regex);

            if(!pattern.matcher(dateString).matches())
                return ResultService.Fail("error data should be in this format dd/mm/AAA");

            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
            DateTime date = DateTime.parse(dateString, formatter);

            // Convertendo DateTime para Date
            Date utilDate = date.toDate();

            // Criando um Timestamp com o java.util.Date
            Timestamp timestamp = new Timestamp(utilDate.getTime());

            var resultCreateImg = cloudinaryUti.CreateImg(theatreCreateValidatorDTO.getBase64Img(), 590, 320);

            if(!resultCreateImg.IsSuccess)
                return ResultService.Fail(resultCreateImg.Message);

            CloudinaryCreate cloudinaryCreate = resultCreateImg.Data;

            theatreCreateValidatorDTO.setIdImgUrlPublicId(theatreId, cloudinaryCreate.getImgUrl(), cloudinaryCreate.getPublicId());
            theatreCreateValidatorDTO.setData(timestamp);

            Theatre resultCreate = theatreRepository.create(theatreMapper.theatreCreateValidatorDTOToTheatre(theatreCreateValidatorDTO));

            if(resultCreate == null)
                return ResultService.Fail("error create Theatre");

            return ResultService.Ok(theatreMapper.theatreToTheatreDto(resultCreate));
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<TheatreDTO> deleteTheatre(String theatreId) {
        try {
            if(!ValidateUUID.Validate(theatreId))
                return ResultService.Fail("error theatreId informed It is not valid");

            Theatre theatre = theatreRepository.getByTheatreIdIdPublicId(UUID.fromString(theatreId));

            if(theatre == null)
                return ResultService.Fail("not found theatre");

            List<String> publicList = new ArrayList<>();
            publicList.add(theatre.getPublicId());

            var resultDeleteAllImgCloudinary = cloudinaryUti.deleteImg(publicList);

            if(!resultDeleteAllImgCloudinary.IsSuccess)
                return ResultService.Fail(resultDeleteAllImgCloudinary.Message);

            Theatre deleteTheatre = theatreRepository.delete(UUID.fromString(theatreId));

            if(deleteTheatre == null)
                return ResultService.Fail("error found when deleting theatre in repository");

            return ResultService.Ok(theatreMapper.theatreToTheatreDto(deleteTheatre));

        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<TheatreDTO> updateImgTheatre(TheatreUpdateValidatorDTO theatreUpdateValidatorDTO, BindingResult result) {
        if(result.hasErrors()){
            var errorsDTO = result.getAllErrors();
            var errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        try {
            String theatreId = theatreUpdateValidatorDTO.getId();

            if(!ValidateUUID.Validate(theatreId))
                return ResultService.Fail("error theatreId informed is not ID valid");

            Theatre theatre = theatreRepository.getByTheatreIdIdPublicId(UUID.fromString(theatreId));

            if(theatre == null)
                return ResultService.Fail("not found theatre to update");

            List<String> publicList = new ArrayList<>();
            publicList.add(theatre.getPublicId());

            var resultDeleteAllImgCloudinary = cloudinaryUti.deleteImg(publicList);

            if(!resultDeleteAllImgCloudinary.IsSuccess)
                return ResultService.Fail(resultDeleteAllImgCloudinary.Message);

            var resultCreateNewImg = cloudinaryUti.CreateImg(theatreUpdateValidatorDTO.getBase64Img(), 590, 320);

            if(!resultCreateNewImg.IsSuccess)
                return ResultService.Fail(resultCreateNewImg.Message);

            CloudinaryCreate cloudinaryCreateNewImg = resultCreateNewImg.Data;

            Theatre theatreUp = new Theatre(UUID.fromString(theatreId), null, null, null,
                    null, null, null, cloudinaryCreateNewImg.getPublicId(), cloudinaryCreateNewImg.getImgUrl());

            Theatre resultUpdateTheatre = theatreRepository.updateImg(theatreUp);

            if(resultUpdateTheatre == null)
                return ResultService.Fail("error update theatre");

            return ResultService.Ok(theatreMapper.theatreToTheatreDto(resultUpdateTheatre));
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }
}
