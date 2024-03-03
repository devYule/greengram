package com.green.greengram4.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class FeedSelectDto {

    @JsonIgnore
    private Long loginIuser;
    @Schema(title = "선택된 유저 PK")
    private Long targetIuser;
    @Schema(title = "로그인 유저가 좋아요한 feeds")
    private int isFavList;

}
