package com.green.greengram4.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignInResultVo {
    private int result;
    private int iuser;
    private String nm;
    private String pic;
    private String accessToken;

    public void setExtra(UserEntity userEntity) {
        this.iuser = userEntity.getIuser();
        this.nm = userEntity.getNm();
        this.pic = userEntity.getPic();
    }
}
