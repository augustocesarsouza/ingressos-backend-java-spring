package com.backend.ingresso.application.mappings.MappingClass;

import com.backend.ingresso.application.dto.CinemaMovieDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.ICinemaMapper;
import com.backend.ingresso.application.mappings.MappingClassInterface.ICinemaMovieMapper;
import com.backend.ingresso.application.mappings.MappingClassInterface.IMovieMapper;
import com.backend.ingresso.application.mappings.MappingClassInterface.IRegionMapper;
import com.backend.ingresso.domain.entities.Cinema;
import com.backend.ingresso.domain.entities.CinemaMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CinemaMovieMapper implements ICinemaMovieMapper {
    private final ICinemaMapper cinemaMapper;
    private final IMovieMapper movieMapper;
    private final IRegionMapper regionMapper;

    @Autowired
    public CinemaMovieMapper(ICinemaMapper cinemaMapper, IMovieMapper movieMapper, IRegionMapper regionMapper) {
        this.cinemaMapper = cinemaMapper;
        this.movieMapper = movieMapper;
        this.regionMapper = regionMapper;
    }

    @Override
    public CinemaMovieDTO cinemaMovieToCinemaMovieDto(CinemaMovie cinemaMovie) {
        if(cinemaMovie == null)
            return null;

        return new CinemaMovieDTO(cinemaMovie.getId(), cinemaMovie.getCinemaId(), cinemaMapper.cinemaToCinemaDto(cinemaMovie.getCinema()),
                cinemaMovie.getMovieId(), movieMapper.movieToMovieDto(cinemaMovie.getMovie()),
                cinemaMovie.getRegionId(),regionMapper.regionToRegionDto(cinemaMovie.getRegion()), cinemaMovie.getScreeningSchedule());
    }

    @Override
    public CinemaMovie cinemaMovieDtoToCinemaMovie(CinemaMovieDTO cinemaMovieDTO) {
        if(cinemaMovieDTO == null)
            return null;

        return new CinemaMovie(cinemaMovieDTO.getId(), cinemaMovieDTO.getCinemaId(), cinemaMapper.cinemaDtoToCinema(cinemaMovieDTO.getCinema()),
                cinemaMovieDTO.getMovieId(), movieMapper.movieDtoToMovie(cinemaMovieDTO.getMovie()),
                cinemaMovieDTO.getRegionId(),regionMapper.regionDtoToRegion(cinemaMovieDTO.getRegion()), cinemaMovieDTO.getScreeningSchedule());
    }

    @Override//nao estou usando
    public List<CinemaMovie> cinemaMovieListDtoToCinemaMovieListCinemaInclude(List<CinemaMovie> cinemaMovieList) {
        if(cinemaMovieList == null)
            return null;

        List<CinemaMovie> cinemaMovies = new ArrayList<>();

        cinemaMovieList.forEach((el) -> {
            Cinema cinema = new Cinema(el.getCinema().getId(), el.getCinema().getNameCinema(),
                    el.getCinema().getDistrict(), el.getCinema().getRanking());
            CinemaMovie cinemaMovie = new CinemaMovie(el.getId(), null, cinema, el.getMovieId(),
                    null, el.getRegionId(), null, el.getScreeningSchedule());
            cinemaMovies.add(cinemaMovie);
        });

        return cinemaMovies;
    }
}
