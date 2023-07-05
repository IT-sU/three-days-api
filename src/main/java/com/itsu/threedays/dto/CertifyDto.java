package com.itsu.threedays.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CertifyDto {
    String image;
    String review;
    int level;
}
