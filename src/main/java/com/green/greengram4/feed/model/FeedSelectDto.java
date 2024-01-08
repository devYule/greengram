package com.green.greengram4.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.green.greengram4.common.Const.FEED_COUNT_PER_PAGE;


@Getter
@Setter
@ToString
public class FeedSelectDto {

    @Schema(title = "페이지", defaultValue = "1")
    private int page;
    @Schema(title = "로그인 유저 PK")
    private int loginedIuser;
    @Schema(title = "선택된 유저 PK")
    private int targetIuser;
    @Schema(title = "로그인 유저가 좋아요한 feeds")
    private int isFavList;

    @JsonIgnore
    private int startIdx;
    @JsonIgnore
    private int rowCount;


    public void setLoginedIuser(int loginedIuser) {
        this.loginedIuser = loginedIuser;
    }

    public void setPage(int page) {
        this.page = page;
        this.rowCount = FEED_COUNT_PER_PAGE;
        this.startIdx = (page - 1) * rowCount;
    }



}
