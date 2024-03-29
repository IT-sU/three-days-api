package com.itsu.threedays.controller;

import com.itsu.threedays.exception.NotFoundException;
import com.itsu.threedays.service.CertifyService;
import com.itsu.threedays.service.HabitService;
import com.itsu.threedays.service.S3uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("api/habits")
public class CertifyController {
    private final CertifyService certifyService;
    private final HabitService habitService;
    private final S3uploader s3uploader;
    private static final String DEFAULT_IMAGE_URL = "https://itsubucket.s3.ap-northeast-2.amazonaws.com/certify-image/Untitled.png";

    @PostMapping(value = "{habitId}/certify", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<?> certifyHabit(@PathVariable("habitId") Long habitId,
                                   @RequestParam(value = "images", required = false) List<MultipartFile> images,
                                   @RequestParam(value = "level", defaultValue = "0") int level,
                                   @RequestParam(value = "review", defaultValue = "") String review) {
        try {
            List<String> imageUrls = null;
            if (images != null && !images.isEmpty()) {
                imageUrls = s3uploader.upload(images, "certify-image");
            } else {
                // 이미지가 선택되지 않았을 때, 기본값으로 설정된 URL 사용
                imageUrls = Collections.singletonList(DEFAULT_IMAGE_URL);
            }
            certifyService.certifyHabit(habitId, review, level, imageUrls); //습관인증저장

            //습관 인증 -> 습관테이블 변경사항(달성률, 달성횟수, 콤보횟수)
            habitService.updateAchievementAndCombo(habitId);

            return ResponseEntity.ok("습관이 인증되었습니다.");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}