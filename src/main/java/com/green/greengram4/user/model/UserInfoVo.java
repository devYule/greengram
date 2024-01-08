package com.green.greengram4.user.model;

import lombok.Data;

@Data
public class UserInfoVo {
    private int feedCnt;
    private int FavCnt;

    private String nm;
    private String createdAt;
    private String pic;
    // 해당 유저의 팔로워 수 (targetIuser 를 팔로우 하는 사람)
    private int follower; 
    // 팔로잉 수 (targetIuser 가 팔로우 하는 사람)
    private int following;
    // 1: loginedIuser 만 targetIuser 를 팔로우 한 상태
    // 2: targetIuser 만 나를 팔로우 한 상태
    // 3: 서로 팔로우 한 상태
    private int followState; 
            
}
