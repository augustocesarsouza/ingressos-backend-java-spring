package com.backend.ingresso.applicationTest.userServiceTest;

import com.backend.ingresso.application.ErrorValidation;
import com.backend.ingresso.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.backend.ingresso.application.dto.validations.userValidationDTOs.UserCreateValidatorDTO;
import com.backend.ingresso.application.dto.validations.userValidationDTOs.UserPasswordChangeDTO;
import com.backend.ingresso.application.dto.validations.userValidationDTOs.UserUpdateValidatorDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IAdditionalInfoUserMapper;
import com.backend.ingresso.application.mappings.MappingClassInterface.IUserMapper;
import com.backend.ingresso.application.services.UserManagementService;
import com.backend.ingresso.application.services.interfaces.IAdditionalInfoUserService;
import com.backend.ingresso.application.util.interfaces.IBCryptPasswordEncoderUtil;
import com.backend.ingresso.data.utilityExternal.Interface.ISendEmailUser;
import com.backend.ingresso.domain.InfoErrors.InfoErrors;
import com.backend.ingresso.domain.entities.User;
import com.backend.ingresso.domain.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@RunWith(Suite.class)
//@Suite.SuiteClasses({/* outras classes de teste */})
public class UserManagementServiceTest {
    @Mock
    private IUserRepository userRepository;
    @Mock
    private IUserMapper userMapper;
    @Mock
    private IAdditionalInfoUserMapper additionalInfoUserMapper;
    @Mock
    private IValidateErrorsDTO validateErrorsDTO;
    @Mock
    private IAdditionalInfoUserService additionalInfoUserService;
    @Mock
    private IBCryptPasswordEncoderUtil bCryptPasswordEncoder;
    @Mock
    private ISendEmailUser sendEmailUser;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private Authentication authentication; // para usar isAuthenticated

