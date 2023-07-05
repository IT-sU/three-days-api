package com.itsu.threedays;

import com.itsu.threedays.config.jwt.JwtConfig;
import com.itsu.threedays.config.jwt.JwtTokenProvider;
import com.itsu.threedays.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
