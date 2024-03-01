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
    @Query("SELECT new com.backend.ingresso.application.dto.MovieDTO(m.Id, m.Title) FROM Movie AS m WHERE m.Id = :movieId")
    //poderia usar "AND" e fazer "AND (:titleParam IS NULL OR m.title = :titleParam)" filtar mas poder se pesado tem que ter index
    MovieDTO getMovieById_Info_Id_Title(UUID movieId);
    @Query("SELECT new com.backend.ingresso.application.dto.MovieDTO(m.Id, m.Title, m.Description, m.Gender, m.Duration, m.MovieRating, m.ImgUrl, m.ImgUrlBackground) " +
            "FROM Movie AS m WHERE m.Id = :movieId")
    MovieDTO getMovieById(UUID movieId);
    @Query("SELECT new com.backend.ingresso.application.dto.MovieDTO(m.Id, m.Title, m.Description, m.Gender, m.MovieRating) " +
            "FROM Movie AS m WHERE m.StatusMovie = :statusMovie")
    MovieDTO getMovieByStatusMovie(String statusMovie);
    @Query("SELECT new com.backend.ingresso.application.dto.MovieDTO(m.Id, m.PublicId, m.PublicIdImgBackground) FROM Movie AS m WHERE m.Id = :movieId")
    MovieDTO getMovieByIdForDelete(UUID movieId);
    //testar esse 'setDataForGetByRegionId' pode não funcionar
    @Query("SELECT new com.backend.ingresso.application.dto.MovieDTO(m.Id, m.Title, m.ImgUrl, m.MovieRating) " +
            "FROM Movie AS m INNER JOIN MovieTheater AS mt ON m.Id = mt.MovieId WHERE mt.RegionId = :regionId")
    List<MovieDTO> getMovieByRegionId_Info_All(UUID regionId);
}