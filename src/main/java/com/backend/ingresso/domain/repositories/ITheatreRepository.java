package com.backend.ingresso.domain.repositories;

import com.backend.ingresso.application.dto.TheatreDTO;
import com.backend.ingresso.domain.entities.Theatre;

import java.util.List;
import java.util.UUID;

public interface ITheatreRepository {
    Theatre getById(UUID theatreId);
    Theatre getByTheatreIdIdPublicId(UUID theatreId);
    Theatre getByIdAllProps(UUID theatreId);
    List<TheatreDTO> getAllTheatreByRegionId(UUID regionId);
    Theatre create(Theatre theatre);
    Theatre updateImg(Theatre theatre);
    Theatre delete(UUID theatreId);
}
