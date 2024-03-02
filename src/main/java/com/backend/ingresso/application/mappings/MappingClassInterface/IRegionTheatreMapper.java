package com.backend.ingresso.application.mappings.MappingClassInterface;

import com.backend.ingresso.application.dto.RegionTheatreDTO;
import com.backend.ingresso.domain.entities.RegionTheatre;

public interface IRegionTheatreMapper {
    RegionTheatreDTO regionTheatreToRegionTheatreDto(RegionTheatre regionTheatre);
    RegionTheatre regionTheatreDtoToRegionTheatre(RegionTheatreDTO regionTheatreDTO);
}
