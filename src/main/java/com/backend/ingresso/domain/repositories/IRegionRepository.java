package com.backend.ingresso.domain.repositories;

import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.domain.entities.Region;

import java.util.UUID;

public interface IRegionRepository {
    Region getCheckIfRegionExistsById(UUID regionId);
    Region getRegionIdByCityName(String city);
    Region getIdByNameState(String state);
    Region getRegionIdByCity(String city);
    Region create(Region region);
}
