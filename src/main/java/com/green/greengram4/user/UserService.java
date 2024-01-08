package com.green.greengram4.user;

import com.green.greengram4.common.Const;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.user.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper mapper;

    public ResVo signup(UserSignupDto dto) {

        // 비밀번호 암호화
        String hashedUpw = BCrypt.hashpw(dto.getUpw(), BCrypt.gensalt());
        UserSignUpEntity userSignUpEntity = new UserSignUpEntity(dto, hashedUpw);


        if (mapper.signup(userSignUpEntity) == 0) return new ResVo(0);

        return new ResVo(userSignUpEntity.getIuser()); // 회원 가입한 iuser pk 값 리턴.

    }


    // result -> 1: 성공 || 2: 아이디 없음 || 3: 비밀번호 틀림
    public UserSignInResultVo signin(UserSignInDto userSignInDto) {

        // get upw, pic, nm, iuser from db
        UserSelDto userSelDto = new UserSelDto();
        userSelDto.setUid(userSignInDto.getUid());
        UserEntity result = mapper.getUserLoginInfo(userSelDto);

        log.info("result = {}", result);

        UserSignInResultVo userSignInResultVo = new UserSignInResultVo();
        if (result == null) {

            userSignInResultVo.setResult(Const.LOGIN_FAIL_ID_NOT_EXISTS);
            return userSignInResultVo;
        }

        // compare password (use BCrypt)

        if (BCrypt.checkpw(userSignInDto.getUpw(), result.getUpw())) {

            userSignInResultVo.setResult(Const.LOGIN_SUCCEED);
            userSignInResultVo.setExtra(result);
            return userSignInResultVo;
        }
        userSignInResultVo.setResult(Const.LOGIN_FAIL_PASSWORD_IS_NOT_CORRECT);
        return userSignInResultVo;

    }

    public ResVo toggleFollow(UserFollowDto userFollowDto) {
        // 팔로잉 - 1 리턴, 취소될경우 - 0 리턴.
        ResVo result = new ResVo();
        if (mapper.unfollow(userFollowDto) == 0) {
            result.setResult(mapper.follow(userFollowDto));
        }
        return result;
    }

    public UserInfoVo selUserInfo(UserInfoSelDto userInfoSelDto) {
        log.info("mapper.selUserInfo(userInfoSelDto) = {}", mapper.selUserInfo(userInfoSelDto));


        return mapper.selUserInfo(userInfoSelDto);
    }


    public ResVo patchToken(UserFireBaseTokenPatchDto dto) {

        return new ResVo(mapper.patchToken(dto));
    }

    public ResVo patchUserPic(UserPicPatchDto dto) {
        return new ResVo(mapper.patchUserPic(dto));
    }
}
