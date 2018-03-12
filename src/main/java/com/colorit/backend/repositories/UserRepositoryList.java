package com.colorit.backend.repositories;

import com.colorit.backend.entities.db.GameResults;
import com.colorit.backend.entities.db.UserEntity;
import com.colorit.backend.entities.input.UserUpdateEntity;

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

    public void changeNickname(String nickname, String newNickname) throws ConstraintNameException,
            NoResultException {
        if (searchByNickname(newNickname) != null) {
            throw new ConstraintNameException();
        }
        UserEntity exisitingUser = getByNickame(nickname);
        exisitingUser.setNickname(newNickname);
    }

    public void update(String nickname, UserUpdateEntity userUpdateEntity) throws ConstraintNameException,
            ConstraintEmailException, NoResultException {
        UserEntity existingUser = getByNickame(nickname);
        if (userUpdateEntity.getNewNickname() != null) {
            if (searchByNickname(userUpdateEntity.getNewNickname()) != null) {
                throw new ConstraintNameException();
            }
        }

        if (userUpdateEntity.getNewEmail() != null) {
            if (searchByEmail(userUpdateEntity.getNewEmail()) != null) {
                throw new ConstraintEmailException();
            }
        }
        existingUser.copy(userUpdateEntity);
    }

    public Integer getCount() {
        return db.size();
    }

    public Long getPosition(String nickname) throws NoResultException {
        UserEntity cur = searchByNickname(nickname);
        return this.db.stream().filter(user -> cur.getRating() < user.getRating()).count();
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
        Integer countGames = rnd.nextInt(20);
        Integer countWins = rnd.nextInt(countGames);
        Integer maxRat = 40;
        Integer minRat = 20;
        Integer rating = -minRat + (rnd.nextInt(maxRat - (-minRat)));
        gameResults.setCountGames(countGames);
        gameResults.setCountWins(countWins);
        gameResults.setRating(rating);
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
