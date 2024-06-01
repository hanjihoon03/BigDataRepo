package com.example.bigdataboost.service;

import com.example.bigdataboost.config.NaverApiConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class NaverService {
    private final String seoulKey = "4f6a7963707261693537756f6b4379";

    private final NaverApiConfig naverApiConfig;
    private String apiUrl = "https://openapi.naver.com/v1/datalab/shopping/category/age";

    public String getSettingParam() {
        // 한국 시간대의 현재 시간을 가져옴
        ZonedDateTime nowInKorea = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

        // 현재 시간을 문자열로 포맷팅 (옵션)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = nowInKorea.format(formatter);

        // 한국 현재 시간을 변수에 저장
        ZonedDateTime koreanTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

        ZonedDateTime oneDayBefore = koreanTime.minusDays(1);
        // 하루 전 시간을 문자열로 포맷팅
        String formattedOneDayBefore = oneDayBefore.format(formatter);


        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("startDate", "2024-05-01");
        bodyMap.put("endDate", formattedDate);
        bodyMap.put("timeUnit", "month");
        bodyMap.put("category", "50000167");


        HttpHeaders headers = getHeaders();
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(bodyMap, headers);

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.exchange(new URI(apiUrl), HttpMethod.POST, entity, String.class);
            return response.getBody();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * 헤더 값
     */
    private HttpHeaders getHeaders() {

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("X-Naver-Client-Id", naverApiConfig.getClientId());
        httpHeaders.set("X-Naver-Client-Secret", naverApiConfig.getClientSecret());
        httpHeaders.set("Content-Type", "application/json");

        return httpHeaders;
    }

}
