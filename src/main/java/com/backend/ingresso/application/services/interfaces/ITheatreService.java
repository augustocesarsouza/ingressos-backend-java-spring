package com.backend.ingresso.application.services.interfaces;

import com.backend.ingresso.application.dto.TheatreDTO;
import com.backend.ingresso.application.dto.validations.theatreDTOs.TheatreCreateValidatorDTO;
import com.backend.ingresso.application.dto.validations.theatreDTOs.TheatreUpdateValidatorDTO;
import com.backend.ingresso.application.services.ResultService;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface ITheatreService {
    ResultService<List<TheatreDTO>> getAllTheatreByRegionId(String region);
    ResultService<TheatreDTO> create(TheatreCreateValidatorDTO theatreCreateValidatorDTO, BindingResult result);
    ResultService<TheatreDTO> deleteTheatre(String theatreId);
    ResultService<TheatreDTO> updateImgTheatre(TheatreUpdateValidatorDTO theatreUpdateValidatorDTO, BindingResult result);
}
