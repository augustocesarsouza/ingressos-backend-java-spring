package com.backend.ingresso.data.context;

import com.backend.ingresso.application.dto.CinemaMovieDTO;
import com.backend.ingresso.domain.entities.CinemaMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CinemaMovieRepositoryJPA extends JpaRepository<CinemaMovie, UUID> {
    //CinemaMovie(UUID id, UUID cinemaId, Cinema cinema, UUID movieId, Movie movie, UUID regionId, Region region, String screeningSchedule)
    //CinemaMovie(cm.Id, cm.CinemaId, null, cm.MovieId, null, cm.RegionId, null, cm.ScreeningSchedule)
    //CinemaMovieDTO(UUID id, UUID cinemaId, CinemaDTO cinema, UUID movieId, MovieDTO movie, UUID regionId, RegionDTO region, String screeningSchedule)

    //Cinema(UUID id, String nameCinema, String district, String ranking)
    @Query("SELECT new com.backend.ingresso.application.dto." +
            "CinemaMovieDTO(null, null, new com.backend.ingresso.application.dto.CinemaDTO(c.Id, c.NameCinema, c.District, c.Ranking)" +
            ", null, null, null, null, cm.ScreeningSchedule) " +
            "FROM CinemaMovie AS cm INNER JOIN Cinema AS c ON cm.CinemaId = c.Id " +
            "WHERE cm.RegionId = :regionId AND cm.MovieId = :movieId")
    List<CinemaMovieDTO> getByRegionCinemaIdAndMovieId(UUID regionId, UUID movieId);
}
