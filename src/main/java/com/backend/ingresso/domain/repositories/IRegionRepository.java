package com.backend.ingresso.domain.repositories;

import com.backend.ingresso.application.dto.RegionDTO;

import java.util.UUID;

public interface IRegionRepository {
    public RegionDTO getRegionIdByCityName(String city);
    public RegionDTO getIdByNameState(String state);
}
