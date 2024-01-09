package com.green.greengram4.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram4.security.common.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


/**
 * Security 와 토큰 관련 정보를 가져오거나 생성, 해석, 만료 체크 등을 하기위한 객체.
 * 토큰 내부에 Claims 를 보유하고 있다.
 * Claims 는 저장한 정보이다.
 *
 * 필요객체:
 * - MyPrincipal - 토큰에 담을 정보를 가진 객체
 * - AppProperties - application.properties || application.yaml 에 지정한 값들을 객체화 시킨 객체.
 *
 * 필요 어노테이션:
 * - @Component
 * - @Autowired or @RequiredArgsConstructor
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final AppProperties appProperties;
    private Key key;

    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        log.info("secret = {}", appProperties.getJwt().getSecret());
        byte[] keyBytes = Decoders.BASE64.decode(appProperties.getJwt().getSecret());
        log.info("key bytes = {}", keyBytes);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 토큰을 생성(발급) 한다.
     *
     * @param principal
     * @param tokenValidMs
     * @return
     */
    public String generateToken(MyPrincipal principal, long tokenValidMs) {
        return Jwts.builder()
                .claims(createClaims(principal))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenValidMs))
                .signWith(key)
                .compact();
    }

    private Claims createClaims(MyPrincipal principal) {

        String json;

        try {
            json = objectMapper.writeValueAsString(principal);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return Jwts.claims()
                .add("user", json)
                .build();
    }

    /**
     * 토큰 추출 (요청 header 의)
     * authorization:Bearer Aasjefgwioejgaoiewgj
     */
    public String getTokenFromHeader(HttpServletRequest request) {
        String headerAuthz = request.getHeader(appProperties.getJwt().getHeaderSchemaName());
        return headerAuthz == null ?
                null :
                headerAuthz.startsWith(appProperties.getJwt().getTokenType()) ?
                        headerAuthz.substring(appProperties.getJwt().getTokenType().length()).trim() :
                        null;

    }

    /**
     * 만료시간 체크
     *
     * @param token
     * @return
     */
    public boolean isValidatedToken(String token) {
        try {
            return !getAllClaims(token).getExpiration().before(new Date());

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 해당 토큰에서 부터 모든 정보(Claims)를 가져온다.
     * @param token
     * @return
     */
    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Authentication 의 구현객체 반환.
     * SecurityContextHolder 에 저장하기 위한 객체 반환.
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = getUserDetailsFromToken(token);

        return userDetails == null ? null :
//                                                        id           password      auth
                new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * 토큰에 저장시킨 정보를 모두 가져온다.
     * @param token
     * @return
     */
    private UserDetails getUserDetailsFromToken(String token) {
        // 토큰 다 가져옴
        Claims claims = getAllClaims(token);
        String json = (String) claims.get("user");
        MyPrincipal myPrincipal;
        try {
            myPrincipal = objectMapper.readValue(json, MyPrincipal.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return new MyUserDetails(myPrincipal);

    }


}
