package com.color_it.backend.services;

import com.color_it.backend.entities.UserEntity;
import com.color_it.backend.repositories.UserRepository;
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
    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private static final String PASSWORD_SAULT = "sault";

    @Autowired
    public UserService(final UserRepository repository) {
        this.userRepository = repository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private boolean checkPasswords(String internalPassword, String externalPassword) {
        final String saultedPassword = PASSWORD_SAULT + externalPassword + PASSWORD_SAULT;
        return passwordEncoder().matches(saultedPassword, internalPassword);
    }

    public UserServiceResponse getUser(String nickname) {
        final UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatusCode.OK_STATE);
        try {
            final UserEntity userEntity = userRepository.getByNickname(nickname);
            if (userEntity != null) {
                userServiceResponse.setEntity(userEntity);
                LOGGER.info("User with nickname: {} found");
            } else {
                userServiceResponse.setStatusCode(UserServiceStatusCode.NAME_MATCH_ERROR_STATE);
                LOGGER.error("User with nickname: {} not found");

            }
        } catch (DataAccessException dAEx) {
            userServiceResponse.setStatusCode(UserServiceStatusCode.DB_ERROR_STATE);
            LOGGER.error("Error database: {}", dAEx.getLocalizedMessage());
        }
        return userServiceResponse;
    }

    public UserServiceResponse updateEmail(String nickname, String email) {
        final UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatusCode.OK_STATE);
        try {
            final UserEntity existingEntity = userRepository.getByNickname(nickname);
            if (existingEntity != null) {
                existingEntity.setEmail(email);
                userRepository.save(existingEntity);
            } else {
                userServiceResponse.setStatusCode(UserServiceStatusCode.NAME_MATCH_ERROR_STATE);
            }
        } catch (DataIntegrityViolationException dIVex) {
            final SQLException sqlEx = (SQLException) dIVex.getCause().getCause();
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

    public UserServiceResponse updatePassword(String nickname, String oldPassword, String newPassword) {
        final UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatusCode.OK_STATE);
        try {
            final UserEntity existingEntity = userRepository.getByNickname(nickname);
            if (existingEntity != null) {
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
        } catch (DataAccessException dAEx) {
            userServiceResponse.setStatusCode(UserServiceStatusCode.DB_ERROR_STATE);
            LOGGER.error("Error DataBase {}", dAEx.getLocalizedMessage());
        }
        return userServiceResponse;
    }

    public UserServiceResponse authenticateUser(UserEntity userEntity) {
        final UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatusCode.OK_STATE);
        try {
            final UserEntity existingUserEntity = userRepository.getByNickname(userEntity.getNickname());
            if (existingUserEntity != null) {
                if (!checkPasswords(existingUserEntity.getPasswordHash(), userEntity.getPasswordHash())) {
                    userServiceResponse.setStatusCode(UserServiceStatusCode.PASSWORD_MATCH_ERROR_STATE);
                    LOGGER.error("User passwords not math nickname: {}", userEntity.getNickname());
                } else {
                    LOGGER.info("User with nickname: {} signin", userEntity.getNickname());
                }
            } else {
                userServiceResponse.setStatusCode(UserServiceStatusCode.NAME_MATCH_ERROR_STATE);
                LOGGER.error("User with nickname: {} not found");
            }
        } catch (DataAccessException dAEx) {
            userServiceResponse.setStatusCode(UserServiceStatusCode.DB_ERROR_STATE);
            LOGGER.error("Error DataBase {}", dAEx.getLocalizedMessage());
        }
        return userServiceResponse;
    }

    public UserServiceResponse createUser(UserEntity userEntity) {
        final UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatusCode.CREATED_STATE);
        try {
            // its password
            final String userPassword = userEntity.getPasswordHash();
            userEntity.setPasswordHash(passwordEncoder().encode(PASSWORD_SAULT + userPassword + PASSWORD_SAULT));
            this.userRepository.save(userEntity);
            LOGGER.info("User with nickname: {} created", userEntity.getNickname());
        } catch (DataIntegrityViolationException dIVEx) {
            final SQLException sqlEx = (SQLException) dIVEx.getCause().getCause();
            if (sqlEx.getLocalizedMessage().contains("nickname_constraint")) {
                userServiceResponse.setStatusCode(UserServiceStatusCode.CONFLICT_NAME_STATE);
            } else {
                userServiceResponse.setStatusCode(UserServiceStatusCode.CONFLICT_EMAIL_STATE);
            }
            LOGGER.error("Constraint error: {}", sqlEx.getLocalizedMessage());
        } catch (DataAccessException dAEx) {
            LOGGER.error("Error DataBase {}", dAEx.getLocalizedMessage());
            userServiceResponse.setStatusCode(UserServiceStatusCode.DB_ERROR_STATE);
        }
        return userServiceResponse;
    }

    @SuppressWarnings("unused")
    public UserServiceResponse userExists(String nickname) {
        final UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatusCode.OK_STATE);
        try {
            if (!userRepository.existsByNickname(nickname)) {
                userServiceResponse.setStatusCode(UserServiceStatusCode.NAME_MATCH_ERROR_STATE);
                LOGGER.error("user with nickname: {} not found", nickname);
            }
        } catch (DataAccessException dAEx) {
            LOGGER.error("Error DataBase {}", dAEx.getLocalizedMessage());
            userServiceResponse.setStatusCode(UserServiceStatusCode.DB_ERROR_STATE);
        }
        return userServiceResponse;
    }

    // think how to do it
    public UserServiceResponse getRating(String nickname) {
        final UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatusCode.OK_STATE);

        return userServiceResponse;
    }
}
