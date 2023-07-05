package com.itsu.threedays.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDto {
    String email; //로그인 한 유저의 email
    String nickname;
    List<String> keywords;
}
