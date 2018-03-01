package com.colorit.backend.repositories;

import com.colorit.backend.entities.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryList {
    private final List<UserEntity> db;

    public UserRepositoryList() {
        db = new ArrayList<>();
    }

    public UserEntity getByNickame(String nickname) throws NoResultException {
        final UserEntity userEntity = searchByNickname(nickname);
        if (userEntity == null) {
            throw new NoResultException();
        }
        return userEntity;
    }

    public void save(UserEntity userEntity) throws ConstraintNameException, ConstraintEmailException {
        UserEntity existingUser = searchByNickname(userEntity.getNickname());
        if(existingUser != null) {
            throw new ConstraintNameException();
        }

        existingUser = searchByEmail(userEntity.getEmail());
        if (existingUser != null) {
            throw new ConstraintEmailException();
        }
        db.add(userEntity);
    }

    public void changePassword(String nickname, String password) throws NoResultException {
        final UserEntity existringUser = getByNickame(nickname);
        existringUser.setPasswordHash(password);
    }

    public void changeEmail(String nickname, String email) throws NoResultException, ConstraintEmailException {
        final UserEntity existringUser = getByNickame(nickname);
        final UserEntity checkMailEntity = searchByEmail(email);
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

    private UserEntity searchByNickname(String nickname) {
        return db.stream()
                .filter(user -> user.getNickname().equals(nickname))
                .findAny().orElse(null);
    }

    private UserEntity searchByEmail(String email) {
        return db.stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny().orElse(null);
    }
}
