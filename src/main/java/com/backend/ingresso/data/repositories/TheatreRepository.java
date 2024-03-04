package com.backend.ingresso.data.repositories;

import com.backend.ingresso.application.dto.TheatreDTO;
import com.backend.ingresso.data.context.TheatreRepositoryJPA;
import com.backend.ingresso.domain.entities.Theatre;
import com.backend.ingresso.domain.repositories.ITheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TheatreRepository implements ITheatreRepository {
    private final TheatreRepositoryJPA theatreRepositoryJPA;

    @Autowired
    public TheatreRepository(TheatreRepositoryJPA theatreRepositoryJPA) {
        this.theatreRepositoryJPA = theatreRepositoryJPA;
    }

    @Override
    public Theatre getById(UUID theatreId) {
        if(theatreId == null)
            return null;

        return theatreRepositoryJPA.getByTheatreId(theatreId);
    }

    @Override
    public Theatre getByTheatreIdIdPublicId(UUID theatreId) {
        if(theatreId == null)
            return null;

        return theatreRepositoryJPA.getByTheatreIdIdPublicId(theatreId);
    }

    @Override
    public Theatre getByIdAllProps(UUID theatreId) {
        if(theatreId == null)
            return null;

        return theatreRepositoryJPA.findById(theatreId).orElse(null);
    }

    @Override
    public List<TheatreDTO> getAllTheatreByRegionId(UUID regionId) {
        if(regionId == null)
            return null;

        return theatreRepositoryJPA.getAllTheatreByRegionId(regionId);
    }

    @Override
    public Theatre create(Theatre theatre) {
        if(theatre == null)
            return null;

        return theatreRepositoryJPA.save(theatre);
    }

    @Override
    public Theatre updateImg(Theatre theatre) {
        if(theatre == null)
            return null;

        Theatre theatreUpdate = theatreRepositoryJPA.findById(theatre.getId()).orElse(null);

        if(theatreUpdate == null)
            return null;

        theatreUpdate.setImgUpdate(theatre.getImgUrl(), theatre.getPublicId());

        return theatreRepositoryJPA.save(theatreUpdate);
    }

    @Override
    public Theatre delete(UUID theatreId) {
        if(theatreId == null)
            return null;

        Theatre theatreDelete = theatreRepositoryJPA.findById(theatreId).orElse(null);

        if(theatreDelete == null)
            return null;

        theatreRepositoryJPA.delete(theatreDelete);

        return theatreDelete;
    }
}
