package com.backend.ingresso.application.mappings.MappingClassInterface;

import com.backend.ingresso.application.dto.TheatreDTO;
import com.backend.ingresso.application.dto.validations.theatreDTOs.TheatreCreateValidatorDTO;
import com.backend.ingresso.domain.entities.Theatre;

public interface ITheatreMapper {
    TheatreDTO theatreToTheatreDto(Theatre theatre);
    Theatre theatreDtoToTheatre(TheatreDTO theatreDTO);
    Theatre theatreCreateValidatorDTOToTheatre(TheatreCreateValidatorDTO theatreCreateValidatorDTO);
}
