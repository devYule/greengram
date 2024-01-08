package com.green.greengram4.feed.feedcomment;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.feedcomment.model.FeedCommentInsDto;
import com.green.greengram4.feed.feedcomment.model.FeedCommentSelDto;
import com.green.greengram4.feed.feedcomment.model.FeedCommentSelVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedCommentService {
    private final FeedCommentMapper commentMapper;

    public ResVo insFeedComment(FeedCommentInsDto feedCommentInsDto) {
        ResVo result = new ResVo();
        if (commentMapper.insFeedComment(feedCommentInsDto) == 0) return result;
        result.setResult(feedCommentInsDto.getIfeedComment());
        return result;
    }

    public List<FeedCommentSelVo> getFeedCommentAll(FeedCommentSelDto feedCommentSelDto) {
        return commentMapper.selFeedCommentAll(feedCommentSelDto);
    }


}
