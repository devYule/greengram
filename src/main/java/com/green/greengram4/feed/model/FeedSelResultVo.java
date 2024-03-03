package com.green.greengram4.feed.model;


import com.green.greengram4.feed.feedcomment.model.FeedCommentSelVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedSelResultVo {

    // 기본 필드
    private int ifeed;
    private String contents;
    private String location;
    private String createdAt;
    private int writerIuser;
    private String writerNm;
    private String writerPic;

    // 이미지 추가
    private List<String> pics;

    // 좋아요 여부 체크
    private int isFav; // 1: 좋아요 한 상태, 0: 좋아요 하지 않은 상태

    // 댓글 가져오기
    private List<FeedCommentSelVo> comments;
    private int isMoreComment; // 0: 댓글 더 없음, 2: 갯글 더 있음.


//    public <R> FeedSelResultVo(Long ifeed, String contents, String location, LocalDateTime createdAt, Long iuser, String pic, Stream<R> rStream, Object o) {
//    }
}
