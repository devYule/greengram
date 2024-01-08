package com.green.greengram4.feed.model;

import lombok.Data;

@Data
public class InsertFeedDto {
    private int ifeed;
    private int iuser;
    private String contents;
    private String location;

    public InsertFeedDto(FeedInsDto feedInsDto) {
        this.iuser = feedInsDto.getIuser();
        this.contents = feedInsDto.getContents();
        this.location = feedInsDto.getLocation();
    }
}
