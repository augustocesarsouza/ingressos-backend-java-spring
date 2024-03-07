package com.backend.ingresso.data.repositories;

import com.backend.ingresso.data.context.CinemaRepositoryJPA;
import com.backend.ingresso.domain.entities.Cinema;
import com.backend.ingresso.domain.repositories.ICinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CinemaRepository implements ICinemaRepository {
    private final CinemaRepositoryJPA cinemaRepositoryJPA;

    @Autowired
    public CinemaRepository(CinemaRepositoryJPA cinemaRepositoryJPA) {
        this.cinemaRepositoryJPA = cinemaRepositoryJPA;
    }

    @Override
    public Cinema getById(UUID cinemaId) {
        return cinemaRepositoryJPA.getCinemaById_Info_Id(cinemaId).stream().findFirst().orElse(null);
    }

    @Override
    public Cinema create(Cinema cinema) {
        if(cinema == null)
            return null;

        return cinemaRepositoryJPA.save(cinema);
    }

    @Override
    public Cinema delete(UUID cinemaId) {
        if(cinemaId == null)
            return null;

        Cinema cinemaDelete = cinemaRepositoryJPA.findById(cinemaId).orElse(null);

        if(cinemaDelete == null)
            return null;

        cinemaRepositoryJPA.deleteById(cinemaId);

        return cinemaDelete;
    }
}
