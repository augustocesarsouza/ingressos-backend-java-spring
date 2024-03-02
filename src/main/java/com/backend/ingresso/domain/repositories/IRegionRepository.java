package com.backend.ingresso.domain.repositories;

import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.domain.entities.Region;

import java.util.UUID;

public interface IRegionRepository {
    public RegionDTO getRegionIdByCityName(String city);
    public RegionDTO getIdByNameState(String state);
    Region create(Region region);
}
