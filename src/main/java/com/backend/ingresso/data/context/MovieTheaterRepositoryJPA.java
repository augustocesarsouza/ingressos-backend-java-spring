package com.backend.ingresso.data.context;

import com.backend.ingresso.application.dto.MovieTheaterDTO;
import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.domain.entities.MovieTheater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface MovieTheaterRepositoryJPA extends JpaRepository<MovieTheater, UUID> {
    @Query("SELECT new com.backend.ingresso.application.dto.MovieTheaterDTO(mt.Id) FROM MovieTheater AS mt WHERE " +
            "mt.MovieId = :movieId AND mt.RegionId = :regionId")
    MovieTheaterDTO getMovieTheaterIfThereIs(UUID movieId, UUID regionId);
}
