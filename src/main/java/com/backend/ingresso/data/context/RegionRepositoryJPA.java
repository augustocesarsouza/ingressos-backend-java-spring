package com.backend.ingresso.data.context;

import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.domain.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RegionRepositoryJPA extends JpaRepository<Region, UUID> {
    //Region(UUID id, String state, String city)
    //Region(r.Id, r.State, r.City)
    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "Region(r.Id, null, null) " +
            "FROM Region AS r WHERE r.Id = :regionId")
    List<Region> getCheckIfRegionExistsById(UUID regionId);
    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "Region(r.Id, null, null) " +
            "FROM Region AS r WHERE r.City = :city")
    List<Region> getRegionIdByCityName(String city);

    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "Region(r.Id, null, null) " +
            "FROM Region AS r WHERE r.State = :state")
    List<Region> getIdByNameState(String state);

    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "Region(r.Id, null, null) " +
            "FROM Region AS r WHERE r.City = :city")
    List<Region> getRegionIdByCity(String city);
}
