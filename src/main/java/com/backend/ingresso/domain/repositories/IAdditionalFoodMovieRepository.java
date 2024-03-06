package com.backend.ingresso.domain.repositories;

import com.backend.ingresso.application.dto.AdditionalFoodMovieDTO;
import com.backend.ingresso.domain.entities.AdditionalFoodMovie;

import java.util.List;
import java.util.UUID;

public interface IAdditionalFoodMovieRepository {
    List<AdditionalFoodMovieDTO> getAllFoodMovie(UUID movieId);
    AdditionalFoodMovie create(AdditionalFoodMovie additionalFoodMovie);
}
