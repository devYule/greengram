package com.green.greengram4.security.oauth2.userinfo;

import com.green.greengram4.security.oauth2.SocialProviderType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Oauth2UserInfoFactory {

    public Oauth2UserInfo getOauth2UserInfo(SocialProviderType type,
                                            Map<String, Object> attributes) {
        return switch (type) {
            case KAKAO -> new KakaoOauth2UserInfo(attributes);
            case NAVER -> new NaverOauth2UserInfo(attributes);
            default -> throw new IllegalArgumentException("Invalid Provider Ex");
        };
    }

}
