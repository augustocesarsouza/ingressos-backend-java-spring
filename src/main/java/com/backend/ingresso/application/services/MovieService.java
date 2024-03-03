package com.backend.ingresso.application.services;

import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.backend.ingresso.application.dto.validations.movieValidationDTOs.MovieCreateValidatorDTO;
import com.backend.ingresso.application.dto.validations.movieValidationDTOs.MovieUpdateValidatorDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IMovieMapper;
import com.backend.ingresso.application.services.interfaces.IMovieService;
import com.backend.ingresso.application.services.interfaces.IRegionService;
import com.backend.ingresso.application.util.ValidateUUID;
import com.backend.ingresso.data.utilityExternal.Interface.ICloudinaryUti;
import com.backend.ingresso.domain.entities.Movie;
import com.backend.ingresso.domain.repositories.IMovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MovieService implements IMovieService {
    private final IMovieRepository movieRepository;
    private final IRegionService regionService;
    private final IValidateErrorsDTO validateErrorsDTO;
    private final ICloudinaryUti cloudinaryUti;
    private final IMovieMapper movieMapper;

    @Autowired
    public MovieService(IMovieRepository movieRepository, IRegionService regionService, IValidateErrorsDTO validateErrorsDTO,
                        ICloudinaryUti cloudinaryUti, IMovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.regionService = regionService;
        this.validateErrorsDTO = validateErrorsDTO;
        this.cloudinaryUti = cloudinaryUti;
        this.movieMapper = movieMapper;
    }

    @Override
    public ResultService<List<MovieDTO>> getAllMovieByRegionId(String region) {
        ResultService<RegionDTO> regionId = regionService.getIdByNameState(region);

        if(!regionId.IsSuccess)
            return ResultService.Fail(regionId.Message);

        List<MovieDTO> movieAll = movieRepository.getAllMovieByRegionId(regionId.Data.getId());

        if(movieAll == null)
            return ResultService.Fail("error get all movies by regionId");

        return ResultService.Ok(movieAll);
    }

    @Override
    @Transactional
    public ResultService<MovieDTO> getInfoMoviesById(UUID movieId) {
        try {
            Movie movie = movieRepository.getInfoMoviesById(movieId);

            if(movie == null)
                return ResultService.Fail("error get info movies by id");

            return ResultService.Ok(movieMapper.movieToMovieDto(movie));
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultService<MovieDTO> getIdMovieByTitle(String title) {
        try {
            Movie movie = movieRepository.getMovieByTitle(title);

            if(movie == null)
                return ResultService.Fail("movie not found");

            return ResultService.Ok(movieMapper.movieToMovieDto(movie));
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultService<MovieDTO> getStatusMovie(String statusMovie) {
        try {
            Movie movie = movieRepository.getStatusMovie(statusMovie);

            if(movie == null)
                return ResultService.Fail("statusMovie not found");

            return ResultService.Ok(movieMapper.movieToMovieDto(movie));
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultService<MovieDTO> create(MovieCreateValidatorDTO movieCreateValidatorDTO, BindingResult result) {
        if(result.hasErrors()){
            var errorsDTO = result.getAllErrors();
            var errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        try {
            UUID movieId = UUID.randomUUID();
            movieCreateValidatorDTO.setId(movieId);

            var resultCreateImgMain = cloudinaryUti.CreateImg(movieCreateValidatorDTO.getBase64Img(), 625, 919);

            if(!resultCreateImgMain.IsSuccess)
                return ResultService.Fail(resultCreateImgMain.Message);

            var resultCreateImgBackground = cloudinaryUti.CreateImg(movieCreateValidatorDTO.getBase64Img(), 1440, 500);

            if(!resultCreateImgBackground.IsSuccess)
                return ResultService.Fail(resultCreateImgBackground.Message);

            movieCreateValidatorDTO.setImgUrl(resultCreateImgMain.Data.getImgUrl());
            movieCreateValidatorDTO.setPublicId(resultCreateImgMain.Data.getPublicId());

            movieCreateValidatorDTO.setImgUrlBackground(resultCreateImgBackground.Data.getImgUrl());
            movieCreateValidatorDTO.setPublicIdImgBackground(resultCreateImgBackground.Data.getPublicId());

            var movieCreate = movieRepository.create(movieMapper.movieCreateValidatorDTOToMovie(movieCreateValidatorDTO));
            if(movieCreate == null)
                return ResultService.Fail("error when creating movie");

            return ResultService.Ok(movieMapper.movieToMovieDto(movieCreate));
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultService<MovieDTO> deleteMovie(UUID movieId) {
        try {
            var movieDelete = movieRepository.getMovieByIdForDelete(movieId);

            if(movieDelete == null)
                return ResultService.Fail("not found movie");

            List<String> publicList = new ArrayList<>();
            publicList.add(movieDelete.getPublicId());
            publicList.add(movieDelete.getPublicIdImgBackground());

            var resultDeleteAllImgCloudinary = cloudinaryUti.deleteImg(publicList);

            if(!resultDeleteAllImgCloudinary.IsSuccess)
                return ResultService.Fail(resultDeleteAllImgCloudinary.Message);

            var deleteMovie = movieRepository.delete(movieDelete.getId());

            if(deleteMovie == null)
                return ResultService.Fail("error found when deleting movie in repository");

            return ResultService.Ok(movieMapper.movieToMovieDto(deleteMovie));
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultService<MovieDTO> updateMovieImg(MovieUpdateValidatorDTO movieUpdateValidatorDTO, BindingResult result) {
        if(result.hasErrors()){
            var errorsDTO = result.getAllErrors();
            var errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        try {
            boolean validateUUID = ValidateUUID.Validate(movieUpdateValidatorDTO.getId());

            if(!validateUUID)
                return ResultService.Fail("error is not a uuid valid");

            var movieUpdate = movieRepository.getMovieByIdForDelete(UUID.fromString(movieUpdateValidatorDTO.getId()));

            if(movieUpdate == null)
                return ResultService.Fail("not found movie");

            List<String> publicList = new ArrayList<>();
            publicList.add(movieUpdate.getPublicId());
            publicList.add(movieUpdate.getPublicIdImgBackground());

            var resultDeleteAllImgCloudinary = cloudinaryUti.deleteImg(publicList);

            if(!resultDeleteAllImgCloudinary.IsSuccess)
                return ResultService.Fail(resultDeleteAllImgCloudinary.Message);

            var resultCreateImgMain = cloudinaryUti.CreateImg(movieUpdateValidatorDTO.getBase64Img(), 625, 919);

            if(!resultCreateImgMain.IsSuccess)
                return ResultService.Fail(resultCreateImgMain.Message);

            var resultCreateImgBackground = cloudinaryUti.CreateImg(movieUpdateValidatorDTO.getBase64Img(), 1440, 500);

            if(!resultCreateImgBackground.IsSuccess)
                return ResultService.Fail(resultCreateImgBackground.Message);

            movieUpdateValidatorDTO.setImgUrl(resultCreateImgMain.Data.getImgUrl());
            movieUpdateValidatorDTO.setPublicId(resultCreateImgMain.Data.getPublicId());

            movieUpdateValidatorDTO.setImgUrlBackground(resultCreateImgBackground.Data.getImgUrl());
            movieUpdateValidatorDTO.setPublicIdImgBackground(resultCreateImgBackground.Data.getPublicId());

            var updateMovie = movieRepository.updateImg(movieMapper.movieUpdateValidatorDTOToMovie(movieUpdateValidatorDTO));

            if(updateMovie == null)
                return ResultService.Fail("error when update movie");

            return ResultService.Ok(movieMapper.movieToMovieDto(updateMovie));

        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }
}
