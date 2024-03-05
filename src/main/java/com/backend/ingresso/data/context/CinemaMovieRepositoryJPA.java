package com.backend.ingresso.data.context;

import com.backend.ingresso.domain.entities.CinemaMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CinemaMovieRepositoryJPA extends JpaRepository<CinemaMovie, UUID> {
    //CinemaMovie(UUID id, UUID cinemaId, Cinema cinema, UUID movieId, Movie movie, UUID regionId, Region region, String screeningSchedule)
    //CinemaMovie(cm.Id, cm.CinemaId, null, cm.MovieId, null, cm.RegionId, null, cm.ScreeningSchedule)

    //Cinema(UUID id, String nameCinema, String district, String ranking)
    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "CinemaMovie(null, null, new com.backend.ingresso.domain.entities.Cinema(cm.Cinema.Id, cm.Cinema.NameCinema, cm.Cinema.District, cm.Cinema.Ranking)" +
            ", null, null, null, null, cm.ScreeningSchedule) " +
            "FROM CinemaMovie AS cm WHERE cm.RegionId = :regionId AND cm.MovieId = :movieId")
    List<CinemaMovie> getByRegionCinemaIdAndMovieId(UUID regionId, UUID movieId);
}
