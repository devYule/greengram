package com.green.greengram4.feed;

import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedRepository extends JpaRepository<FeedEntity, Long>, FeedQueryDslRepository {
    @EntityGraph(attributePaths = "userEntity")
    List<FeedEntity> findAllByUserEntityOrderByIfeedDesc(UserEntity userEntity, Pageable pageable);
}
