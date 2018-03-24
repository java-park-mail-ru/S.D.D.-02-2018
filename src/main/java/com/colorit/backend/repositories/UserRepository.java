package com.colorit.backend.repositories;

import com.colorit.backend.entities.db.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity getByNickname(String nickname);

    Boolean existsByNickname(String nickname);

    @Override
    Page<UserEntity> findAll(Pageable pageable);

    List<UserEntity> updateByNickname
    List<UserEntity> findAllOrderByRatingWithLimitOffset(Integer limi);


}