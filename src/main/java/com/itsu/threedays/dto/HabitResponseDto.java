package com.itsu.threedays.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HabitResponseDto {
    Long id;
    String title;
    int duration;
    boolean visible;
    int comboCount;
    int achievementRate;
    int achievementCount;
    boolean deleteYn;
    LocalDateTime stopDate;

}