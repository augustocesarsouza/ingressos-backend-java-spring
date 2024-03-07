package com.backend.ingresso.data.context;

import com.backend.ingresso.domain.entities.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CinemaRepositoryJPA extends JpaRepository<Cinema, UUID> {
    //Cinema(UUID id, String nameCinema, String district, String ranking)
    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "Cinema(c.Id, null, null, null) " +
            "FROM Cinema AS c WHERE c.Id = :cinemaId")
    List<Cinema> getCinemaById_Info_Id(UUID cinemaId);
}
