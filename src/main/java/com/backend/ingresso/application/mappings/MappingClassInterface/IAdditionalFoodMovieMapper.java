package com.backend.ingresso.application.mappings.MappingClassInterface;

import com.backend.ingresso.application.dto.AdditionalFoodMovieDTO;
import com.backend.ingresso.domain.entities.AdditionalFoodMovie;

public interface IAdditionalFoodMovieMapper {
    AdditionalFoodMovieDTO additionalFoodMovieToAdditionalFoodMovieDto(AdditionalFoodMovie additionalFoodMovie);
    AdditionalFoodMovie additionalFoodMovieDtoToAdditionalFoodMovie(AdditionalFoodMovieDTO additionalFoodMovieDTO);
}