    private UserManagementService userManagementService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        userManagementService = new UserManagementService(userRepository, validateErrorsDTO, userMapper,
                additionalInfoUserService,bCryptPasswordEncoder, additionalInfoUserMapper,sendEmailUser, authenticationManager);
        authentication = mock(Authentication.class);
    }
    //CREATE//
    @Test
    public void shouldCreateAccountWithoutErrors(){
        UserCreateValidatorDTO userCreateValidatorDTO = new UserCreateValidatorDTO();
        userCreateValidatorDTO.setBirthDateString("05/10/1999");

        var userCreateDTO = new UserCreateValidatorDTO();

        when(userRepository.checkUserExits(any(), any())).thenReturn(null);
        when(bCryptPasswordEncoder.encodePassword(any())).thenReturn("passwordrandom");
        when(userMapper.userCreateValidatorDtoToUser(any(UserCreateValidatorDTO.class)))
                .thenReturn(new User());
        when(userRepository.create(any(User.class))).thenReturn(new User());
        when(sendEmailUser.sendEmail(any(User.class)))
                .thenReturn(new InfoErrors<>(true, "tudo certo", null));
        when(userMapper.userToUserCreateValidatorDto(any(User.class)))
                .thenReturn(userCreateDTO);

        var resultError = new BeanPropertyBindingResult(userCreateValidatorDTO, "userCreateValidatorDTO");

        // Act
        var result = userManagementService.create(userCreateValidatorDTO, resultError);

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(result.Data, userCreateDTO);
    }

    @Test
    public void shouldCreateAccountWithErrorDTO(){
        UserCreateValidatorDTO userCreateValidatorDTO = new UserCreateValidatorDTO();

        var resultError = new BeanPropertyBindingResult(userCreateValidatorDTO, "userCreateValidatorDTO");
        resultError.rejectValue("Name", "NotEmpty", "name should not be empty");

        List<ErrorValidation> errors = new ArrayList<>();
        var errorValidation = new ErrorValidation("Name", "name should not be empty");
        errors.add(errorValidation);

        when(validateErrorsDTO.ValidateDTO(anyList())).thenReturn(errors);

        // Act
        var result = userManagementService.create(userCreateValidatorDTO, resultError);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error validate DTO");
        assertEquals(result.Errors, errors);
    }

    @Test
    public void shouldCreateAccountWithErrorCheckUserExistNotNull(){
        UserCreateValidatorDTO userCreateValidatorDTO = new UserCreateValidatorDTO();

        var resultError = new BeanPropertyBindingResult(userCreateValidatorDTO, "userCreateValidatorDTO");

        when(userRepository.checkUserExits(any(), any())).thenReturn(new User());

        // Act
        var result = userManagementService.create(userCreateValidatorDTO, resultError);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "email or cpf already exist");
    }

    @Test
    public void shouldCreateAccountWithErrorBirthDateNotInformed(){
        UserCreateValidatorDTO userCreateValidatorDTO = new UserCreateValidatorDTO();

        when(userRepository.checkUserExits(any(), any())).thenReturn(null);
        when(bCryptPasswordEncoder.encodePassword(any())).thenReturn("seila");

        var resultError = new BeanPropertyBindingResult(userCreateValidatorDTO, "userCreateValidatorDTO");

        // Act
        var result = userManagementService.create(userCreateValidatorDTO, resultError);

        // Assert
        assertFalse(result.IsSuccess);
//        assertTrue(result.IsSuccess);
    }

    @Test
    public void shouldCreateAccountWithErrorUserNotCreated(){
        UserCreateValidatorDTO userCreateValidatorDTO = new UserCreateValidatorDTO();
        userCreateValidatorDTO.setBirthDateString("05/10/1999");

        when(userRepository.checkUserExits(any(), any())).thenReturn(null);
        when(bCryptPasswordEncoder.encodePassword(any())).thenReturn("passwordrandom");
        when(userMapper.userCreateValidatorDtoToUser(any(UserCreateValidatorDTO.class)))
                .thenReturn(new User());
        when(userRepository.create(any(User.class))).thenReturn(null);

        var resultError = new BeanPropertyBindingResult(userCreateValidatorDTO, "userCreateValidatorDTO");

        // Act
        var result = userManagementService.create(userCreateValidatorDTO, resultError);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error when create user");
    }
    @Test
    public void shouldCreateAccountWithErrorUserCreateDTONull(){
        UserCreateValidatorDTO userCreateValidatorDTO = new UserCreateValidatorDTO();
        userCreateValidatorDTO.setBirthDateString("05/10/1999");

        when(userRepository.checkUserExits(any(), any())).thenReturn(null);
        when(bCryptPasswordEncoder.encodePassword(any())).thenReturn("passwordrandom");
        when(userMapper.userCreateValidatorDtoToUser(any(UserCreateValidatorDTO.class)))
                .thenReturn(new User());
        when(userRepository.create(any(User.class))).thenReturn(new User());
        when(sendEmailUser.sendEmail(any(User.class)))
                .thenReturn(new InfoErrors<>(true, "tudo certo", null));
        when(userMapper.userToUserCreateValidatorDto(any(User.class)))
                .thenReturn(null);

        var resultError = new BeanPropertyBindingResult(userCreateValidatorDTO, "userCreateValidatorDTO");

        // Act
        var result = userManagementService.create(userCreateValidatorDTO, resultError);

        // Assert
        assertFalse(result.IsSuccess);
    }

    //UPDATE//
    @Test
    public void shouldUpdateUserWithoutErrors(){
        UserUpdateValidatorDTO userUpdateValidatorDTO =
                new UserUpdateValidatorDTO("5aabf615-0286-4962-b324-41b6f76014ae",
                        "seilaname");
        String password = "password1";

        var resultError = new BeanPropertyBindingResult(userUpdateValidatorDTO, "userUpdateValidatorDTO");

        when(userRepository.getByIdOnlyEmailOrCpfId(any())).thenReturn(new User());
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(userRepository.update(any(User.class))).thenReturn(new User());

        var result = userManagementService.update(userUpdateValidatorDTO, resultError, password);

        assertTrue(result.IsSuccess);
    }

    @Test
    public void shouldUpdateUserWithErrorUserDTONull(){
        UserUpdateValidatorDTO userUpdateValidatorDTO = null;
        String password = "password1";

        var resultError = new BeanPropertyBindingResult(userUpdateValidatorDTO, "userUpdateValidatorDTO");

        var result = userManagementService.update(userUpdateValidatorDTO, resultError, password);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "obj null");
    }

    @Test
    public void shouldUpdateUserWithErrorDTO(){
        UserUpdateValidatorDTO userUpdateValidatorDTO =
                new UserUpdateValidatorDTO("5aabf615-0286-4962-b324-41b6f76014ae",
                        null);
        String password = "password1";

        var resultError = new BeanPropertyBindingResult(userUpdateValidatorDTO, "userUpdateValidatorDTO");
        resultError.rejectValue("name", "NotEmpty", "name should not be empty");

        List<ErrorValidation> errors = new ArrayList<>();
        var errorValidation = new ErrorValidation("name", "name should not be empty");
        errors.add(errorValidation);

        when(validateErrorsDTO.ValidateDTO(anyList())).thenReturn(errors);

        var result = userManagementService.update(userUpdateValidatorDTO, resultError, password);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error validate DTO");
        assertEquals(result.Errors, errors);
    }

    @Test
    public void shouldUpdateUserWithErrorUUIDNotMatchPatternOfTheUUID(){
        UserUpdateValidatorDTO userUpdateValidatorDTO =
                new UserUpdateValidatorDTO("5aabf615-0286-4962-b324-41b6f76014a",// not have 36 character
                        "seilaname");
        String password = "password1";

        var resultError = new BeanPropertyBindingResult(userUpdateValidatorDTO, "userUpdateValidatorDTO");

        var result = userManagementService.update(userUpdateValidatorDTO, resultError, password);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error validate DTO");
    }

    @Test
    public void shouldUpdateUserWithErrorPasswordNull(){
        UserUpdateValidatorDTO userUpdateValidatorDTO =
                new UserUpdateValidatorDTO("5aabf615-0286-4962-b324-41b6f76014ae",
                        "seilaname");
        String password = null;

        var resultError = new BeanPropertyBindingResult(userUpdateValidatorDTO, "userUpdateValidatorDTO");

        var result = userManagementService.update(userUpdateValidatorDTO, resultError, password);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "password is null");
    }
    @Test
    public void shouldUpdateUserWithErrorUserNotExist(){
        UserUpdateValidatorDTO userUpdateValidatorDTO =
                new UserUpdateValidatorDTO("5aabf615-0286-4962-b324-41b6f76014ae",
                        "seilaname");
        String password = "password1";

        var resultError = new BeanPropertyBindingResult(userUpdateValidatorDTO, "userUpdateValidatorDTO");

        when(userRepository.getByIdOnlyEmailOrCpfId(any())).thenReturn(null);

        var result = userManagementService.update(userUpdateValidatorDTO, resultError, password);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "user not found");
    }

    @Test
    public void shouldUpdateUserWithErrorPasswordNotValid(){
        UserUpdateValidatorDTO userUpdateValidatorDTO =
                new UserUpdateValidatorDTO("5aabf615-0286-4962-b324-41b6f76014ae",
                        "seilaname");
        String password = "password1";

        var resultError = new BeanPropertyBindingResult(userUpdateValidatorDTO, "userUpdateValidatorDTO");

        when(userRepository.getByIdOnlyEmailOrCpfId(any())).thenReturn(new User());
        when(authentication.isAuthenticated()).thenReturn(false);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
//        when(userRepository.update(any(User.class))).thenReturn(new User());

        var result = userManagementService.update(userUpdateValidatorDTO, resultError, password);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "password is not valid");
    }

    @Test
    public void shouldUpdateUserWithErrorWhenUpdatingUser(){
        UserUpdateValidatorDTO userUpdateValidatorDTO =
                new UserUpdateValidatorDTO("5aabf615-0286-4962-b324-41b6f76014ae",
                        "seilaname");
        String password = "password1";

        var resultError = new BeanPropertyBindingResult(userUpdateValidatorDTO, "userUpdateValidatorDTO");

        when(userRepository.getByIdOnlyEmailOrCpfId(any())).thenReturn(new User());
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(userRepository.update(any(User.class))).thenReturn(null);

        var result = userManagementService.update(userUpdateValidatorDTO, resultError, password);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error when updating user");
    }

    //UPDATE PASSWORD USER//
    @Test
    public void shouldUpdateUserPasswordWithoutErrors(){
        UserPasswordChangeDTO userUpdateValidatorDTO =
                new UserPasswordChangeDTO("casa123456",
                        "newcasa123456", "5aabf615-0286-4962-b324-41b6f76014ae");

        var resultError = new BeanPropertyBindingResult(userUpdateValidatorDTO, "userUpdateValidatorDTO");

        when(userRepository.getByIdOnlyEmailOrCpfId(any())).thenReturn(new User());
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(bCryptPasswordEncoder.encodePassword(any())).thenReturn("$2g$s0$G75cOo8RwsVSMPlre9T.beJvqd74fS8sgjYtVFmFOswQ/WtMedQ/q");
        when(userRepository.update(any(User.class))).thenReturn(new User());

        var result = userManagementService.updateUserPassword(userUpdateValidatorDTO, resultError);

        assertTrue(result.IsSuccess);
    }

    @Test
    public void shouldUpdateUserPasswordWithErrorUserDTONull(){
        UserPasswordChangeDTO userUpdateValidatorDTO = null;

        var resultError = new BeanPropertyBindingResult(userUpdateValidatorDTO, "userUpdateValidatorDTO");

        var result = userManagementService.updateUserPassword(userUpdateValidatorDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "obj null");
    }

    @Test
    public void shouldUpdateUserPasswordWithErrorDTOValidate(){
        UserPasswordChangeDTO userUpdateValidatorDTO =
                new UserPasswordChangeDTO("casa123456",
                        "newcasa123456", "5aabf615-0286-4962-b324-41b6f76014ae");

        var resultError = new BeanPropertyBindingResult(userUpdateValidatorDTO, "userUpdateValidatorDTO");
        resultError.rejectValue("newPassword", "NotEmpty", "newPassword should not be empty");

        List<ErrorValidation> errors = new ArrayList<>();
//        var errorValidation = new ErrorValidation("newPassword", "newPassword should not be empty");
//        errors.add(errorValidation);

        when(validateErrorsDTO.ValidateDTO(anyList())).thenReturn(errors);

        var result = userManagementService.updateUserPassword(userUpdateValidatorDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error validate DTO");
//        assertEquals(result.Errors, errors);
    }

    @Test
    public void shouldUpdateUserPasswordWithErrorUserNotFound(){
        UserPasswordChangeDTO userUpdateValidatorDTO =
                new UserPasswordChangeDTO("casa123456",
                        "newcasa123456", "5aabf615-0286-4962-b324-41b6f76014ae");

        var resultError = new BeanPropertyBindingResult(userUpdateValidatorDTO, "userUpdateValidatorDTO");

        when(userRepository.getByIdOnlyEmailOrCpfId(any())).thenReturn(null);

        var result = userManagementService.updateUserPassword(userUpdateValidatorDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "user not found");
    }

    @Test
    public void shouldUpdateUserPasswordWithPasswordNotValid(){
        UserPasswordChangeDTO userUpdateValidatorDTO =
                new UserPasswordChangeDTO("casa123456",
                        "newcasa123456", "5aabf615-0286-4962-b324-41b6f76014ae");

        var resultError = new BeanPropertyBindingResult(userUpdateValidatorDTO, "userUpdateValidatorDTO");

        when(userRepository.getByIdOnlyEmailOrCpfId(any())).thenReturn(new User());
        when(authentication.isAuthenticated()).thenReturn(false);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        var result = userManagementService.updateUserPassword(userUpdateValidatorDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "password is not valid");
    }

    @Test
    public void shouldUpdateUserPasswordWithErrorWhenUpdatingUser(){
        UserPasswordChangeDTO userUpdateValidatorDTO =
                new UserPasswordChangeDTO("casa123456",
                        "newcasa123456", "5aabf615-0286-4962-b324-41b6f76014ae");

        var resultError = new BeanPropertyBindingResult(userUpdateValidatorDTO, "userUpdateValidatorDTO");

        when(userRepository.getByIdOnlyEmailOrCpfId(any())).thenReturn(new User());
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(bCryptPasswordEncoder.encodePassword(any())).thenReturn("$2g$s0$G75cOo8RwsVSMPlre9T.beJvqd74fS8sgjYtVFmFOswQ/WtMedQ/q");
        when(userRepository.update(any(User.class))).thenReturn(null);

        var result = userManagementService.updateUserPassword(userUpdateValidatorDTO, resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error when updating user");
    }
}
