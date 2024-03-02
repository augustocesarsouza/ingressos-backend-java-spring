package com.backend.ingresso.application.mappings.MappingClass;

import com.backend.ingresso.application.dto.TheatreDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.ITheatreMapper;
import com.backend.ingresso.domain.entities.Theatre;

public class TheatreMapper implements ITheatreMapper {
    @Override
    public TheatreDTO theatreToTheatreDto(Theatre theatre) {
        if(theatre == null)
            return null;

        return new TheatreDTO(theatre.getId(), theatre.getTitle(), theatre.getDescription(),
                theatre.getData(), theatre.getLocation(), theatre.getTypeOfAttraction(),
                theatre.getCategory(), theatre.getPublicId(), theatre.getImgUrl());
    }

    @Override
    public Theatre theatreDtoToTheatre(TheatreDTO theatreDTO) {
        if(theatreDTO == null)
            return null;

        return new Theatre(theatreDTO.getId(), theatreDTO.getTitle(), theatreDTO.getDescription(),
                theatreDTO.getData(), theatreDTO.getLocation(), theatreDTO.getTypeOfAttraction(),
                theatreDTO.getCategory(), theatreDTO.getPublicId(), theatreDTO.getImgUrl());
    }
}
