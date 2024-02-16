package com.green.greengram4.user;

import com.green.greengram4.user.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int signup(UserSignUpEntity userSignUpEntity);

    UserModel getUserLoginInfo(UserSelDto userSelDto);

    int unfollow(UserFollowDto userFollowDto);

    int follow(UserFollowDto userFollowDto);

    UserInfoVo selUserInfo(UserInfoSelDto userInfoSelDto);


    int patchToken(UserFireBaseTokenPatchDto dto);

    int patchUserPic(UserPicPatchDto dto);
}
