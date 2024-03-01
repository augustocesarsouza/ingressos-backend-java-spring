package com.backend.ingresso.application.mappings.MappingClass;

import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IRegionMapper;
import com.backend.ingresso.domain.entities.Region;
import org.springframework.stereotype.Component;

@Component
public class RegionMapper implements IRegionMapper {
    @Override
    public RegionDTO regionToRegionDto(Region region) {
        if(region == null)
            return null;

        return new RegionDTO(region.getId(), region.getState(), region.getCity());
    }

    @Override
    public Region regionDtoToRegion(RegionDTO regionDTO) {
        if(regionDTO == null)
            return null;

        return new Region(regionDTO.getId(), regionDTO.getState(), regionDTO.getCity());
    }
}
