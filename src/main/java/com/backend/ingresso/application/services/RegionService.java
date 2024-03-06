package com.backend.ingresso.application.services;

import com.backend.ingresso.application.ErrorValidation;
import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IRegionMapper;
import com.backend.ingresso.application.services.interfaces.IRegionService;
import com.backend.ingresso.domain.entities.Region;
import com.backend.ingresso.domain.repositories.IRegionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

@Service
public class RegionService implements IRegionService {
    private final IRegionRepository regionRepository;
    private final IValidateErrorsDTO validateErrorsDTO;
    private final IRegionMapper regionMapper;

    @Autowired
    public RegionService(IRegionRepository regionRepository, IRegionMapper regionMapper,
                         IValidateErrorsDTO validateErrorsDTO) {
        this.regionRepository = regionRepository;
        this.regionMapper = regionMapper;
        this.validateErrorsDTO = validateErrorsDTO;
    }

    @Override
    @Transactional
    public ResultService<RegionDTO> getCheckIfRegionExistsById(UUID regionId) {
        try {
            Region region = regionRepository.getCheckIfRegionExistsById(regionId);

            if(region == null)
                return ResultService.Fail("not found region");

            return ResultService.Ok(regionMapper.regionToRegionDto(region));
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultService<RegionDTO> getRegionIdByCityName(String city) {
        try {
            Region regionDTO = regionRepository.getRegionIdByCityName(city);

            if(regionDTO == null)
                return ResultService.Fail("not found region");

            return ResultService.Ok(regionMapper.regionToRegionDto(regionDTO));
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultService<RegionDTO> getIdByNameState(String state) {
        try {
            Region regionDTO = regionRepository.getIdByNameState(state);

            if(regionDTO == null)
                return ResultService.Fail("not found region");

            return ResultService.Ok(regionMapper.regionToRegionDto(regionDTO));
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultService<RegionDTO> getIdRegionByState(String state) {
        try {
            Region regionDTO = regionRepository.getIdByNameState(state);

            if(regionDTO == null)
                return ResultService.Fail("region not found");

            return ResultService.Ok(regionMapper.regionToRegionDto(regionDTO));
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultService<RegionDTO> getRegionIdByCity(String city) {
        try {
            Region region = regionRepository.getRegionIdByCity(city);

            if(region == null)
                return ResultService.Fail("region not found");

            return ResultService.Ok(regionMapper.regionToRegionDto(region));
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultService<RegionDTO> create(RegionDTO regionDTO, BindingResult resultValid) {
        if(regionDTO == null)
            return ResultService.Fail("error DTO Null");

        if(resultValid.hasErrors()){
            var errorsDTO = resultValid.getAllErrors();
            List<ErrorValidation> errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        try {
            regionDTO.setId(UUID.randomUUID());
            Region region = regionRepository.create(regionMapper.regionDtoToRegion(regionDTO));

            if(region == null)
                return ResultService.Fail("not create region");

            return ResultService.Ok(regionMapper.regionToRegionDto(region));
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }
}
