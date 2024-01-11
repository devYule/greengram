package com.green.greengram4.security.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "app") // 해당 app 아래에 있는 속성들을 객체화 시킴
//@ConfigurationPropertiesScan 를 Main Application 에 추가해주어야 함 (스캔)
public class AppProperties {
    /*
    필요한 어노테이션들
    @Getter
    @ConfigurationProperties(prefix = "app") // 해당 app 아래에 있는 속성들을 객체화 시킴
    //@ConfigurationPropertiesScan 를 Main Application 에 추가해주어야 함 (스캔)
     */

    private final Jwt jwt = new Jwt();

    @Getter
    @Setter
    public class Jwt {
        private String secret; // 암호화 시킬 키
        private String headerSchemaName; // authorization: 의 헤더로 들어오는것을 가져오기 위해
        private String tokenType; // authorization: 의 값에 있는 앞머리 Bearer
        private Long accessTokenExpiry; // access 토큰 만료 시간
        private Long refreshTokenExpiry; // refresh 토큰 만료 시간
        private int refreshTokenCookieMaxAge;
        /*
        자동으로 application.yaml 의 커스텀 내용들 주입할 수 있음.
         */

        public void setRefreshTokenExpiry(Long refreshTokenExpiry) {
            this.refreshTokenExpiry = refreshTokenExpiry;
            this.refreshTokenCookieMaxAge = (int) (refreshTokenExpiry * 0.001);
        }
    }


}
