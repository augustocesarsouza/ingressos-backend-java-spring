package com.backend.ingresso.data.context;

import com.backend.ingresso.domain.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovieRepositoryJPA extends JpaRepository<Movie, UUID> {
    @Query("SELECT m.Id, m.Title FROM Movie AS m WHERE m.Id = :movieId")
    //poderia usar "AND" e fazer "AND (:titleParam IS NULL OR m.title = :titleParam)" filtar mas poder se pesado tem que ter index
    String getMovieById_Info_Id_Title(UUID movieId);
    @Query("SELECT m.Id, m.Title, m.Description, m.Gender, m.Duration, m.MovieRating, m.ImgUrl, m.ImgUrlBackground FROM Movie AS m WHERE m.Id = :movieId")
    String getMovieById_Info_Id_Title_Description_Gender_Duration_MovieRating_ImgUrl_ImgUrlBackground(UUID movieId);
    @Query("SELECT m.Id, m.Title, m.Description, m.Gender, m.Duration, m.MovieRating, m.ImgUrl, m.StatusMovie FROM Movie AS m WHERE m.StatusMovie = :statusMovie")
    String getMovieByStatusMovie_Info_Id_Title_Description_Gender_Duration_MovieRating_ImgUrl_StatusMovie(String statusMovie);
    @Query("SELECT m FROM Movie AS m WHERE m.StatusMovie = :statusMovie")
    String getMovieById_Info_All(UUID movieId);
    @Query("SELECT m.Id, m.Title, m.ImgUrl, m.MovieRating FROM Movie AS m INNER JOIN MovieTheater AS mt ON m.Id = mt.MovieId WHERE m.RegionId = :regionId")
    List<Object[]> getMovieByRegionId_Info_All(UUID regionId);
}