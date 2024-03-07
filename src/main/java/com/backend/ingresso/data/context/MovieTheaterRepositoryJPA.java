package com.backend.ingresso.data.context;

import com.backend.ingresso.application.dto.MovieTheaterDTO;
import com.backend.ingresso.domain.entities.MovieTheater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovieTheaterRepositoryJPA extends JpaRepository<MovieTheater, UUID> {
    //MovieTheater(UUID id, UUID movieId, UUID regionId)
    //MovieTheater(mt.Id, mt.MovieId, mt.RegionId)

    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "MovieTheater(mt.Id, null, null) " +
            "FROM MovieTheater AS mt " +
            "WHERE mt.MovieId = :movieId AND mt.RegionId = :regionId")
    List<MovieTheater> getMovieTheaterIfThereIs(UUID movieId, UUID regionId);
}
