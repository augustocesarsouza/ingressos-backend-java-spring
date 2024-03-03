package com.backend.ingresso.data.context;
///package com.backend.ingresso.application.dto

import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.domain.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovieRepositoryJPA extends JpaRepository<Movie, UUID> {
    //Movie(m.Id, m.Title, m.Description, m.Gender, m.Duration, m.MovieRating, m.ImgUrl, m.PublicId, m.ImgUrlBackground, m.PublicIdImgBackground, m.StatusMovie)
    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "Movie(m.Id, m.Title, null, null, null, null, null, null, null, null, null) " +
            "FROM Movie AS m WHERE m.Id = :movieId")
    Movie getMovieById_Info_Id_Title(UUID movieId);

    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "Movie(m.Id, m.Title, m.Description, m.Gender, m.Duration, m.MovieRating, m.ImgUrl, null, m.ImgUrlBackground, null, null) " +
            "FROM Movie AS m WHERE m.Id = :movieId")
    Movie getMovieById(UUID movieId);

    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "Movie(m.Id, m.Title, m.Description, m.Gender, null, m.MovieRating, null, null, null, null, null) " +
            "FROM Movie AS m WHERE m.StatusMovie = :statusMovie")
    Movie getMovieByStatusMovie(String statusMovie);

    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "Movie(m.Id, null, null, null, null, null, null, m.PublicId, null, m.PublicIdImgBackground, null) " +
            "FROM Movie AS m WHERE m.Id = :movieId")
    Movie getMovieByIdForDelete(UUID movieId);

    @Query("SELECT new com.backend.ingresso.application.dto." +
            "MovieDTO(m.Id, m.Title, null, null, null, m.MovieRating, m.ImgUrl, null, null, null, null) " +
            "FROM Movie AS m INNER JOIN MovieTheater AS mt ON m.Id = mt.MovieId WHERE mt.RegionId = :regionId")
    List<MovieDTO> getMovieByRegionId_Info_All(UUID regionId);

    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "Movie(m.Id, null, null, null, null, null, null, null, null, null, null) " +
            "FROM Movie AS m " +
            "WHERE m.Title = :title")
    Movie getMovieByTitle(String title);
}