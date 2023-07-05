package com.itsu.threedays.service;


import com.itsu.threedays.dto.HabitDto;
import com.itsu.threedays.entity.HabitEntity;
import com.itsu.threedays.entity.ProfileEntity;
import com.itsu.threedays.entity.UserEntity;
import com.itsu.threedays.exception.NotFoundException;
import com.itsu.threedays.repository.HabitRepository;
import com.itsu.threedays.repository.ProfileRepository;
import com.itsu.threedays.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final HabitRepository habitRepository;

    public UserEntity findByEmail(String email) {
        Optional<UserEntity> byEmail = userRepository.findByEmail(email);
        return byEmail.get();
    }
    public boolean isUserExist(String email){
        try {
            findByEmail(email);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void saveUser(UserEntity user){
       userRepository.save(user);
    }

    public UserEntity getUserById(Long id){
        Optional<UserEntity> byId = userRepository.findById(id);
        return byId.get();
    }

    /**
     * RefreshToken DB 저장(업데이트)
     */
//    public void updateRefreshToken(String email, String refreshToken) {
//        userRepository.findByEmail(email)
//                .ifPresentOrElse(
//                        user -> user.updateRefreshToken(refreshToken),
//                        () -> new Exception("일치하는 회원이 없습니다.")
//                );
//    }


    //유저 프로필 생성
    public List<HabitEntity> saveProfile(String email,String nickname,List<String> keywords){
        //유저 정보 조회
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));


        log.info("user:{}",user.getEmail());
        //프로필 생성 및 저장
        ProfileEntity profile = new ProfileEntity();
        profile.setUserId(user);
        profile.setNickname(nickname);
        profile.setKeywords(keywords);
        profileRepository.save(profile);

        //습괌 목록 조회
        List<HabitEntity> habits = habitRepository.findAllByUserId(user);
        log.info("habits :{}",habits);

        // 습관 목록 반환 (습관 목록이 없으면 null 반환)
        return habits.isEmpty() ? null : habits;
    }





}