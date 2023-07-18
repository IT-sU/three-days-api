package com.itsu.threedays.controller;

import com.itsu.threedays.entity.FollowEntity;
import com.itsu.threedays.entity.UserEntity;
import com.itsu.threedays.repository.FollowRepository;
import com.itsu.threedays.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/users")
public class FollowController {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @PostMapping("{fromUserId}/follow/{toUserId}")
    ResponseEntity<?> Follow(@PathVariable Long fromUserId, @PathVariable Long toUserId) {
        UserEntity fromUser = userRepository.findById(fromUserId).get();
        UserEntity toUser = userRepository.findById(toUserId).get();

        FollowEntity follow = FollowEntity.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .build();
        followRepository.save(follow);
        log.info("{} 이 {} 을 팔로우하였습니다.", fromUser.getNickname(), toUser.getNickname());

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
