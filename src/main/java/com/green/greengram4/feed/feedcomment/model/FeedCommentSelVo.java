package com.green.greengram4.feed.feedcomment.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedCommentSelVo {
    private int ifeedComment;
    private Long ifeed;
    private String comment;
    private String createdAt;
    private int writerIuser;
    private String writerNm;
    private String writerPic;
}
