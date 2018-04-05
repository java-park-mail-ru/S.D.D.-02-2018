package com.colorit.backend.repositories;

import com.colorit.backend.entities.db.GameResults;
import com.colorit.backend.entities.db.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
@Transactional
public class UserRepositoryJpa {
    private final EntityManager entityManager;

    public UserRepositoryJpa(@NotNull EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Long count() {
        return entityManager.createQuery("select count(u) from UserEntity u", Long.class).getSingleResult();
    }

    public UserEntity getByNickname(String nickname) {

        return entityManager.createQuery(
                "select u from UserEntity u join GameResults g on (u.gameResults = g) where u.nickname=:nickname",
                UserEntity.class).setParameter("nickname", nickname).getSingleResult();
    }

    public boolean existsByNickname(String nickname) {
        return (entityManager.createQuery("select count(u) from UserEntity u where u.nickname=:nickname",
                Long.class)
                .setParameter("nickname", nickname).getSingleResult()) > 0;
    }

    public void update(UserEntity updateEntity) {
        entityManager.merge(updateEntity);
        entityManager.flush();
    }

    public List<UserEntity> getUsers(Integer limit, Integer offset) {
        return entityManager.createQuery(
                "SELECT u  FROM UserEntity u join GameResults g on (u.gameResults = g) ORDER by g.rating DESC",
                UserEntity.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public Integer getPosition(String nickname) {
        return entityManager.createQuery(
                "SELECT COUNT(u) FROM UserEntity u join GameResults g on (u.gameResults = g) "
                        + "WHERE u.gameResults.rating > (SELECT u1.gameResults.rating "
                        + "FROM UserEntity u1 join GameResults g on (u1.gameResults = g) "
                        + "WHERE u1.nickname = :nickname)", Integer.class)
                .setParameter("nickname", nickname)
                .getSingleResult();
    }

    public void save(UserEntity userEntity) {
        GameResults gameResults = new GameResults();
        entityManager.persist(userEntity);
        entityManager.persist(gameResults);
        userEntity.setGameResults(gameResults);
        entityManager.merge(userEntity);
        entityManager.flush();
    }
}
