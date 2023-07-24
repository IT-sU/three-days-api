package com.itsu.threedays.service;

import com.itsu.threedays.entity.FollowEntity;
import com.itsu.threedays.entity.UserEntity;
import com.itsu.threedays.repository.FollowRepository;
import com.itsu.threedays.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final UserService userService;

    public void followUser(Long fromUserId, Long toUserId) {
        UserEntity fromUser = userService.getUser(fromUserId);
        UserEntity toUser = userService.getUser(toUserId);
        log.info("{}이 {}를 팔로우했습니다.", fromUser.getNickname(), toUser.getNickname());

        FollowEntity follow = FollowEntity.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .createdDate(LocalDateTime.now())
                .build();
        followRepository.save(follow);
    }

    public List<FollowEntity> getFollowingList(Long userId) { //본인이 팔로잉한 목록조회
        UserEntity user = userService.getUser(userId);
        List<FollowEntity> allByFromUser = followRepository.findAllByFromUser(user);
        return allByFromUser;
    }

    public List<FollowEntity> getFollowerList(Long userId) { //본인을 팔로워한 목록조회
        UserEntity user = userService.getUser(userId);
        List<FollowEntity> allByToUser = followRepository.findAllByToUser(user);
        return allByToUser;
    }


}
