package com.colorit.backend.repositories;

import com.colorit.backend.entities.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryList {
    final List<UserEntity> db;

    public UserRepositoryList() {
        db = new ArrayList<>();
    }

    public UserEntity getByNickame(String nickname) throws NoResultException {
        final UserEntity userEntity = db.stream()
                .filter(user -> user.getNickname().equals(nickname))
                .findAny().orElse(null);
        if (userEntity == null) {
            throw new NoResultException();
        }
        return userEntity;
    }

    public UserEntity getByEmail(String email) {
        return db.stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny().orElse(null);
    }

    public void save(UserEntity userEntity) throws ConstraintNameException, ConstraintEmailException {
        try {
            //noinspection unused
            final UserEntity existringUser = getByNickame(userEntity.getNickname());
            throw new ConstraintNameException();
        } catch (NoResultException ex) {
            final UserEntity existringUser = getByEmail(userEntity.getEmail());
            if (existringUser != null) {
                throw new ConstraintEmailException();
            }
            db.add(userEntity);
        }
    }

    public void changePassword(String nickname, String password) throws NoResultException {
        final UserEntity existringUser = getByNickame(nickname);
        existringUser.setPasswordHash(password);
    }

    public void changeEmail(String nickname, String email) throws NoResultException, ConstraintEmailException {
        final UserEntity existringUser = getByNickame(nickname);
        final UserEntity checkMailEntity = getByEmail(email);
        if (checkMailEntity != null) {
            throw new ConstraintEmailException();
        }
        existringUser.setEmail(email);
    }

    public static class RepositoryException extends Exception {
    }

    public static class ConstraintNameException extends RepositoryException {
    }

    public static class ConstraintEmailException extends RepositoryException {
    }

    public static class NoResultException extends RepositoryException {
    }
}
