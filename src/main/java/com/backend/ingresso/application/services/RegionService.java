package com.backend.ingresso.application.services;

import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IRegionMapper;
import com.backend.ingresso.application.services.interfaces.IRegionService;
import com.backend.ingresso.domain.repositories.IRegionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionService implements IRegionService {
    private final IRegionRepository regionRepository;
    private final IRegionMapper regionMapper;

    @Autowired
    public RegionService(IRegionRepository regionRepository, IRegionMapper regionMapper) {
        this.regionRepository = regionRepository;
        this.regionMapper = regionMapper;
    }

    @Override
    @Transactional
    public ResultService<RegionDTO> getRegionIdByCityName(String city) {
        try {
            RegionDTO regionDTO = regionRepository.getRegionIdByCityName(city);

            if(regionDTO == null)
                return ResultService.Fail("not found region");

            return ResultService.Ok(regionDTO);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultService<RegionDTO> getIdByNameState(String state) {
        try {
            RegionDTO regionDTO = regionRepository.getIdByNameState(state);

            if(regionDTO == null)
                return ResultService.Fail("not found region");

            return ResultService.Ok(regionDTO);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultService<RegionDTO> getIdRegionByState(String state) {
        try {
            RegionDTO regionDTO = regionRepository.getIdByNameState(state);

            if(regionDTO == null)
                return ResultService.Fail("region not found");

            return ResultService.Ok(regionDTO);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }
}
