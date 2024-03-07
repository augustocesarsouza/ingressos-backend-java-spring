package com.backend.ingresso.data.repositories;

import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.data.context.MovieRepositoryJPA;
import com.backend.ingresso.domain.entities.Movie;
import com.backend.ingresso.domain.repositories.IMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class MovieRepository implements IMovieRepository {
    private final MovieRepositoryJPA movieRepositoryJPA;

    @Autowired
    public MovieRepository(MovieRepositoryJPA movieRepositoryJPA) {
        this.movieRepositoryJPA = movieRepositoryJPA;
    }

    @Override
    public Movie getById(UUID movieId) {
        return movieRepositoryJPA.getMovieById_Info_Id_Title(movieId).stream().findFirst().orElse(null);
    }

    @Override
    public Movie getInfoMoviesById(UUID movieId) {
        return movieRepositoryJPA.getMovieById(movieId).stream().findFirst().orElse(null);
    }

    @Override
    public Movie getStatusMovie(String statusMovie) {
        return movieRepositoryJPA.getMovieByStatusMovie(statusMovie).stream().findFirst().orElse(null);
    }

    @Override
    public Movie getMovieByIdForDelete(UUID movieId) {
        return movieRepositoryJPA.getMovieByIdForDelete(movieId).stream().findFirst().orElse(null);
    }

    @Override
    public List<MovieDTO> getAllMovieByRegionId(UUID regionId) {
        List<MovieDTO> listMovieDTOS = movieRepositoryJPA.getMovieByRegionId_Info_All(regionId);

        if(listMovieDTOS.isEmpty())
            return null;

        return listMovieDTOS;
    }

    @Override
    public Movie getMovieByTitle(String title) {
        return movieRepositoryJPA.getMovieByTitle(title).stream().findFirst().orElse(null);
    }

    @Override
    public Movie create(Movie movie) {
        if(movie == null)
            return null;

        return movieRepositoryJPA.save(movie);
    }

    @Override
    public Movie updateImg(Movie movie) {
        if(movie == null)
            return null;

        Movie movieUpdate = movieRepositoryJPA.findById(movie.getId()).orElse(null);

        if(movieUpdate == null)
            return null;

//        movieUpdate.setMovieUpdate(movie.getId(), movie.getTitle(), movie.getDescription(), movie.getGender(),
//                movie.getDuration(), movie.getMovieRating(), movie.getImgUrl(), movie.getPublicId(),
//                movie.getImgUrlBackground(), movie.getPublicIdImgBackground(), movie.getStatusMovie());
        movieUpdate.setImgUpdate(movie.getImgUrl(), movie.getPublicId(),
                movie.getImgUrlBackground(), movie.getPublicIdImgBackground());

        return movieRepositoryJPA.save(movieUpdate);
    }

    @Override
    public Movie delete(UUID movieId) {
        Movie movieDelete = movieRepositoryJPA.findById(movieId).orElse(null);

        if(movieDelete == null)
            return null;

        movieRepositoryJPA.delete(movieDelete);
        return movieDelete;
    }
}
