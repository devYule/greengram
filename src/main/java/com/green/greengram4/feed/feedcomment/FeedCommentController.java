package com.green.greengram4.feed.feedcomment;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.feedcomment.model.FeedCommentInsDto;
import com.green.greengram4.feed.feedcomment.model.FeedCommentSelDto;
import com.green.greengram4.feed.feedcomment.model.FeedCommentSelVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed/comment")
public class FeedCommentController {
    private final FeedCommentService service;


    @PostMapping
    public ResVo insFeedComment(@RequestBody @Validated FeedCommentInsDto feedCommentInsDto) {
//        if (feedCommentInsDto.getIfeed() == 0 || feedCommentInsDto.getComment().isEmpty()) {
//        if (feedCommentInsDto.getIfeed() == 0 || "".equals(feedCommentInsDto.getComment())) {
//            throw new RestApiException(FeedErrorCode.IMPOSSIBLE_REG_COMMENT);
//        }
        return service.insFeedComment(feedCommentInsDto);
    }

    @GetMapping
    public List<FeedCommentSelVo> getFeedCommentAll(FeedCommentSelDto feedCommentSelDto){
        // 4~ 999 까지의 레코드 리턴되게.
        feedCommentSelDto.setStartIdx(3);
        feedCommentSelDto.setRowCount(999);
        return service.getFeedCommentAll(feedCommentSelDto);

    }


}