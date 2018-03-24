package com.colorit.backend.repositories;

import com.colorit.backend.entities.db.UserEntity;
import com.colorit.backend.entities.db.GameResults;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity getByNickname(String nickname);

    Boolean existsByNickname(String nickname);


//    List<UserEntity> updateByNickname
   // @Query("SELECT * FROM user_entity u join game_result g on (u.game_results_id = g.id) ORDER by g.rating DESC, LIMIT :?{1}, OFFSET :#{offset}")
    @Query("SELECT u  FROM UserEntity u join GameResults g on (u.gameResults = g) ORDER by g.rating DESC") //, LIMIT 1}, OFFSET :#{offset}")
    List<UserEntity> getUsers();//@Param("limit") Integer limit, Integer offset);

//    List<UserEntity> findAllOrderByGameResults_Rating();

}