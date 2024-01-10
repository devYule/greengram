package com.green.greengram4.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {

    /**
     * 필요 어노테이션
     * - @Configuration
     * - @Autowired || @RequiredArgsConstructor
     */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    /**
     * 인가
     * filter 기능 사용 됨
     * Spring boot 의 내장 톰캣 덕분에 filter 도 스프링의 관리 영역에 들어오게 됨 (DI 까지 가능해짐)
     *
     * @param httpSecurity
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // session 사용하지 않겠다 - 설정 (세션 끄기)
        log.info("checkClass = {}", this.getClass());
        return httpSecurity.sessionManagement(authz -> authz.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(http -> http.disable()) // security 자체에서 제공하는 로그인 화면을 끈다.
                .formLogin(formLogin -> formLogin.disable()) // form 로그인 끄기
                .csrf(csrf -> csrf.disable()) // 기본 제공되는 보안기법 끄기 (화면이 있으면 필요 없음으로) -> 차후 공부해보자 - csrf, cors 보안
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                                        "api/user/signin",
                                        "/api/user/signup",
                                        "/error",
                                        "/err",
                                        "/index.html",
                                        "/static/**", // react 의 static url 부분 허용 (다운로드를 위해 열어주어야 함)
                                        "/",
                                        "/swagger.html", // swagger 허용
                                        "/swagger-ui/**", // swagger 허용
                                        "v3/api-docs/**") // swagger 허용
                                .permitAll() // 해당 url 의 요청은 인증, 인가 없이 패스 (permit All)
/*
                          .requestMatchers(HttpMethod.GET, "url").permitAll()) // 해당 메소드의 url 에 허용
                          **xxx** 로 포함여부 체크도 가능. (requestMatcherse)
                          권한 - hasAnyRole("xxx", "yyy") = 해당 문자열의 권한일때는 잡속 가능.
                          기타 기능은 requestMatchers 로 검색하여 공부.
                          .anyRequest("ADMIN") 으로 ADMIN 이 모든 요청에 허용하게 되는것이다. (권한은 JWT 에서 설정 가능하다 - 차후 배움)
 */

                                .anyRequest().authenticated() // 나머지 요청은 로그인 해야만 쓸 수 있게 설정.
                )
                .exceptionHandling(ex -> {
                    // 예외 Handling 부분. Advice 로도 해결 가능해 보임
                    // (예외종류는 AuthenticationException, AccessDeniedException 임.
                    // ㄴ> filter 에서 검증하면 안되긴 할듯함.
                    ex.authenticationEntryPoint(new JwtAuthenticationEntryPoint());
                    ex.accessDeniedHandler(new JwtAccessDeniedHandler());
                }).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    /**
     * 비밀번호를 BCrypt 로 암호화 하기 위한 인코더
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}