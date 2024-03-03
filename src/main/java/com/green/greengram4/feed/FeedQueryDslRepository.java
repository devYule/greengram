package com.green.greengram4.feed;


import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.entity.FeedFavEntity;
import com.green.greengram4.entity.FeedPicsEntity;
import com.green.greengram4.feed.model.FeedSelectDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeedQueryDslRepository {
    List<FeedEntity> selFeedAll(FeedSelectDto feedSelectDto, Pageable pageable);

    List<FeedPicsEntity> selFeedPicsAll(List<FeedEntity> feedEntities);

    List<FeedFavEntity> findIfeedByIuser(List<FeedEntity> feedEntity, Long userEntity);
}
