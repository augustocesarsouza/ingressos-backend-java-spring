package com.backend.ingresso.application.mappings.MappingClassInterface;

import com.backend.ingresso.application.dto.TheatreDTO;
import com.backend.ingresso.domain.entities.Theatre;

public interface ITheatreMapper {
    TheatreDTO theatreToTheatreDto(Theatre theatre);
    Theatre theatreDtoToTheatre(TheatreDTO theatreDTO);
}
