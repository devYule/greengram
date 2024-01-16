package com.green.greengram4.user;

import com.green.greengram4.common.Const;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.common.fileupload.MyFileUtils;
import com.green.greengram4.security.AuthenticationFacade;
import com.green.greengram4.security.JwtTokenProvider;
import com.green.greengram4.security.MyPrincipal;
import com.green.greengram4.security.MyUserDetails;
import com.green.greengram4.security.common.AppProperties;
import com.green.greengram4.security.common.CookieUtils;
import com.green.greengram4.user.model.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AppProperties appProperties;
    private final CookieUtils cookieUtils;
    private final AuthenticationFacade authenticationFacade;
    private final MyFileUtils fileUtils;


    public ResVo signup(UserSignupDto dto) {

        // 비밀번호 암호화
        String hashedUpw = passwordEncoder.encode(dto.getUpw());
        UserSignUpEntity userSignUpEntity = new UserSignUpEntity(dto, hashedUpw);


        if (mapper.signup(userSignUpEntity) == 0) return new ResVo(0);

        return new ResVo(userSignUpEntity.getIuser()); // 회원 가입한 iuser pk 값 리턴.

    }


    // result -> 1: 성공 || 2: 아이디 없음 || 3: 비밀번호 틀림
    public UserSignInResultVo signin(UserSignInVo userSignInDto, HttpServletResponse response) {

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

        if (!passwordEncoder.matches(userSignInDto.getUpw(), result.getUpw())) {

            userSignInResultVo.setResult(Const.LOGIN_FAIL_PASSWORD_IS_NOT_CORRECT);
            return userSignInResultVo;


        }
        MyPrincipal myPrincipal = new MyPrincipal(result.getIuser());
        String at = jwtTokenProvider.generateAccessToken(myPrincipal);
        String rt = jwtTokenProvider.generateRefreshToken(myPrincipal);


        int rtCookieMaxAge = (int) (appProperties.getJwt().getRefreshTokenExpiry() * 0.001);
        cookieUtils.deleteCookie(response, "rt");
        cookieUtils.setCookie(response, "rt", rt, rtCookieMaxAge);


        userSignInResultVo.setResult(Const.LOGIN_SUCCEED);
        userSignInResultVo.setExtra(result);

        userSignInResultVo.setAccessToken(at);

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

    public UserPicPatchDto patchUserPic(MultipartFile pic) {
        int iuser = authenticationFacade.getLoginUserPk();
        log.info("iuser = {}", iuser);
        fileUtils.delFiles();
        UserPicPatchDto dto =
                UserPicPatchDto.builder()
                        .iuser(iuser)
                        .pic(fileUtils.transferTo(pic, "/user/" + iuser))
                        .build();
        mapper.patchUserPic(dto);
        return dto;
    }

    public ResVo signout(HttpServletResponse response) {
        cookieUtils.deleteCookie(response, "rt");
        return new ResVo(1);
    }

    public UserSignInResultVo getRefreshToken(HttpServletRequest request) {
        Cookie cookie = cookieUtils.getCookie(request, "rt");
        if (cookie == null) return UserSignInResultVo.builder().result(0).accessToken(null).build();
        String token = cookie.getValue();
        MyUserDetails myUserDetails = (MyUserDetails) jwtTokenProvider.getUserDetailsFromToken(token);
        MyPrincipal myPrincipal = myUserDetails.getMyPrincipal();

        return UserSignInResultVo.builder()
                .result(1)
                .accessToken(jwtTokenProvider.generateAccessToken(myPrincipal))
                .build();
    }
}
