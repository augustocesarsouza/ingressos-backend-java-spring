package com.backend.ingresso.application.mappings.MappingClassInterface;

import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.domain.entities.Region;

public interface IRegionMapper {
    public RegionDTO regionToRegionDto(Region region);
    public Region regionDtoToRegion(RegionDTO regionDTO);
}
