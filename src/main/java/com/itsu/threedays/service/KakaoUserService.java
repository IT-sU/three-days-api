//package com.itsu.threedays.service;
//
//import com.itsu.threedays.config.kakao.KakaoOAuth2;
//import com.itsu.threedays.config.kakao.KakaoUserInfo;
//import com.itsu.threedays.entity.UserEntity;
//import com.itsu.threedays.entity.role.Role;
//import com.itsu.threedays.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class KakaoUserService {
//    private KakaoOAuth2 kakaoOAuth2;
//    private UserRepository userRepository;
//
//    public String kakaoLogin(String token){
//        // 카카오 OAuth2 를 통해 카카오 사용자 정보 조회
//        KakaoUserInfo userInfo = kakaoOAuth2.getUserInfo(token);
//        String nickname = userInfo.getNickname();
//        String email = userInfo.getEmail();
//
//        // DB 에 중복된 Kakao Id 가 있는지 확인
//        UserEntity kakaoUser = userRepository.findByEmail(email)
//                .orElse(null);
//
//        // 카카오 정보로 회원가입
//        if (kakaoUser == null) {
//            log.info("회원가입 시작");
//
//            UserEntity user = UserEntity.builder()
//                    .role(Role.USER)
//                    .email(email)
//                    .nickname(nickname)
//                    .password(new BCryptPasswordEncoder().encode(email))
////                        .fireBaseToken(userDto.getFirebaseToken())
//                    .build();
//
//            userRepository.save(user);
//        }
//
//        return email;
//
//    }
//
//
//}
//
