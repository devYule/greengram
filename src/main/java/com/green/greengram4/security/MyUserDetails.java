package com.green.greengram4.security;

import com.green.greengram4.user.model.UserModel;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MyUserDetails implements UserDetails, OAuth2User {

    /**
     * 필요 어노테이션
     * - @NoArgsConstructor
     * - @AllArgsConstructor
     * - @Getter
     * - @Setter
     * - @Builder
     */
    private MyPrincipal myPrincipal;
    private Map<String, Object> attributes;
    private UserModel userModel;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return myPrincipal == null ? null :
                myPrincipal.getRoles()
                        .stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                        // security 에서는 앞에 'ROLE_' 접두사가 필수
                        .toList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    // 소셜 로그인은 security 의 루틴을 따르기 때문에 null 을 리턴하면 안됨.
    @Override
    public String getUsername() {
        return userModel == null ? null : userModel.getUid();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
