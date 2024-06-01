package com.example.bigdataboost.controller;

import com.example.bigdataboost.service.NaverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestController {

    private final NaverService naverService;

    @GetMapping("/naver")
    public ResponseEntity<String> naverTest() {
        String settingParam = naverService.getSettingParam();
        return ResponseEntity.ok(settingParam);
    }
}
