package com.backend.ingresso.application.services;

import com.backend.ingresso.application.ErrorValidation;
import com.backend.ingresso.application.dto.AdditionalInfoUserDTO;
import com.backend.ingresso.application.dto.UserDTO;
import com.backend.ingresso.application.dto.validations.userValidationDTOs.UserPasswordChangeDTO;
import com.backend.ingresso.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.backend.ingresso.application.dto.validations.userValidationDTOs.UserCreateValidatorDTO;
import com.backend.ingresso.application.dto.validations.userValidationDTOs.UserUpdateValidatorDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IAdditionalInfoUserMapper;
import com.backend.ingresso.application.mappings.MappingClassInterface.IUserMapper;
import com.backend.ingresso.application.services.interfaces.IAdditionalInfoUserService;
import com.backend.ingresso.application.services.interfaces.IUserManagementService;
import com.backend.ingresso.application.util.interfaces.IBCryptPasswordEncoderUtil;
import com.backend.ingresso.data.utilityExternal.Interface.ISendEmailUser;
import com.backend.ingresso.domain.InfoErrors.InfoErrors;
import com.backend.ingresso.domain.entities.User;
import com.backend.ingresso.domain.repositories.IUserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UserManagementService implements IUserManagementService {
    private final IUserRepository userRepository;
    private final IUserMapper userMapper;
    private final IAdditionalInfoUserMapper additionalInfoUserMapper;
    private final IValidateErrorsDTO validateErrorsDTO;
    private final IAdditionalInfoUserService additionalInfoUserService;
    private final IBCryptPasswordEncoderUtil bCryptPasswordEncoder;
    private final ISendEmailUser sendEmailUser;
    private final AuthenticationManager authenticationManager;
    @Autowired
    public UserManagementService(IUserRepository userRepository, IValidateErrorsDTO validateErrorsDTO, IUserMapper userMapper, IAdditionalInfoUserService additionalInfoUserService,
                                 IBCryptPasswordEncoderUtil bCryptPasswordEncoder, IAdditionalInfoUserMapper additionalInfoUserMapper,
                                 ISendEmailUser sendEmailUser, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.validateErrorsDTO = validateErrorsDTO;
        this.userMapper = userMapper;
        this.additionalInfoUserService = additionalInfoUserService;
        this.additionalInfoUserMapper = additionalInfoUserMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.sendEmailUser = sendEmailUser;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public ResultService<UserCreateValidatorDTO> create(UserCreateValidatorDTO userCreateValidatorDTO, BindingResult result) {
        // ideia futura colocar aqui quando criar um usuario salvar na tabela 'tb_user_permissions' uma permissao padrão já para os usuarios
        if(result.hasErrors()){
            var errorsDTO = result.getAllErrors();
            var errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        User userExist = userRepository.checkUserExits(userCreateValidatorDTO.getEmail(), userCreateValidatorDTO.getCpf());

        if(userExist != null)
            return ResultService.Fail("email or cpf already exist");

        try {
            String passwordEncoder = bCryptPasswordEncoder.encodePassword(userCreateValidatorDTO.getPassword());

            UUID uuid_user_id = UUID.randomUUID();

            userCreateValidatorDTO.setIdPasswordHashConfirmEmail(uuid_user_id, passwordEncoder, false);

            UUID uuid_additionalInfoUserId = UUID.randomUUID();

            String birthDate = userCreateValidatorDTO.getBirthDateString();
            String regex = "\\d{2}/\\d{2}/\\d{4}";
            Pattern pattern = Pattern.compile(regex);
            AdditionalInfoUserDTO additionalInfoUserDTO;

            if(pattern.matcher(birthDate).matches()){
                DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
                DateTime dateBirthDate = DateTime.parse(birthDate, formatter);

                // Convertendo DateTime para Date
                Date utilDate = dateBirthDate.toDate();

                // Criando um Timestamp com o java.util.Date
                Timestamp timestamp = new Timestamp(utilDate.getTime());

                additionalInfoUserDTO = new AdditionalInfoUserDTO(uuid_additionalInfoUserId, timestamp, userCreateValidatorDTO.getGender(),
                        userCreateValidatorDTO.getPhone(), userCreateValidatorDTO.getCep(), userCreateValidatorDTO.getLogradouro(), userCreateValidatorDTO.getNumero(),
                        userCreateValidatorDTO.getComplemento(), userCreateValidatorDTO.getReferencia(), userCreateValidatorDTO.getBairro(),
                        userCreateValidatorDTO.getEstado(), userCreateValidatorDTO.getCidade(), uuid_user_id);
            }else{
                additionalInfoUserDTO = new AdditionalInfoUserDTO(uuid_additionalInfoUserId, null, userCreateValidatorDTO.getGender(),
                        userCreateValidatorDTO.getPhone(), userCreateValidatorDTO.getCep(), userCreateValidatorDTO.getLogradouro(), userCreateValidatorDTO.getNumero(),
                        userCreateValidatorDTO.getComplemento(), userCreateValidatorDTO.getReferencia(), userCreateValidatorDTO.getBairro(),
                        userCreateValidatorDTO.getEstado(), userCreateValidatorDTO.getCidade(), uuid_user_id);
            }

            User userCreate = userRepository.create(userMapper.userCreateValidatorDtoToUser(userCreateValidatorDTO));

            if(userCreate == null)
                return ResultService.Fail("error when create user");

            BindingResult resultValid = new BeanPropertyBindingResult(additionalInfoUserDTO, "additionalInfoUserDTO");
            CreateInfoUser(additionalInfoUserDTO, resultValid);
            InfoErrors<String> resultSend = sendMessageEmail(userCreate); // tem que tratar isso se nao mandar eu tenho que mandar algo para o frontend informando

            UserCreateValidatorDTO userCreateDTO = userMapper.userToUserCreateValidatorDto(userCreate);

            if(resultSend == null){
                userCreateDTO.setEmailSendSuccessfully(false);
            }else {
                userCreateDTO.setEmailSendSuccessfully(resultSend.IsSuccess);
            }

            return ResultService.Ok(userCreateDTO);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultService<UserDTO> update(UserUpdateValidatorDTO userUpdateValidatorDTO, BindingResult resultValid, String password) {
        if(userUpdateValidatorDTO == null)
            return ResultService.Fail("obj null");

        var resultValidateDTO = validateDTOUser(userUpdateValidatorDTO.getId(), resultValid);
        if(!resultValidateDTO.IsSuccess)
            return resultValidateDTO;

        if(password == null)
            return ResultService.Fail("password is null");

        try {
            var user = userRepository.getByIdOnlyEmailOrCpfId(UUID.fromString(userUpdateValidatorDTO.getId()));
            if(user == null)
                return ResultService.Fail("user not found");

            var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), password);
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            if(!authenticate.isAuthenticated())
                return ResultService.Fail("password is not valid");

            user.changeNameUser(userUpdateValidatorDTO.getName());
            var userChange = userRepository.update(user);

            if(userChange == null)
                return ResultService.Fail("error when updating user");

            return ResultService.Ok(userMapper.userToUserDto(userChange));
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultService<UserDTO> updateUserPassword(UserPasswordChangeDTO userPasswordChangeDTO, BindingResult resultValid) {
        if(userPasswordChangeDTO == null)
            return ResultService.Fail("obj null");

        var resultValidateDTO = validateDTOUser(userPasswordChangeDTO.getIdGuid(), resultValid);

        if(!resultValidateDTO.IsSuccess)
            return resultValidateDTO;

        try {
            var user = userRepository.getByIdOnlyEmailOrCpfId(UUID.fromString(userPasswordChangeDTO.getIdGuid()));
            if(user == null)
                return ResultService.Fail("user not found");

            var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), userPasswordChangeDTO.getPasswordCurrent());
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            if(!authenticate.isAuthenticated())
                return ResultService.Fail("password is not valid");

            String passwordEncoder = bCryptPasswordEncoder.encodePassword(userPasswordChangeDTO.getNewPassword());

            user.changePasswordHash(passwordEncoder);

            var userChange = userRepository.update(user);

            if(userChange == null)
                return ResultService.Fail("error when updating user");

            return ResultService.Ok(userMapper.userToUserDto(userChange));
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    private ResultService<UserDTO> validateDTOUser(String idGuid, BindingResult resultValid){
        if(resultValid.hasErrors()){
            var errorsDTO = resultValid.getAllErrors();
            List<ErrorValidation> errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }else {
            String regex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
            Pattern pattern = Pattern.compile(regex);

            if(!pattern.matcher(idGuid).matches()){
                List<ErrorValidation> errors = new ArrayList<>();
                var errorValidation = new ErrorValidation("id", "invalid format id must be UUID");
                errors.add(errorValidation);
                return ResultService.RequestError("error validate DTO", errors);
            }
        }

        return ResultService.Ok("everything ok correct DTO and UUID");
    }

    private void CreateInfoUser(@Valid AdditionalInfoUserDTO additionalInfoUserDTO, BindingResult resultValid){
        additionalInfoUserService.create(additionalInfoUserMapper.additionalInfoUserDtoToAdditionalInfoUserCreateDto(additionalInfoUserDTO), resultValid);
    }

    private InfoErrors<String> sendMessageEmail(User user){
        return sendEmailUser.sendEmail(user);
    }
}
