package com.itsu.threedays;

import com.itsu.threedays.config.kakao.jwt.JwtConfig;
import com.itsu.threedays.config.kakao.jwt.JwtTokenProvider;
import com.itsu.threedays.entity.UserEntity;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private JwtConfig jwtConfig;

    @BeforeEach
    void setUp() {
        jwtConfig = new JwtConfig();
        jwtConfig.setJwtKey("secretKey");
    }



    @Test
    @DisplayName("Accesstoken 생성 테스트")
    void generated_accesstoken_test() {
        //given
        UserEntity user = new UserEntity();
        user.setNickname("itsU");
        user.setEmail("threedays@gmail.com");


        //when
        String accessToken = jwtTokenProvider.generateAccessToken(user);

        //then
        assertNotNull(accessToken);

    }
}
