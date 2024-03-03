package com.green.greengram4.feed;

import com.green.greengram4.entity.FeedFavEntity;
import com.green.greengram4.entity.FeedFavIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedFavRepository extends JpaRepository<FeedFavEntity, FeedFavIdentity> {

    long countByFeedFavIdentity(FeedFavIdentity feedFavIdentity);

    List<FeedFavEntity> findByFeedFavIdentity(FeedFavIdentity feedFavEntity);

    @Query("select ff.feedFavIdentity.ifeed from FeedFavEntity ff where ff.feedFavIdentity.iuser = :userEntity")
    List<Long> findIfeedByIuser(Long userEntity);
}
