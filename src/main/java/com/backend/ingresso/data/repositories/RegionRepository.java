package com.backend.ingresso.data.repositories;

import com.backend.ingresso.application.dto.MovieDTO;
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
    public Region getRegionIdByCityName(String city) {
        return regionRepositoryJPA.getRegionIdByCityName(city);
    }

    @Override
    public Region getIdByNameState(String state) {
        return regionRepositoryJPA.getIdByNameState(state);
    }

    @Override
    public Region create(Region region) {
        if(region == null)
            return null;

        return regionRepositoryJPA.save(region);
    }
}
