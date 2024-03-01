package com.backend.ingresso.data.repositories;

import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.data.context.RegionRepositoryJPA;
import com.backend.ingresso.domain.entities.Region;
import com.backend.ingresso.domain.repositories.IRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegionRepository implements IRegionRepository {
    private final RegionRepositoryJPA regionRepositoryJPA;

    @Autowired
    public RegionRepository(RegionRepositoryJPA regionRepositoryJPA) {
        this.regionRepositoryJPA = regionRepositoryJPA;
    }

    @Override
    public RegionDTO getRegionIdByCityName(String city) {
        RegionDTO regionDTO = regionRepositoryJPA.getRegionIdByCityName(city);

        if(regionDTO == null)
            return null;

        return regionDTO;
    }

    @Override
    public RegionDTO getIdByNameState(String state) {
        RegionDTO regionDTO = regionRepositoryJPA.getIdByNameState(state);

        if(regionDTO == null)
            return null;

        return regionDTO;
    }
}
