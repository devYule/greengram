package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedDelDto;
import com.green.greengram4.feed.model.FeedSelResultVo;
import com.green.greengram4.feed.model.FeedSelectDto;
import com.green.greengram4.feed.model.InsertFeedDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedMapper {

    int insertFeed(InsertFeedDto insertFeedDto);




    List<FeedSelResultVo> getFeeds(FeedSelectDto feedSelectDto);



    int isExists(FeedDelDto feedDelDto);

    void delExtra(FeedDelDto feedDelDto);

    int delFeed(FeedDelDto feedDelDto);



}

