package com.green.greengram4.user.model;

import lombok.Data;

@Data
public class UserSignUpEntity {

    private int iuser;
    private String uid;
    private String upw;
    private String nm;
    private String pic;

    public UserSignUpEntity(UserSignupDto userSignupDto, String hashedPassword){
        this.uid = userSignupDto.getUid();
        this.nm = userSignupDto.getNm();
        this.pic = userSignupDto.getPic();
        this.upw = hashedPassword;

    }

}
