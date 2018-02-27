package com.color_it.backend.services;

import com.color_it.backend.entities.UserEntity;
import com.color_it.backend.repositories.Repository;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserService {
    private final Repository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private static final String passwordSault = "sault";

    @Autowired
    public UserService(final Repository repository) {
        this.userRepository = repository;
    }

    public UserServiceResponse getUser(String nickname) {
        UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatusCode.OK_STATE);
        try {
            UserEntity userEntity = userRepository.getByNickname(nickname);
            if (userEntity != null) {
                userServiceResponse.setEntity(userEntity);
            } else {
                userServiceResponse.setStatusCode(UserServiceStatusCode.NAME_MATCH_ERROR_STATE);
            }
        } catch (DataAccessException dAEx) {
            userServiceResponse.setStatusCode(UserServiceStatusCode.DB_ERROR_STATE);
        }
        return userServiceResponse;
    }

    @Autowired
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UserServiceResponse getUsers(Integer limit, Integer offset) {
        return null;
    }

    private boolean checkPasswords(String internalPassword, String externalPassword) {
        String saultedPassword = passwordSault + externalPassword + passwordSault;
        if (!passwordEncoder().matches(internalPassword, saultedPassword)) {
            return false;
        }
        return true;
    }

    // maybe entity
    public UserServiceResponse authenticateUser(UserEntity userEntity) {
        UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatusCode.OK_STATE);
        try {
            final UserEntity existingUserEntity = userRepository.getByNickname(userEntity.getNickname());
            if (existingUserEntity != null) {
                if (!checkPasswords(existingUserEntity.getPasswordHash(), userEntity.getPasswordHash())) {
                    userServiceResponse.setStatusCode(UserServiceStatusCode.PASSWORD_MATCH_ERROR_STATE);
                }
            } else {
                userServiceResponse.setStatusCode(UserServiceStatusCode.NAME_MATCH_ERROR_STATE);
            }
        } catch (DataAccessException dAEx) {
            userServiceResponse.setStatusCode(UserServiceStatusCode.DB_ERROR_STATE);
        }
        return userServiceResponse;
    }

    public UserServiceResponse createUser(UserEntity userEntity) {
        final UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatusCode.CREATED_STATE);
        try {
            // its password, because no ope
            String userPassword = userEntity.getPasswordHash();
            userEntity.setPasswordHash(passwordEncoder().encode(passwordSault + userPassword + passwordSault));
            this.userRepository.save(userEntity);
            LOGGER.info("user created {}", userEntity.getNickname());
        } catch (DataIntegrityViolationException dIVEx) {
            SQLException sqlEx = (SQLException)dIVEx.getCause().getCause();
            LOGGER.error("Constraint error: {}", dIVEx.getLocalizedMessage());
            if (sqlEx.getLocalizedMessage().contains("nickname_constraint")) {
                userServiceResponse.setStatusCode(UserServiceStatusCode.CONFLICT_NAME_STATE);
            } else {
                userServiceResponse.setStatusCode(UserServiceStatusCode.CONFLICT_EMAIL_STATE);
            }
            LOGGER.error("Constraint error: {}", sqlEx.getLocalizedMessage());
        } catch (DataAccessException dAEx) {
            LOGGER.error("Error DataBase", dAEx);
            userServiceResponse.setStatusCode(UserServiceStatusCode.DB_ERROR_STATE);
        }
        return userServiceResponse;
    }

    public UserServiceResponse updateEmail(String newEmail) {
        return null;
    }

    public UserServiceResponse updatePassword(String oldPassword, String newPassword) {
        return null;
    }


    public UserServiceResponse userExists(String nickname) {
        final UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatusCode.OK_STATE);
        try {
            if (!userRepository.existsByNickname(nickname)) {
                userServiceResponse.setStatusCode(UserServiceStatusCode.NAME_MATCH_ERROR_STATE);
                LOGGER.error("user not found {}", nickname);
            }
        } catch (DataAccessException dAEx) {
            LOGGER.error("Error DataBase", dAEx);
            userServiceResponse.setStatusCode(UserServiceStatusCode.DB_ERROR_STATE);
        }
        return userServiceResponse;
    }

//    public UserServiceResponse
}
