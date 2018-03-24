package com.colorit.backend.repositories;

import com.colorit.backend.entities.db.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity getByNickname(String nickname);

    Boolean existsByNickname(String nickname);


//    List<UserEntity> updateByNickname
    //List<UserEntity> findAllOrderByRatingWithLimitOffset(Integer limi);
//

}