package com.backend.ingresso.data.context;

import com.backend.ingresso.domain.entities.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CinemaRepositoryJPA extends JpaRepository<Cinema, UUID> {

}
