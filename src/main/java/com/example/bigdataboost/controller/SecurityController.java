package com.example.bigdataboost.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SecurityController {

    @GetMapping("/secure")
    public ResponseEntity<String> testSecurityController() {
        return ResponseEntity.ok("security Test Ok");
    }
    @GetMapping("/test")
    public ResponseEntity<String> testController() {
        return ResponseEntity.ok("security Test Ok");
    }
}
