package com.backend.ingresso.application.mappings.MappingClass;

import com.backend.ingresso.application.dto.RegionTheatreDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IRegionTheatreMapper;
import com.backend.ingresso.domain.entities.RegionTheatre;
import org.springframework.stereotype.Component;

@Component
public class RegionTheatreMapper implements IRegionTheatreMapper {
    @Override
    public RegionTheatreDTO regionTheatreToRegionTheatreDto(RegionTheatre regionTheatre) {
        if(regionTheatre == null)
            return null;

        return new RegionTheatreDTO(regionTheatre.getId(), regionTheatre.getTheatreId(), regionTheatre.getRegionId());
    }

    @Override
    public RegionTheatre regionTheatreDtoToRegionTheatre(RegionTheatreDTO regionTheatreDTO) {
        if(regionTheatreDTO == null)
            return null;

        return new RegionTheatre(regionTheatreDTO.getId(), regionTheatreDTO.getTheatreId(), regionTheatreDTO.getRegionId());
    }
}
