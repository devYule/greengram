package com.green.greengram4.user.model;

import com.green.greengram4.entity.UserEntity;
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

    public void setExtra(UserModel userModel) {
        this.iuser = userModel.getIuser();
        this.nm = userModel.getNm();
        this.pic = userModel.getPic();
    }

    public void setExtra(UserEntity userEntity) {
        this.iuser = userEntity.getIuser().intValue();
        this.nm = userEntity.getNm();
        this.pic = userEntity.getPic();
    }
}
