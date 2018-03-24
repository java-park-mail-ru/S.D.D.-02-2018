package com.colorit.backend.services;


import com.colorit.backend.entities.db.UserEntity;
import com.colorit.backend.entities.input.UserUpdateEntity;
import com.colorit.backend.repositories.GameRepository;
import com.colorit.backend.repositories.UserRepository;
import com.colorit.backend.services.responses.UserServiceResponse;
import com.colorit.backend.services.statuses.UserServiceStatus;
import com.colorit.backend.views.entity.representations.UserEntityRepresentation;
import com.colorit.backend.views.input.SignInView;
import com.colorit.backend.views.input.SignUpView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.sql.SQLException;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private static final String passwordSault = "sault";

    @Autowired
    public UserService(@NotNull UserRepository repository, @NotNull GameRepository gameRepository) {
        this.userRepository = repository;
        this.gameRepository = gameRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private boolean checkPasswords(String internalPassword, String externalPassword) {
        String saultedPassword = passwordSault + externalPassword + passwordSault;
        if (!passwordEncoder().matches(saultedPassword, internalPassword)) {
            return false;
        }
        return true;
    }

    @Override
    public UserServiceResponse getUser(String nickname) {
        //UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatus.OK_STATE);
        // TODO aspect, checks dataaceess exception
        //try {
            UserEntity userEntity = userRepository.getByNickname(nickname);
            if (userEntity != null) {
                return new UserServiceResponse<>(UserServiceStatus.OK_STATE, userEntity);
            } else {
                return new UserServiceResponse(UserServiceStatus.NAME_MATCH_ERROR_STATE);
            }
       // } catch (DataAccessException dAEx) {
       //     userServiceResponse.setStatusCode(UserServiceStatusCode.DB_ERROR_STATE);
       // }
        //return userServiceResponse;
    }

    @Override
    public UserServiceResponse authenticateUser(SignInView userEntity) {
        //UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatusCode.OK_STATE);
        //try {
        final UserEntity existingUserEntity = userRepository.getByNickname(userEntity.getNickname());
        if (existingUserEntity != null) {
            if (!checkPasswords(existingUserEntity.getPasswordHash(), userEntity.getPassword())) {
                return new UserServiceResponse(UserServiceStatus.PASSWORD_MATCH_ERROR_STATE);
            }
            return new UserServiceResponse(UserServiceStatus.OK_STATE);
        } else {
            return new UserServiceResponse(UserServiceStatus.NAME_MATCH_ERROR_STATE);
        }
//        } catch (DataAccessException dAEx) {
//            userServiceResponse.setStatusCode(UserServiceStatusCode.DB_ERROR_STATE);
//        }
//        return userServiceResponse;
    }
    @Override
    public UserServiceResponse userExists(String nickname) {
        //final UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatusCode.OK_STATE);
        //try {
        if (!userRepository.existsByNickname(nickname)) {
            return new UserServiceResponse(UserServiceStatus.NAME_MATCH_ERROR_STATE);
            //userServiceResponse.setStatusCode(UserServiceStatusCode.NAME_MATCH_ERROR_STATE);
        }
        return new UserServiceResponse(UserServiceStatus.OK_STATE);
       // } catch (DataAccessException daEx) {
       //     LOGGER.error("Error DataBase", daEx);
       //     userServiceResponse.setStatusCode(UserServiceStatusCode.DB_ERROR_STATE);
       // }
        //return userServiceResponse;
    }

    @Override
    public UserServiceResponse createUser(SignUpView signUpView) {
//        try {
            // its password, because no ope
            UserEntity userEntity = UserEntity.fromView(signUpView);
            String userPassword = userEntity.getPasswordHash();
            userEntity.setPasswordHash(passwordEncoder().encode(passwordSault + userPassword + passwordSault));
            this.userRepository.save(userEntity);
            return new UserServiceResponse(UserServiceStatus.CREATED_STATE);
//       // } catch (DataIntegrityViolationException dIVEx) {
//        SQLException sqlEx = (SQLException)dIVEx.getCause().getCause();
//        LOGGER.error("Constraint error: {}", dIVEx.getLocalizedMessage());
//        if (sqlEx.getLocalizedMessage().contains("nickname_constraint")) {
         //   ConstraintViolationException cEx = (ConstraintViolationException)dIVEx.getCause();
//            if (cEx.getConstraintName().equals("nickname_constraint")) {
//                userServiceResponse.setStatusCode(UserServiceStatusCode.CONFLICT_NAME_STATE);
//            } else {
//                userServiceResponse.setStatusCode(UserServiceStatusCode.CONFLICT_EMAIL_STATE);
//            }
//        } catch (DataAccessException dAEx) {
//            userServiceResponse.setStatusCode(UserServiceStatusCode.DB_ERROR_STATE);
//        }
//        return userServiceResponse;
    }

    public UserServiceResponse updateEmail(String nickname, String email) {
        UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatusCode.OK_STATE);
        try {
            UserEntity existingEntity = userRepository.getByNickname(nickname);
            if (existingEntity != null) {
                existingEntity.setEmail(email);
                userRepository.save(existingEntity);
            } else {
                userServiceResponse.setStatusCode(UserServiceStatusCode.NAME_MATCH_ERROR_STATE);
            }
        } catch (DataIntegrityViolationException dIVex) {
            SQLException sqlEx = (SQLException)dIVex.getCause().getCause();
            if (sqlEx.getLocalizedMessage().contains("nickname_constraint")) {
                userServiceResponse.setStatusCode(UserServiceStatusCode.CONFLICT_NAME_STATE);
            } else {
                userServiceResponse.setStatusCode(UserServiceStatusCode.CONFLICT_EMAIL_STATE);
            }
            LOGGER.error("Constraint error: {}", sqlEx.getLocalizedMessage());
        } catch (DataAccessException dAEx) {

            userServiceResponse.setStatusCode(UserServiceStatusCode.DB_ERROR_STATE);
            LOGGER.error("Error DataBase {}", dAEx.getLocalizedMessage());
        }
            return userServiceResponse;
    }


    @Override
    public UserServiceResponse updatePassword(String nickname, String oldPassword, String newPassword) {
        final UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatusCode.OK_STATE);
        try {
            UserEntity existingEntity = userRepository.getByNickname(nickname);
            final UserEntity existingEntity = userRepository.getByNickname(nickname);
            if (existingEntity != null) {
                existingEntity.setEmail(email);
                userRepository.save(existingEntity);
                if (checkPasswords(existingEntity.getPasswordHash(), oldPassword)) {
                    existingEntity.setPasswordHash(
                            passwordEncoder().encode(PASSWORD_SAULT + newPassword + PASSWORD_SAULT)
                    );
                    userRepository.save(existingEntity);
                    LOGGER.info("User with nickname: {} changed password");
                } else {
                    userServiceResponse.setStatusCode(UserServiceStatusCode.PASSWORD_MATCH_ERROR_STATE);
                    LOGGER.info("User with nickname: {} input incorrect oldPassword");
                }
            } else {
                userServiceResponse.setStatusCode(UserServiceStatusCode.NAME_MATCH_ERROR_STATE);
            }
    }

    @Override
    public UserServiceResponse updateNickname(String nickname, String newNickname) {
        return null;
    }

    @Override
    public UserServiceResponse updateAvatar(String nickname, String avatar) {

            //return null;
    }

    @Override
    public UserServiceResponse update(String nickname, UserUpdateEntity updateEntity) {
        return null;
    }



    @Override
    public UserServiceResponse getUsers(Integer limit, Integer offset) {
//        UserServiceResponse<UserEntityRepresentation> userServiceResponse =
//                new UserServiceResponse<>(UserServiceStatus.OK_STATE);
//        try {
//            List<UserEntity> userEntityList = userRepository.find
//        } catch (DataAccessException dAEx) {
//
//        }
        return null;
    }

    @Override
    public UserServiceResponse getUsersCount() {
        return null;
    }

    @Override
    public UserServiceResponse getPosition(String nickname) {
        return null;
    }
}