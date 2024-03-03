package com.backend.ingresso.application.services;

import com.backend.ingresso.application.dto.AdditionalInfoUserDTO;
import com.backend.ingresso.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.backend.ingresso.application.dto.validations.AdditionalInfoUserValidationDTOs.AdditionalInfoUserCreateDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IAdditionalInfoUserMapper;
import com.backend.ingresso.application.services.interfaces.IAdditionalInfoUserService;
import com.backend.ingresso.domain.entities.AdditionalInfoUser;
import com.backend.ingresso.domain.entities.User;
import com.backend.ingresso.domain.repositories.IAdditionalInfoUserRepository;
import com.backend.ingresso.domain.repositories.IUserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class AdditionalInfoUserService implements IAdditionalInfoUserService {
    private final IAdditionalInfoUserRepository additionalInfoUserRepository;
    private final IUserRepository userRepository;
    private final IValidateErrorsDTO validateErrorsDTO;
    private final IAdditionalInfoUserMapper additionalInfoUserMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AdditionalInfoUserService(IAdditionalInfoUserRepository additionalInfoUserRepository, IValidateErrorsDTO validateErrorsDTO,
                                     IAdditionalInfoUserMapper additionalInfoUserMapper, IUserRepository userRepository) {
        this.additionalInfoUserRepository = additionalInfoUserRepository;
        this.validateErrorsDTO = validateErrorsDTO;
        this.additionalInfoUserMapper = additionalInfoUserMapper;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public ResultService<AdditionalInfoUserDTO> getInfoUser(String idGuid) throws ParseException {
        try {
            var userInfo = additionalInfoUserRepository.getInfoUser(UUID.fromString(idGuid));

            if(userInfo == null)
                return ResultService.Fail("Erro obj info user null");

            return ResultService.Ok(additionalInfoUserMapper.additionalInfoUserToadditionalInfoUserDto(userInfo));
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
       // return ResultService.Ok("ok");
    }

    @Override
    public ResultService<AdditionalInfoUserDTO> create(@Valid AdditionalInfoUserCreateDTO additionalInfoUserCreateDTO, BindingResult resultValid) {
        if(additionalInfoUserCreateDTO == null)
            return ResultService.Fail("Error info user null");

        if(resultValid.hasErrors()){
            var errorsDTO = resultValid.getAllErrors();
            var errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        try {
            AdditionalInfoUser additionalInfoUser = additionalInfoUserRepository.create(additionalInfoUserMapper.additionalInfoUserCreateDtoToadditionalInfoUser(additionalInfoUserCreateDTO));

            if(additionalInfoUser == null)
                return ResultService.Fail("database returned null create additionalInfoUser");

            return ResultService.Ok(additionalInfoUserMapper.additionalInfoUserToadditionalInfoUserDto(additionalInfoUser));
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Transactional
    @Override
    public ResultService<AdditionalInfoUserDTO> update(@Valid AdditionalInfoUserCreateDTO additionalInfoUserCreateDTO, String password) {
        if(additionalInfoUserCreateDTO == null)
            return ResultService.Fail("Error info user null");

        UUID userId = additionalInfoUserCreateDTO.getUserId();
        if(userId == null)
            return ResultService.Fail("error userId is null");

        User userUpdate = userRepository.getByUserIdOnlyPasswordHash(userId);
        if(userUpdate == null)
            return ResultService.Fail("user not found");

        try{
            //String hashStringPasswordProvided = generateHashPassword.HashPassword(password);
            String hashStringPasswordProvided = bCryptPasswordEncoder.encode(password);
            String hashStringPasswordUserDatabase = userUpdate.getPasswordHash();

            if(!hashStringPasswordUserDatabase.equals(hashStringPasswordProvided))
                return ResultService.Fail("password is not valid");

            AdditionalInfoUser additionalInfoUser = additionalInfoUserRepository.getByIdGuidUser(additionalInfoUserCreateDTO.getUserId());

            if(additionalInfoUser == null)
                return ResultService.Fail("error when get data additionalInfoUser");

            String birthDate = additionalInfoUserCreateDTO.getBirthDateString();

            if(birthDate != null){
                String regex = "\\d{2}/\\d{2}/\\d{4}";
                Pattern pattern = Pattern.compile(regex);

                if(pattern.matcher(birthDate).matches()){
                    DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
                    DateTime dateBirthDate = DateTime.parse(birthDate, formatter);

                    // Convertendo DateTime para Date
                    Date utilDate = dateBirthDate.toDate();

                    // Criando um Timestamp com o java.util.Date
                    Timestamp timestamp = new Timestamp(utilDate.getTime());

                    additionalInfoUser.addData(timestamp, additionalInfoUserCreateDTO.getGender(), additionalInfoUserCreateDTO.getPhone(),
                            additionalInfoUserCreateDTO.getCep(), additionalInfoUserCreateDTO.getLogradouro(), additionalInfoUserCreateDTO.getNumero(),
                            additionalInfoUserCreateDTO.getComplemento(), additionalInfoUserCreateDTO.getReferencia(), additionalInfoUserCreateDTO.getBairro(),
                            additionalInfoUserCreateDTO.getEstado(), additionalInfoUserCreateDTO.getCidade(), additionalInfoUserCreateDTO.getUserId());
                }else{
                    return ResultService.Fail("error data should be in this format dd/mm/AAA");
                }
            }else{
                additionalInfoUser.addData(null, additionalInfoUserCreateDTO.getGender(), additionalInfoUserCreateDTO.getPhone(),
                        additionalInfoUserCreateDTO.getCep(), additionalInfoUserCreateDTO.getLogradouro(), additionalInfoUserCreateDTO.getNumero(),
                        additionalInfoUserCreateDTO.getComplemento(), additionalInfoUserCreateDTO.getReferencia(), additionalInfoUserCreateDTO.getBairro(),
                        additionalInfoUserCreateDTO.getEstado(), additionalInfoUserCreateDTO.getCidade(), additionalInfoUserCreateDTO.getUserId());
            }

            AdditionalInfoUser userInfoUpdate = additionalInfoUserRepository.update(additionalInfoUser);

            if(userInfoUpdate == null)
                return ResultService.Fail("error when update user");

            return ResultService.Ok(additionalInfoUserMapper.additionalInfoUserToadditionalInfoUserDto(userInfoUpdate));

        }catch (Exception ex){
            String  error = ex.getMessage();
            return ResultService.Fail(error);
        }
    }
}
