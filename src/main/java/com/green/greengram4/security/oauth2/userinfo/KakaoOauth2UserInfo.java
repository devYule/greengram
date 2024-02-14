package com.green.greengram4.security.oauth2.userinfo;

import java.util.Map;

public class KakaoOauth2UserInfo extends Oauth2UserInfo {

    public KakaoOauth2UserInfo(Map<String, Object> attribute) {
        super(attribute);
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        return properties == null ? null : properties.get("nickname").toString();
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("account_email");
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        return properties == null ? null : properties.get("thumbnail_image").toString();
    }
}
