package com.colorit.backend.repositories;

import com.colorit.backend.entities.db.UserEntity;
import com.colorit.backend.entities.db.GameResults;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity getByNickname(String nickname);

    Boolean existsByNickname(String nickname);

    @Query("SELECT u  FROM UserEntity u join GameResults g on (u.gameResults = g) ORDER by g.rating DESC") //, LIMIT 1}, OFFSET :#{offset}")
    List<UserEntity> getUsers(Pageable page);

    public class OffsetLimitPageable extends PageRequest {
        private int offset;
        public OffsetLimitPageable(int offset, int limit){
            super(offset,limit);
            this.offset=offset;
        }
        @Override
        public long getOffset(){
            return this.offset;
        }
    }

    @Query("SELECT COUNT(u) FROM UserEntity u join GameResults g on (u.gameResults = g) " +
            "WHERE u.gameResults.rating > (SELECT u1.gameResults.rating " +
            "FROM UserEntity u1 join GameResults g on (u1.gameResults = g) WHERE u1.nickname = :#{#nickname})")
    Long getPosition(@Param("nickname") String nickname);


}