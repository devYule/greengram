package com.green.greengram4.feed.feedcomment.model;

import lombok.Data;

@Data
public class FeedCommentSelVo {

    private int ifeedComment;
    private String comment;
    private int writerIuser;
    private String writerNm;
    private String writerPic;
    private String createdAt;
}
