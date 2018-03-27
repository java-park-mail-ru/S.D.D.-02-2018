package com.colorit.backend.services;

import com.colorit.backend.entities.db.GameResults;
import com.colorit.backend.entities.db.UserEntity;
import com.colorit.backend.entities.input.UserUpdateEntity;
import com.colorit.backend.repositories.GameRepository;
import com.colorit.backend.repositories.UserRepository;
import com.colorit.backend.services.responses.UserServiceResponse;
import com.colorit.backend.services.statuses.UserServiceStatus;
import com.colorit.backend.views.entity.representations.UserListEntityRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Primary
@Service
public class UserServiceJpa implements IUserService {
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceJpa.class);
    private static final String PASSWORD_SAULT = "sault";

    @Autowired
    public UserServiceJpa(@NotNull UserRepository repository, @NotNull GameRepository gameRepository) {
        this.userRepository = repository;
        this.gameRepository = gameRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private boolean checkPasswords(String internalPassword, String externalPassword) {
        final String saultedPassword = PASSWORD_SAULT + externalPassword + PASSWORD_SAULT;
        if (!passwordEncoder().matches(saultedPassword, internalPassword)) {
            return false;
        }
        return true;
    }

    @Override
    public UserServiceResponse getUser(String nickname) {
        final UserEntity userEntity = userRepository.getByNickname(nickname);
        if (userEntity != null) {
            LOGGER.info("info returned about user {}", nickname);
            return new UserServiceResponse<>(UserServiceStatus.OK_STATE, userEntity.toRepresentation());
        } else {
            LOGGER.info("no user found user {}", nickname);
            return new UserServiceResponse(UserServiceStatus.NOT_FOUND_STATE);
        }
    }

    @Override
    public UserServiceResponse authenticateUser(UserEntity userEntity) {
        final UserEntity existingUserEntity = userRepository.getByNickname(userEntity.getNickname());
        if (existingUserEntity == null) {
            LOGGER.info("cant authenticate user {}", userEntity.getNickname());
            return new UserServiceResponse(UserServiceStatus.NAME_MATCH_ERROR_STATE);
        }

        if (!checkPasswords(existingUserEntity.getPasswordHash(), userEntity.getPasswordHash())) {
            LOGGER.info("cant authenticate user {}", userEntity.getNickname());
            return new UserServiceResponse(UserServiceStatus.PASSWORD_MATCH_ERROR_STATE);
        }

        LOGGER.info("authenticated user {}", userEntity.getNickname());
        return new UserServiceResponse(UserServiceStatus.OK_STATE);
    }

    @Override
    public UserServiceResponse userExists(String nickname) {
        if (!userRepository.existsByNickname(nickname)) {
            LOGGER.info("no such user {}", nickname);
            return new UserServiceResponse(UserServiceStatus.NAME_MATCH_ERROR_STATE);
        }
        LOGGER.info("user found {}", nickname);
        return new UserServiceResponse(UserServiceStatus.OK_STATE);
    }

    @Override
    public UserServiceResponse createUser(UserEntity userEntity) {
        final GameResults gameResults = new GameResults();
        try {
            final String userPassword = userEntity.getPasswordHash();
            userEntity.setPasswordHash(passwordEncoder().encode(PASSWORD_SAULT + userPassword + PASSWORD_SAULT));

            this.gameRepository.save(gameResults);
            userEntity.setGameResults(gameResults);
            this.userRepository.save(userEntity);

            LOGGER.info("user created", userEntity.getNickname());
        } catch (DataIntegrityViolationException dIVEx) {
            this.gameRepository.delete(gameResults);
            throw dIVEx;
        }
        return new UserServiceResponse(UserServiceStatus.CREATED_STATE);
    }

    @Override
    public UserServiceResponse updateEmail(String nickname, String email) {
        final UserEntity existingEntity = userRepository.getByNickname(nickname);
        if (existingEntity != null) {
            existingEntity.setEmail(email);
            userRepository.saveAndFlush(existingEntity);
        } else {
            return new UserServiceResponse(UserServiceStatus.NAME_MATCH_ERROR_STATE);
        }
        return new UserServiceResponse(UserServiceStatus.OK_STATE);
    }

    @Override
    public UserServiceResponse updatePassword(String nickname, String oldPassword, String newPassword) {
        final UserEntity existingEntity = userRepository.getByNickname(nickname);
        if (existingEntity == null) {
            return new UserServiceResponse(UserServiceStatus.NAME_MATCH_ERROR_STATE);
        }

        if (!checkPasswords(existingEntity.getPasswordHash(), oldPassword)) {
            return new UserServiceResponse(UserServiceStatus.PASSWORD_MATCH_ERROR_STATE);
        }

        existingEntity.setPasswordHash(passwordEncoder().encode(PASSWORD_SAULT + newPassword + PASSWORD_SAULT));
        userRepository.save(existingEntity);
        return new UserServiceResponse(UserServiceStatus.OK_STATE);
    }

    @Override
    public UserServiceResponse updateNickname(String nickname, String newNickname) {
        final UserEntity existingEntity = userRepository.getByNickname(nickname);
        if (existingEntity == null) {
            return new UserServiceResponse(UserServiceStatus.NAME_MATCH_ERROR_STATE);
        }

        existingEntity.setNickname(newNickname);
        userRepository.saveAndFlush(existingEntity);
        return new UserServiceResponse(UserServiceStatus.OK_STATE);
    }

    @Override
    public UserServiceResponse updateAvatar(String nickname, String avatar) {
        final UserEntity existingEntity = userRepository.getByNickname(nickname);
        if (existingEntity == null) {
            return new UserServiceResponse(UserServiceStatus.NAME_MATCH_ERROR_STATE);
        }

        existingEntity.setAvatarPath(avatar);
        userRepository.save(existingEntity);
        return new UserServiceResponse(UserServiceStatus.OK_STATE);
    }

    @Override
    public UserServiceResponse update(String nickname, UserUpdateEntity updateEntity) {
        final UserEntity existingEntity = userRepository.getByNickname(nickname);
        if (existingEntity == null) {
            return new UserServiceResponse(UserServiceStatus.NAME_MATCH_ERROR_STATE);
        }

        if (updateEntity.getOldPassword() != null) {
            if (!checkPasswords(existingEntity.getPasswordHash(), updateEntity.getOldPassword())) {
                return new UserServiceResponse(UserServiceStatus.PASSWORD_MATCH_ERROR_STATE);
            } else {
                updateEntity.setNewPassword(passwordEncoder().encode(
                        PASSWORD_SAULT + updateEntity.getNewPassword() + PASSWORD_SAULT));
            }
        }

        existingEntity.copy(updateEntity);
        userRepository.saveAndFlush(existingEntity);
        return new UserServiceResponse(UserServiceStatus.OK_STATE);
    }

    @Override
    public UserServiceResponse getUsers(Integer limit, Integer offset) {
        return new UserServiceResponse<>(UserServiceStatus.OK_STATE,
                new UserListEntityRepresentation(userRepository.getUsers(
                        new UserRepository.OffsetLimitPageable(offset, limit))));
    }

    @Override
    public UserServiceResponse getUsersCount() {
        return new UserServiceResponse<>(UserServiceStatus.OK_STATE, userRepository.count());
    }

    @Override
    public UserServiceResponse getPosition(String nickname) {
        final UserEntity existingEntity = userRepository.getByNickname(nickname);
        if (existingEntity == null) {
            LOGGER.info("NO such user {}", nickname);
            return new UserServiceResponse(UserServiceStatus.NAME_MATCH_ERROR_STATE);
        }
        return new UserServiceResponse<>(UserServiceStatus.OK_STATE, userRepository.getPosition("bbb"));
    }
}