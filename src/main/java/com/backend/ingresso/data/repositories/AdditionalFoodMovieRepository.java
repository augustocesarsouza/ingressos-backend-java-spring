package com.backend.ingresso.data.repositories;

import com.backend.ingresso.application.dto.AdditionalFoodMovieDTO;
import com.backend.ingresso.data.context.AdditionalFoodMovieRepositoryJPA;
import com.backend.ingresso.domain.entities.AdditionalFoodMovie;
import com.backend.ingresso.domain.repositories.IAdditionalFoodMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AdditionalFoodMovieRepository implements IAdditionalFoodMovieRepository {
    private final AdditionalFoodMovieRepositoryJPA additionalFoodMovieRepositoryJPA;

    @Autowired
    public AdditionalFoodMovieRepository(AdditionalFoodMovieRepositoryJPA additionalFoodMovieRepositoryJPA) {
        this.additionalFoodMovieRepositoryJPA = additionalFoodMovieRepositoryJPA;
    }

    @Override
    public List<AdditionalFoodMovieDTO> getAllFoodMovie(UUID movieId) {
        return additionalFoodMovieRepositoryJPA.getAllFoodMovie(movieId);
    }

    @Override
    public AdditionalFoodMovie create(AdditionalFoodMovie additionalFoodMovie) {
        if(additionalFoodMovie == null)
            return null;

        return additionalFoodMovieRepositoryJPA.save(additionalFoodMovie);
    }
}
