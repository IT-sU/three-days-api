package com.itsu.threedays.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserInfoDto {
    String email;
    String nickname;
    TokenDto tokenDto;
    Long userId;

}