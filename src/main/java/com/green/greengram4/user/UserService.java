package com.green.greengram4.user;

import com.green.greengram4.common.Const;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.common.Role;
import com.green.greengram4.common.fileupload.MyFileUtils;
import com.green.greengram4.entity.UserEntity;
import com.green.greengram4.entity.UserFollow;
import com.green.greengram4.entity.UserFollowIdentities;
import com.green.greengram4.exception.AuthErrorCode;
import com.green.greengram4.exception.RestApiException;
import com.green.greengram4.security.AuthenticationFacade;
import com.green.greengram4.security.JwtTokenProvider;
import com.green.greengram4.security.MyPrincipal;
import com.green.greengram4.security.MyUserDetails;
import com.green.greengram4.security.common.AppProperties;
import com.green.greengram4.security.common.CookieUtils;
import com.green.greengram4.security.oauth2.SocialProviderType;
import com.green.greengram4.user.model.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

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

    //

    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;

    public ResVo signup(UserSignupDto dto) {
        UserEntity entity = userRepository.save(UserEntity.builder()
                .providerType(SocialProviderType.LOCAL)
                .uid(dto.getUid())
                .upw(passwordEncoder.encode(dto.getUpw()))
                .nm(dto.getNm())
                .pic(dto.getPic())
                .role(Role.USER)
                .build());
        return new ResVo(entity.getIuser().intValue());
    }

    //    public ResVo signup(UserSignupDto dto) {
//
//        // 비밀번호 암호화
//        String hashedUpw = passwordEncoder.encode(dto.getUpw());
//        UserSignUpEntity userSignUpEntity = new UserSignUpEntity(dto, hashedUpw);
//
//
//        if (mapper.signup(userSignUpEntity) == 0) return new ResVo(0);
//
//        return new ResVo(userSignUpEntity.getIuser()); // 회원 가입한 iuser pk 값 리턴.
//
//    }
    public UserSignInResultVo signin(UserSignInVo userSignInDto, HttpServletResponse response) {

        UserEntity userEntity = userRepository.findByProviderTypeAndUid(SocialProviderType.LOCAL, userSignInDto.getUid())
                .orElseThrow(() -> new RestApiException(AuthErrorCode.VALID_PASSWORD));
        if (!passwordEncoder.matches(userSignInDto.getUpw(), userEntity.getUpw())) {
            throw new RestApiException(AuthErrorCode.VALID_PASSWORD);
        }
        // 동일

        MyPrincipal myPrincipal = MyPrincipal.builder()
                .iuser(userEntity.getIuser().intValue())
//                .roles(List.of(result.getRole()))
                .build();
        myPrincipal.getRoles().add(userEntity.getRole().name());


        String at = jwtTokenProvider.generateAccessToken(myPrincipal);
        String rt = jwtTokenProvider.generateRefreshToken(myPrincipal);


        int rtCookieMaxAge = (int) (appProperties.getJwt().getRefreshTokenExpiry() * 0.001);
        cookieUtils.deleteCookie(response, "rt");
        cookieUtils.setCookie(response, "rt", rt, rtCookieMaxAge);

        UserSignInResultVo userSignInResultVo = new UserSignInResultVo();

        userSignInResultVo.setResult(Const.LOGIN_SUCCEED);
        userSignInResultVo.setExtra(userEntity);

        userSignInResultVo.setAccessToken(at);

        return userSignInResultVo;


    }

    // result -> 1: 성공 || 2: 아이디 없음 || 3: 비밀번호 틀림
