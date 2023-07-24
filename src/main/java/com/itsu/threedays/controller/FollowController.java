package com.itsu.threedays.controller;

import com.itsu.threedays.dto.FollowUserDto;
import com.itsu.threedays.entity.FollowEntity;
import com.itsu.threedays.entity.HabitEntity;
import com.itsu.threedays.entity.ProfileEntity;
import com.itsu.threedays.entity.UserEntity;
import com.itsu.threedays.service.FollowService;
import com.itsu.threedays.service.HabitService;
import com.itsu.threedays.service.ProfileService;
import com.itsu.threedays.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/users")
public class FollowController {
    private final FollowService followService;
    private final UserService userService;
    private final ProfileService profileService;
    private final HabitService habitService;

    @PostMapping("{fromUserId}/follow/{toUserId}")
//팔로우하기
    ResponseEntity<?> Follow(@PathVariable Long fromUserId, @PathVariable Long toUserId) {
        followService.followUser(fromUserId, toUserId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //팔로잉목록 - 닉네임, 프로필, 달성중인 습관갯수, 평균 달성률
    @GetMapping("followingList/me")
    ResponseEntity<List<FollowUserDto>> getFollowingList(@RequestParam("email") String email) throws Exception {
        UserEntity byEmail = userService.findByEmail(email);
        ProfileEntity profile = profileService.getProfile(byEmail);
        List<HabitEntity> h = habitService.findUndeletedAndActiveHabits(email); //내 습관목록(메인페이지)
        int totalRate = habitService.calculateAverageAchievementRate(h); //내 습관목록의 평균 달성률
        List<FollowEntity> followingList = followService.getFollowingList(byEmail.getId());
        log.info("followingList :{}", followingList);
        List<FollowUserDto> followDtoList = followingList.stream().map(follow -> {
            UserEntity toUser = follow.getToUser();

            return FollowUserDto.builder()
                    .id(toUser.getId())
                    .profileImageUrl(toUser.getProfile_image())
                    .nickname(profile.getNickname()) //profile의 nickname
                    .totalAchievementRate(totalRate)
                    .totalHabitCount(h.size()).build(); //내 습관목록들 갯수(=달성중)

        }).collect(Collectors.toList());
        return ResponseEntity.ok(followDtoList);
    }

    //팔로워목록 - 닉네임, 프로필, 달성중인 습관갯수, 평균 달성률

    //팔로우 삭제하기


}
