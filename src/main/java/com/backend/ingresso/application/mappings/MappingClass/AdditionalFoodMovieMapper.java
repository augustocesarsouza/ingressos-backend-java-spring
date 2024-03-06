package com.backend.ingresso.application.mappings.MappingClass;

import com.backend.ingresso.application.dto.AdditionalFoodMovieDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IAdditionalFoodMovieMapper;
import com.backend.ingresso.application.mappings.MappingClassInterface.IMovieMapper;
import com.backend.ingresso.domain.entities.AdditionalFoodMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdditionalFoodMovieMapper implements IAdditionalFoodMovieMapper {
    private final IMovieMapper movieMapper;

    @Autowired
    public AdditionalFoodMovieMapper(IMovieMapper movieMapper) {
        this.movieMapper = movieMapper;
    }

    @Override
    public AdditionalFoodMovieDTO additionalFoodMovieToAdditionalFoodMovieDto(AdditionalFoodMovie additionalFoodMovie) {
        if(additionalFoodMovie == null)
            return null;

        return new AdditionalFoodMovieDTO(additionalFoodMovie.getId(), additionalFoodMovie.getTitle(), additionalFoodMovie.getPrice(),
                additionalFoodMovie.getFee(), additionalFoodMovie.getImgUrl(),
                additionalFoodMovie.getPublicId(), additionalFoodMovie.getMovieId(), movieMapper.movieToMovieDto(additionalFoodMovie.getMovie()));
    }

    @Override
    public AdditionalFoodMovie additionalFoodMovieDtoToAdditionalFoodMovie(AdditionalFoodMovieDTO additionalFoodMovieDTO) {
        if(additionalFoodMovieDTO == null)
            return null;

        return new AdditionalFoodMovie(additionalFoodMovieDTO.getId(), additionalFoodMovieDTO.getTitle(), additionalFoodMovieDTO.getPrice(),
                additionalFoodMovieDTO.getFee(), additionalFoodMovieDTO.getImgUrl(),
                additionalFoodMovieDTO.getPublicId(), additionalFoodMovieDTO.getMovieId(), movieMapper.movieDtoToMovie(additionalFoodMovieDTO.getMovieDTO()));
    }
}
