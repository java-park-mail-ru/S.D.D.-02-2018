package com.colorit.backend.repositories;

import com.colorit.backend.entities.GameResults;
import com.colorit.backend.entities.UserEntity;

import java.util.*;
import java.util.stream.Collectors;

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

    public Integer getCount() {
        return db.size();
    }


    public Long getPosition(String nickname) throws NoResultException {
        UserEntity cur = searchByNickname(nickname);
        return this.db.stream().filter(user -> cur.getRating() >
                user.getRating()).count();
    }

    public List<UserEntity> getListUsers(Integer limit, Integer offset) {
        return db.stream()
                .sorted(Comparator.comparingDouble(UserEntity::getRating).reversed())
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
    }

    public void save(UserEntity userEntity) throws ConstraintNameException, ConstraintEmailException {
        UserEntity existingUser = searchByNickname(userEntity.getNickname());
        if (existingUser != null) {
            throw new ConstraintNameException();
        }

        existingUser = searchByEmail(userEntity.getEmail());
        if (existingUser != null) {
            throw new ConstraintEmailException();
        }
        final GameResults gameResults = new GameResults();
        Random rnd = new Random();
        gameResults.setCountGames(rnd.nextInt(100));
        gameResults.setCountWins(rnd.nextInt(20));
        userEntity.setGameResults(gameResults);
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

    public static class ConstraintNameException extends Exception {
    }

    public static class ConstraintEmailException extends Exception {
    }

    public static class NoResultException extends Exception {
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
