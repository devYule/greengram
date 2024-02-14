package com.green.greengram4.security.oauth2.userinfo;

import com.green.greengram4.common.Role;
import com.green.greengram4.security.MyPrincipal;
import com.green.greengram4.security.MyUserDetails;
import com.green.greengram4.security.oauth2.SocialProviderType;
import com.green.greengram4.user.UserMapper;
import com.green.greengram4.user.model.UserEntity;
import com.green.greengram4.user.model.UserSelDto;
import com.green.greengram4.user.model.UserSignUpEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {
    private final UserMapper mapper;
    private final Oauth2UserInfoFactory factory;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        SocialProviderType type =
                SocialProviderType.valueOf(
                        userRequest.getClientRegistration().getRegistrationId().toUpperCase()
                );
        // user 의 attribute 중 id 는 유일값, 동일 아이디 기준 동일한 값이 넘어온다.
        Oauth2UserInfo oauth2UserInfo = factory.getOauth2UserInfo(type, user.getAttributes());
        UserEntity userLoginInfo = getUserLoginInfo(oauth2UserInfo, type);
        if (userLoginInfo == null) {
            userLoginInfo = signupUser(oauth2UserInfo, type);
        }

        MyPrincipal myPrincipal = MyPrincipal.builder()
                .iuser(userLoginInfo.getIuser())
                .build();

        myPrincipal.getRoles().add(userLoginInfo.getRole());

        return MyUserDetails
                .builder()
                .userEntity(userLoginInfo)
                .attributes(user.getAttributes())
                .myPrincipal(myPrincipal)
                .build();


    }

    private UserEntity getUserLoginInfo(Oauth2UserInfo oauth2UserInfo, SocialProviderType type) {
        return mapper.getUserLoginInfo(
                UserSelDto.builder()
                        .uid(oauth2UserInfo.getId())
                        .providerType(type.name())
                        .build());
    }

    private UserEntity signupUser(Oauth2UserInfo oauth2UserInfo, SocialProviderType type) {
        UserSignUpEntity userSignUpEntity = UserSignUpEntity.builder()
                .uid(oauth2UserInfo.getId())
                .upw("social: " + UUID.randomUUID())
                .nm(oauth2UserInfo.getName())
                .pic(oauth2UserInfo.getImageUrl())
                .role(Role.USER.name())
                .providerType(type.name())
                .build();
        if (mapper.signup(userSignUpEntity) == 1) {
            return UserEntity.builder()
                    .iuser(userSignUpEntity.getIuser())
                    .role(Role.USER.name())
                    .nm(userSignUpEntity.getNm())
                    .pic(userSignUpEntity.getPic())
                    .uid(userSignUpEntity.getUid())
                    .build();
        }

        throw new OAuth2AuthenticationException("FAIL");
    }
}
