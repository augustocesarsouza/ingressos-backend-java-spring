package com.backend.ingresso.application.services.interfaces;

import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.application.services.ResultService;
import org.springframework.validation.BindingResult;

public interface IRegionService {
    ResultService<RegionDTO> getRegionIdByCityName(String city);
    ResultService<RegionDTO> getIdRegionByState(String state);
    ResultService<RegionDTO> getIdByNameState(String state);
    ResultService<RegionDTO> getRegionIdByCity(String city);
    ResultService<RegionDTO> create(RegionDTO regionDTO, BindingResult resultValid);
}
