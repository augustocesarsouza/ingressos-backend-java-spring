package com.backend.ingresso.data.repositories;

import com.backend.ingresso.data.context.MovieRepositoryJPA;
import com.backend.ingresso.domain.entities.Movie;
import com.backend.ingresso.domain.repositories.IMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MovieRepository implements IMovieRepository {
    private final MovieRepositoryJPA movieRepositoryJPA;

    @Autowired
    public MovieRepository(MovieRepositoryJPA movieRepositoryJPA) {
        this.movieRepositoryJPA = movieRepositoryJPA;
    }

    @Override
    public Movie getById(UUID movieId) {
        String query = movieRepositoryJPA.getMovieById_Info_Id_Title(movieId);

        if(query == null){
            return null;
        }

        String[] stringSplit = query.split(",");
        return new Movie(UUID.fromString(stringSplit[0]), stringSplit[1], null, null,
                null, null, null, null,
                null, null, null);
    }

    @Override
    public Movie getInfoMoviesById(UUID movieId) {
        String query = movieRepositoryJPA.getMovieById_Info_Id_Title_Description_Gender_Duration_MovieRating_ImgUrl_ImgUrlBackground(movieId);

        if(query == null){
            return null;
        }

        String[] stringSplit = query.split(",");
        return new Movie(UUID.fromString(stringSplit[0]), stringSplit[1], stringSplit[2], stringSplit[3],
                stringSplit[4], Integer.parseInt(stringSplit[5]), stringSplit[6], null,
                stringSplit[7], null, null);
    }

    @Override
    public Movie getStatusMovie(String statusMovie) {
        String query = movieRepositoryJPA.getMovieByStatusMovie_Info_Id_Title_Description_Gender_Duration_MovieRating_ImgUrl_StatusMovie(statusMovie);

        if(query == null){
            return null;
        }

        String[] stringSplit = query.split(",");
        return new Movie(UUID.fromString(stringSplit[0]), stringSplit[1], stringSplit[2], stringSplit[3],
                stringSplit[4], Integer.parseInt(stringSplit[5]), stringSplit[6], null,
                null, null, stringSplit[7]);
    }

    @Override
    public Movie getByIdAllProps(UUID movieId) {
        String query = movieRepositoryJPA.getMovieById_Info_All(movieId);

        if(query == null){
            return null;
        }

        String[] stringSplit = query.split(",");
        return new Movie(UUID.fromString(stringSplit[0]), stringSplit[1], stringSplit[2], stringSplit[3],
                stringSplit[4], Integer.parseInt(stringSplit[5]), stringSplit[6], stringSplit[7],
                stringSplit[8], stringSplit[9], stringSplit[10]);
    }

    @Override
    public List<Movie> getAllMovieByRegionId(UUID regionId) {
        List<Object[]> listObj = movieRepositoryJPA.getMovieByRegionId_Info_All(regionId);

        if(listObj == null){
            return null;
        }

        List<Movie> movieList = new ArrayList<>();

        listObj.forEach((listObjInner) -> {// testar isso
            Movie movie = new Movie(UUID.fromString(listObjInner[0].toString()), listObjInner[1].toString(), null, null,
                    null, Integer.parseInt(listObjInner[3].toString()), listObjInner[2].toString(), null, null,
                    null, null);
            movieList.add(movie);
        });

        return movieList;
    }

    @Override
    public Movie create(Movie movie) {
        if(movie == null)
            return null;


        return movieRepositoryJPA.save(movie);
    }

    @Override
    public Movie update(Movie movie) {
        if(movie == null)
            return null;

        Movie movieUpdate = movieRepositoryJPA.findById(movie.getId()).orElse(null);

        if(movieUpdate == null)
            return null;

        movieUpdate.setMovieUpdate(movie.getId(), movie.getTitle(), movie.getDescription(), movie.getGender(),
                movie.getDuration(), movie.getMovieRating(), movie.getImgUrl(), movie.getPublicId(),
                movie.getImgUrlBackground(), movie.getPublicIdImgBackground(), movie.getStatusMovie());

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
