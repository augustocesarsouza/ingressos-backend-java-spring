package com.backend.ingresso.data.context;

import com.backend.ingresso.application.dto.TheatreDTO;
import com.backend.ingresso.domain.entities.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface TheatreRepositoryJPA extends JpaRepository<Theatre, UUID> {
    //Theatre(UUID id, String title, String description, Timestamp data,String location, String typeOfAttraction, String category ,String publicId, String imgUrl)
    //
    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "Theatre(t.Id, t.Title, null, null, null, null, null, null, null) " +
            "FROM Theatre AS t WHERE t.Id = :theatreId")
    Theatre getByTheatreId(UUID theatreId);
    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "Theatre(t.Id, null, null, null, null, null, null, t.PublicId, null) " +
            "FROM Theatre AS t WHERE t.Id = :theatreId")
    Theatre getByTheatreIdIdPublicId(UUID theatreId);
    @Query("SELECT new com.backend.ingresso.application.dto." +
            "TheatreDTO(t.Id, t.Title, null, t.Data, t.Location, null, null, null, t.ImgUrl) " +
            "FROM Theatre AS t INNER JOIN RegionTheatre AS rt ON rt.TheatreId = t.Id WHERE rt.RegionId = :regionId")
    List<TheatreDTO> getAllTheatreByRegionId(UUID regionId);
}

