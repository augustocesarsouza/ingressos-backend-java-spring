package com.backend.ingresso.domain.repositories;

import com.backend.ingresso.domain.entities.Cinema;

import java.util.UUID;

public interface ICinemaRepository {
    Cinema getById(UUID cinemaId);
    Cinema create(Cinema cinema);
    Cinema delete(UUID cinemaId);
}
