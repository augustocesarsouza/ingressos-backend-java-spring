package com.backend.ingresso.application.services.interfaces;

import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.application.services.ResultService;

public interface IRegionService {
    ResultService<RegionDTO> getRegionIdByCityName(String city);
    ResultService<RegionDTO> getIdByNameState(String state);
}
