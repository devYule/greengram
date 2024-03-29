package com.green.greengram4.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignUpEntity {

    private int iuser;
    private String uid;
    private String upw;
    private String nm;
    private String pic;
    private String role;
    private String providerType;

    public UserSignUpEntity(UserSignupDto userSignupDto, String hashedPassword){
        this.uid = userSignupDto.getUid();
        this.nm = userSignupDto.getNm();
        this.pic = userSignupDto.getPic();
        this.upw = hashedPassword;

    }

}
