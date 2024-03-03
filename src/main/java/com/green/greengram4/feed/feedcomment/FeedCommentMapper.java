package com.green.greengram4.feed.feedcomment;

import com.green.greengram4.feed.feedcomment.model.FeedCommentInsDto;
import com.green.greengram4.feed.feedcomment.model.FeedCommentSelDto;
import com.green.greengram4.feed.feedcomment.model.FeedCommentSelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedCommentMapper {
    int insFeedComment(FeedCommentInsDto feedCommentInsDto);

    List<FeedCommentSelVo> selFeedCommentAll(FeedCommentSelDto feedCommentSelDto);

    List<FeedCommentSelVo> findByIfeeds(List<Long> ifeeds);
}
