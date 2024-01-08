package com.green.greengram4.user.model;

import lombok.Data;

@Data
public class UserSignInResultVo {
    private int result;
    private int iuser;
    private String nm;
    private String pic;

    public void setExtra(UserEntity userEntity) {
        this.iuser = userEntity.getIuser();
        this.nm = userEntity.getNm();
        this.pic = userEntity.getPic();
    }
}