//    public UserSignInResultVo signin(UserSignInVo userSignInDto, HttpServletResponse response) {
//
//        // get upw, pic, nm, iuser from db
//        UserSelDto userSelDto = new UserSelDto();
//        userSelDto.setUid(userSignInDto.getUid());
//        userSelDto.setProviderType(SocialProviderType.LOCAL.name());
//        UserModel result = mapper.getUserLoginInfo(userSelDto);
//
//        log.info("result = {}", result);
//
//        UserSignInResultVo userSignInResultVo = new UserSignInResultVo();
//        if (result == null) {
//
//            userSignInResultVo.setResult(Const.LOGIN_FAIL_ID_NOT_EXISTS);
//            return userSignInResultVo;
//        }
//
//        // compare password (use BCrypt)
//
//        if (!passwordEncoder.matches(userSignInDto.getUpw(), result.getUpw())) {
//
//            throw new PasswordNotMatchException(AuthErrorCode.VALID_PASSWORD);
//
////            userSignInResultVo.setResult(Const.LOGIN_FAIL_PASSWORD_IS_NOT_CORRECT);
////            return userSignInResultVo;
//
//
//        }
////        MyPrincipal myPrincipal = new MyPrincipal(result.getIuser(), List.of(result.getRole()));
//        MyPrincipal myPrincipal = MyPrincipal.builder()
//                .iuser(result.getIuser())
////                .roles(List.of(result.getRole()))
//                .build();
//        myPrincipal.getRoles().add(result.getRole());
//
//
//        String at = jwtTokenProvider.generateAccessToken(myPrincipal);
//        String rt = jwtTokenProvider.generateRefreshToken(myPrincipal);
//
//
//        int rtCookieMaxAge = (int) (appProperties.getJwt().getRefreshTokenExpiry() * 0.001);
//        cookieUtils.deleteCookie(response, "rt");
//        cookieUtils.setCookie(response, "rt", rt, rtCookieMaxAge);
//
//
//        userSignInResultVo.setResult(Const.LOGIN_SUCCEED);
//        userSignInResultVo.setExtra(result);
//
//        userSignInResultVo.setAccessToken(at);
//
//        return userSignInResultVo;
//
//
//    }


    @Transactional
    public ResVo toggleFollow(UserFollowDto userFollowDto) {
        // 팔로잉 - 1 리턴, 취소될경우 - 0 리턴.
        ResVo resVo = new ResVo();
        UserFollowIdentities userFollowIdentities = UserFollowIdentities.builder()
                .toIuser((long) userFollowDto.getToIuser())
                .fromIuser((long) authenticationFacade.getLoginUserPk())
                .build();
        AtomicInteger atomic = new AtomicInteger(0);
        // 1번째 파라미터는 ifPresent 일때, 2번째 파라미터는 null 일때의 행위다.
        userFollowRepository.findById(userFollowIdentities).ifPresentOrElse(
                userFollowRepository::delete,
                () -> {
                    atomic.set(1);
                    UserFollow userFollowWhenNull = new UserFollow();
                    userFollowWhenNull.setUserFollowIdentities(userFollowIdentities);
                    UserEntity fromUserEntity = userRepository.getReferenceById((long) authenticationFacade.getLoginUserPk());
                    UserEntity toUserEntity = userRepository.getReferenceById((long) userFollowDto.getToIuser());
                    userFollowWhenNull.setFromUserEntity(fromUserEntity);
                    userFollowWhenNull.setToUserEntity(toUserEntity);
                    userFollowRepository.save(userFollowWhenNull);
                });
        resVo.setResult(atomic.get());
        return resVo;

    }

//    public ResVo toggleFollow(UserFollowDto userFollowDto) {
//        // 팔로잉 - 1 리턴, 취소될경우 - 0 리턴.
//        ResVo result = new ResVo();
//
//        if (mapper.unfollow(userFollowDto) == 0) {
//            result.setResult(mapper.follow(userFollowDto));
//        }
//        return result;
//    }

    public UserInfoVo selUserInfo(UserInfoSelDto userInfoSelDto) {
        log.info("mapper.selUserInfo(userInfoSelDto) = {}", mapper.selUserInfo(userInfoSelDto));


        return mapper.selUserInfo(userInfoSelDto);
    }

    @Transactional
    public ResVo patchToken(UserFireBaseTokenPatchDto dto) {
        UserEntity findUser = userRepository.getReferenceById((long) authenticationFacade.getLoginUserPk());
        findUser.setFirebaseToken(dto.getFirebaseToken());
        return new ResVo(Const.LOGIN_SUCCEED);
    }

//    public ResVo patchToken(UserFireBaseTokenPatchDto dto) {
//
//        return new ResVo(mapper.patchToken(dto));
//    }

    @Transactional
    public UserPicPatchDto patchUserPic(MultipartFile pic) {
        int iuser = authenticationFacade.getLoginUserPk();
        UserEntity userEntity = userRepository.findById((long) iuser)
                .orElseThrow(() -> new RestApiException(AuthErrorCode.VALID_PASSWORD));
        log.info("iuser = {}", iuser);
        String path = "/user/" + iuser;
        fileUtils.delFolderTrigger(path);
        UserPicPatchDto dto =
                UserPicPatchDto.builder()
                        .iuser(iuser)
                        .pic(fileUtils.transferTo(pic, path))
                        .build();

        userEntity.setPic(dto.getPic());

        return dto;
    }

//    public UserPicPatchDto patchUserPic(MultipartFile pic) {
//        int iuser = authenticationFacade.getLoginUserPk();
//        log.info("iuser = {}", iuser);
//        String path = "/user/" + iuser;
//        fileUtils.delFolderTrigger(path);
//        UserPicPatchDto dto =
//                UserPicPatchDto.builder()
//                        .iuser(iuser)
//                        .pic(fileUtils.transferTo(pic, path))
//                        .build();
//        mapper.patchUserPic(dto);
//
//        return dto;
//    }

    public ResVo signout(HttpServletResponse response) {
        cookieUtils.deleteCookie(response, "rt");
        return new ResVo(1);
    }

    public UserSignInResultVo getRefreshToken(HttpServletRequest request) {
//        Cookie cookie = cookieUtils.getCookie(request, "rt");
        Optional<String> opRt = cookieUtils.getCookie(request, "rt").map(Cookie::getValue);

        if (opRt.isEmpty()) return UserSignInResultVo.builder().result(0).accessToken(null).build();
        String token = opRt.get();
        MyUserDetails myUserDetails = (MyUserDetails) jwtTokenProvider.getUserDetailsFromToken(token);
        MyPrincipal myPrincipal = myUserDetails.getMyPrincipal();

        return UserSignInResultVo.builder()
                .result(1)
                .accessToken(jwtTokenProvider.generateAccessToken(myPrincipal))
                .build();
    }
}
