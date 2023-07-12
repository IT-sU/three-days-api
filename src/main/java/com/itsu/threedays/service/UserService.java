package com.itsu.threedays.service;


import com.itsu.threedays.entity.UserEntity;
import com.itsu.threedays.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserEntity findByEmail(String email) {
        Optional<UserEntity> byEmail = userRepository.findByEmail(email);
        return byEmail.get();
    }

    public boolean isUserExist(String email) {
        try {
            findByEmail(email);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }


}