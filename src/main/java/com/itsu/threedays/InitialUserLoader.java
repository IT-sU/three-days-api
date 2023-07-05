package com.itsu.threedays;

import com.itsu.threedays.entity.UserEntity;
import com.itsu.threedays.entity.role.Role;
import com.itsu.threedays.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

//@Component
//public class InitialUserLoader implements ApplicationRunner {
//    private final UserRepository userRepository;
//    @Autowired
//    public InitialUserLoader(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//    @Override
//    public void run(ApplicationArguments args){
//        //초기 유저셋팅
//        UserEntity user = new UserEntity();
//        user.setEmail("threeday2023@gmail.com");
//        user.setNickname("itsU");
//        user.setPassword("password1234");
//        user.setRole(Role.USER);
//
//        userRepository.save(user);
//    }
//}
