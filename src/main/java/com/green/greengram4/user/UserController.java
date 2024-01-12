package com.green.greengram4.user;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService service;

    @Operation(summary = "회원가입", description = "회원가입 처리")
    @PostMapping("/signup")
    public ResVo postSignup(@RequestBody UserSignupDto dto) {
        log.info("dto = {}", dto);
        return service.signup(dto);
    }

    @PostMapping("/signin")
    public UserSignInResultVo postSignin(@RequestBody UserSignInVo userSignInDto, HttpServletResponse response) {

        log.info("userSignInDto = {}", userSignInDto);
        return service.signin(userSignInDto, response);

    }

    @PostMapping("/signout")
    public ResVo postSignout(HttpServletResponse response) {

        return service.signout(response);

    }

    @GetMapping("/refresh-token")
    public UserSignInResultVo getRefreshToken(HttpServletRequest request) {
        return service.getRefreshToken(request);
    }


    @PatchMapping("/firebase-token")
    public ResVo patchUserFirebaseToken(@RequestBody UserFireBaseTokenPatchDto dto) {

        log.info("dto = {}", dto);
        return service.patchToken(dto);
    }

    @PatchMapping("/pic")
    public ResVo patchUserPic(@RequestBody UserPicPatchDto dto) {
        return service.patchUserPic(dto);
    }


    // --------- follow -------------------
    @PostMapping("/follow")
    public ResVo toggleFollow(@RequestBody UserFollowDto userFollowDto) {
        log.info("userFollowDto = {}", userFollowDto);
        return service.toggleFollow(userFollowDto);
    }

    @GetMapping
    public UserInfoVo selUserInfo(UserInfoSelDto userInfoSelDto) {

        return service.selUserInfo(userInfoSelDto);

    }
}
