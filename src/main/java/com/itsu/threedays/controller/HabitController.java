package com.itsu.threedays.controller;

import com.itsu.threedays.dto.HabitDto;
import com.itsu.threedays.dto.HabitResponseDto;
import com.itsu.threedays.dto.HabitUpdateRequestDto;
import com.itsu.threedays.entity.HabitEntity;
import com.itsu.threedays.entity.UserEntity;
import com.itsu.threedays.exception.NotFoundException;
import com.itsu.threedays.service.HabitService;
import com.itsu.threedays.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("api")
public class HabitController {

    private final HabitService habitService;
    private final UserService userService;

    @PostMapping("habit") //습관 생성
    ResponseEntity<?> createHabit(@RequestBody HabitDto habitDto){

        UserEntity byEmail = userService.findByEmail(habitDto.getEmail());
        log.info("byEmail.getEmail(): {}",byEmail.getEmail());

        HabitEntity habit = HabitEntity.builder()
                .title(habitDto.getTitle()) //습관명 저장
                .duration(habitDto.getDuration()) //습관 기간 저장
                .visible(habitDto.isVisible()) //공개여부 저장 //!!다시 확인하기
                .createdDate(LocalDateTime.now()) //생성일 저장
                .lastModifiedDate(LocalDateTime.now()) //변경일 저장
                .achievementRate(0) //달성률 저장
                .achievementCount(0) //달성횟수 저장
                .totalAchievementCount(0) //누적달성횟수 저장
                .comboCount(0) //콤보횟수 저장
                .stopDate(null) //중지일 저장
                .deleteYn(false) //삭제여부 저장
                .userId(byEmail)
                .build();

        habitService.saveHabit(habit);
        log.info("habit entity: {}",habit);
        log.info("{}의 습관: 습관명-{}, 습관기간-{}, 공개여부-{}",
                byEmail.getNickname(),habit.getTitle(),habit.getDuration(),habit.isVisible());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("habits") //습관목록 조회(메인페이지)
    ResponseEntity<List<HabitResponseDto>> getHabitList(@RequestParam("email") String email) throws Exception {
        List<HabitEntity> habits = habitService.findUndeletedAndActiveHabits(email);
        log.info("habits: {}",habits);
        List<HabitResponseDto> habitResponseDtos = habits.stream()
                .map(habit -> {
                    HabitResponseDto responseDto = new HabitResponseDto();
                    responseDto.setId(habit.getId());
                    responseDto.setTitle(habit.getTitle());
                    responseDto.setVisible(habit.isVisible());
                    responseDto.setComboCount(habit.getComboCount());
                    responseDto.setAchievementRate(habit.getAchievementRate());
                    responseDto.setAchievementCount(habit.getAchievementCount());
                    return responseDto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(habitResponseDtos);
    }

    @PutMapping("habits/{habitId}/edit") //습관수정(이름, 기간, 공개여부)
    ResponseEntity<HabitResponseDto> updateHabit(@PathVariable("habitId") Long habitId,
                                      @RequestBody HabitUpdateRequestDto habitUpdateDto) throws Exception {
        HabitResponseDto updatedHabit = habitService.updateHabit(habitId, habitUpdateDto);

        return ResponseEntity.ok(updatedHabit);

    }

    @DeleteMapping("habits/{habitId}") //습관삭제
    ResponseEntity<?> deleteHabit(@PathVariable("habitId") Long habitId){
        habitService.deleteHabit(habitId);

        return ResponseEntity.ok("습관이 성공적으로 삭제되었습니다.");

    }

    //습관 그만두기
    //습관 편집시 목록

    @GetMapping("habits/{habitId}/reset") //매주마다 달성횟수 리셋
    ResponseEntity<?> resetAcievement(@PathVariable("habitId") Long habitId){
        habitService.resetAchievement(habitId);
        return ResponseEntity.ok("달성횟수가 성공적으로 리셋되었습니다.");
    }
}
