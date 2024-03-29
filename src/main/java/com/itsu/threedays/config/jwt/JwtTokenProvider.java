package com.itsu.threedays.config.jwt;

import com.itsu.threedays.entity.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Component
@Slf4j
public class JwtTokenProvider {
    private final Long accessTokenValid = 60 * 60 * 24 * 1000L; // 24 hour
    private final Long refreshTokenValid = 14 * 24 * 60 * 60 * 1000L; // 14 day

    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    public void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String generateToken(UserEntity user, Long tokenValid) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenValid);
//        Claims claims = Jwts.claims().setSubject(String.valueOf(authentication.getPrincipal()));
//        String authorities = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(user.getNickname())
//                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("email", user.getEmail())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String generateAccessToken(UserEntity user) {
        return generateToken(user, accessTokenValid);
    }

    public String generateRefreshToken(UserEntity user) {
        return generateToken(user, refreshTokenValid);
    }


    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            //토큰의 만료 시간이 현재 시간 이전인지를 확인 -> 만료 시간이 현재 시간 이전이라면 유효하지 않은 토큰으로 판단
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 인증 성공시 SecurityContextHolder에 저장할 Authentication 객체 생성
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        //User 객체를 만들어서 Authentication 리턴
        User principal = new User(claims.get("email", String.class), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private Object detailedValidateToken(String jwtToken) {
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(jwtToken);
            return true;
        } catch (ExpiredJwtException e) {
            log.info("JWT token has expired.");//유효기간 만료
            return e;
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token: " + e.getMessage());//지원하지 않는 형식 또는 구조
            return e;
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token: " + e.getMessage()); //지원하지 않는 형식 또는 손상
            return e;
        } catch (SignatureException e) {
            log.info("Invalid JWT signature: " + e.getMessage()); //유효하지 않은 서명
            return e;
        } catch (JwtException e) {
            log.info("Invalid JWT token: " + e.getMessage()); //예상하지 못한 유효성 검사 실패
            return e;
        }
    }

    public Boolean isExpired(String token) {
        Object detailedValidateToken = detailedValidateToken(token);

        if (detailedValidateToken instanceof ExpiredJwtException) {
            return true;
        }
        return false;
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Duration getExpiryDuration(String token) { //만료기간 확인
        Claims claims = getClaims(token);
        Date expiration = claims.getExpiration();
        return Duration.ofDays(expiration.getTime() - System.currentTimeMillis());
    }

}