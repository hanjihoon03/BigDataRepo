package com.example.bigdataboost.controller;

import com.example.bigdataboost.model.NaverShoppingResponse;
import com.example.bigdataboost.service.NaverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @GetMapping("naver/data")
    public ResponseEntity<List<NaverShoppingResponse>> naverAlldata() {
        List<NaverShoppingResponse> allNaver = naverService.findAllNaver();
        return ResponseEntity.ok(allNaver);
    }
}
