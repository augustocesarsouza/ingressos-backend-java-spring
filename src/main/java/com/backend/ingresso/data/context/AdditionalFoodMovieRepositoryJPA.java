package com.backend.ingresso.data.context;

import com.backend.ingresso.application.dto.AdditionalFoodMovieDTO;
import com.backend.ingresso.domain.entities.AdditionalFoodMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AdditionalFoodMovieRepositoryJPA extends JpaRepository<AdditionalFoodMovie, UUID> {
    //AdditionalFoodMovie(UUID id, String title, String price, String fee, String imgUrl, String publicId, UUID movieId, Movie movie)
    //AdditionalFoodMovieDTO(UUID id, String title, String price, String fee, String imgUrl, String publicId, UUID movieId, MovieDTO movieDTO)
    @Query("SELECT new com.backend.ingresso.application.dto." +
            "AdditionalFoodMovieDTO(null, af.Title, af.Price, af.Fee, af.ImgUrl, null, null, null) " +
            "FROM AdditionalFoodMovie AS af WHERE af.MovieId = :movieId")
    List<AdditionalFoodMovieDTO> getAllFoodMovie(UUID movieId);
}
