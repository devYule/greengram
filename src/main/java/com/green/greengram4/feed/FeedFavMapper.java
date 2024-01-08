package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedFavDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedFavMapper {
    int insertFeedFav(FeedFavDto feedFavDto);
    int deleteFeedFav(FeedFavDto feedFavDto);


    /**
     * for test
     */
    int selFeedFav(FeedFavDto feedFavDto);
    FeedFavDto selFeedFavV2(FeedFavDto feedFavDto);

    int delFeedFavAll(int ifeed);

}
