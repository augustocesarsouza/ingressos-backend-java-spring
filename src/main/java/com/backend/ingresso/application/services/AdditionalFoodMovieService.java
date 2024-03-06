package com.backend.ingresso.application.services;

import com.backend.ingresso.application.ErrorValidation;
import com.backend.ingresso.application.dto.AdditionalFoodMovieDTO;
import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.backend.ingresso.application.dto.validations.additionalFoodMovieDTOs.AdditionalFoodMovieCreate;
import com.backend.ingresso.application.mappings.MappingClassInterface.IAdditionalFoodMovieMapper;
import com.backend.ingresso.application.services.interfaces.IAdditionalFoodMovieService;
import com.backend.ingresso.application.services.interfaces.IMovieService;
import com.backend.ingresso.application.util.ValidateUUID;
import com.backend.ingresso.data.cloudinaryUtil.CloudinaryCreate;
import com.backend.ingresso.data.utilityExternal.Interface.ICloudinaryUti;
import com.backend.ingresso.domain.entities.AdditionalFoodMovie;
import com.backend.ingresso.domain.repositories.IAdditionalFoodMovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

@Service
public class AdditionalFoodMovieService implements IAdditionalFoodMovieService {
    private final IAdditionalFoodMovieRepository additionalFoodMovieRepository;
    private final IValidateErrorsDTO validateErrorsDTO;
    private final IMovieService movieService;
    private final ICloudinaryUti cloudinaryUti;
    private final IAdditionalFoodMovieMapper additionalFoodMovieMapper;

    @Autowired
    public AdditionalFoodMovieService(IAdditionalFoodMovieRepository additionalFoodMovieRepository,
                                      IValidateErrorsDTO validateErrorsDTO, IMovieService movieService,
                                      ICloudinaryUti cloudinaryUti, IAdditionalFoodMovieMapper additionalFoodMovieMapper) {
        this.additionalFoodMovieRepository = additionalFoodMovieRepository;
        this.validateErrorsDTO = validateErrorsDTO;
        this.movieService = movieService;
        this.cloudinaryUti = cloudinaryUti;
        this.additionalFoodMovieMapper = additionalFoodMovieMapper;
    }

    @Override
    @Transactional
    public ResultService<List<AdditionalFoodMovieDTO>> getAllFoodMovie(UUID movieId) {
        try {
            List<AdditionalFoodMovieDTO> getAllFood = additionalFoodMovieRepository.getAllFoodMovie(movieId);

            return ResultService.Ok(getAllFood);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultService<AdditionalFoodMovieDTO> create(AdditionalFoodMovieCreate additionalFoodMovieCreate, BindingResult result) {
        if(additionalFoodMovieCreate == null)
            return ResultService.Fail("error DTO Create Null");

        if(result.hasErrors()){
            var errorsDTO = result.getAllErrors();
            List<ErrorValidation> errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        try {
            if(!ValidateUUID.Validate(additionalFoodMovieCreate.getMovieId()))
                return ResultService.Fail("error movieId, not is UUID valid");

            UUID movieId = UUID.fromString(additionalFoodMovieCreate.getMovieId());

            ResultService<MovieDTO> movieDTO = movieService.getCheckIfMovieExistsById(movieId);

            if(!movieDTO.IsSuccess)
                return ResultService.Fail("error movie not exist");

            ResultService<CloudinaryCreate> resultCreateImgMain = cloudinaryUti.CreateImg(additionalFoodMovieCreate.getBase64Img(), 210, 210);

            if(!resultCreateImgMain.IsSuccess)
                return ResultService.Fail(resultCreateImgMain.Message);

            CloudinaryCreate cloudinaryCreate = resultCreateImgMain.Data;
            UUID id = UUID.randomUUID();

            AdditionalFoodMovie additionalFoodMovie = new AdditionalFoodMovie(id, additionalFoodMovieCreate.getTitle(),
                    additionalFoodMovieCreate.getPrice(), additionalFoodMovieCreate.getFee(),
                    cloudinaryCreate.getImgUrl(), cloudinaryCreate.getPublicId(),
                    movieId, null);

            AdditionalFoodMovie resultCreateAdd = additionalFoodMovieRepository.create(additionalFoodMovie);

            if(resultCreateAdd == null)
                return ResultService.Fail("error create repository additional");

            return ResultService.Ok(additionalFoodMovieMapper.additionalFoodMovieToAdditionalFoodMovieDto(resultCreateAdd));

        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }
}
