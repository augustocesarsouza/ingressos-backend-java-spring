package com.backend.ingresso.data.context;

import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.domain.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface RegionRepositoryJPA extends JpaRepository<Region, UUID> {
    @Query("SELECT new com.backend.ingresso.application.dto.RegionDTO(r.Id) FROM Region AS r WHERE r.City = :city")
    RegionDTO getRegionIdByCityName(String city);

    @Query("SELECT new com.backend.ingresso.application.dto.RegionDTO(r.Id) FROM Region AS r WHERE r.State = :state")
    RegionDTO getIdByNameState(String state);
}
