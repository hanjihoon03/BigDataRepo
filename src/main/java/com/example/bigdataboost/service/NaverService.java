package com.example.bigdataboost.service;

import com.example.bigdataboost.config.NaverApiConfig;
import com.example.bigdataboost.model.NaverShoppingResponse;
import com.example.bigdataboost.repository.NaverRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class NaverService {
    private final NaverRepository naverRepository;
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
        bodyMap.put("startDate", formattedOneDayBefore);
        bodyMap.put("endDate", formattedDate);
        bodyMap.put("timeUnit", "date");
        bodyMap.put("category", "50000167");


        HttpHeaders headers = getHeaders();
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(bodyMap, headers);

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    new URI(apiUrl), HttpMethod.POST, entity, String.class);

            // JSON 응답을 로그로 출력
            log.info("response body: {}", response.getBody());

            // JSON 문자열을 NaverShoppingResponse 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            NaverShoppingResponse naverShoppingResponse = objectMapper.readValue(response.getBody(), NaverShoppingResponse.class);
            naverRepository.save(naverShoppingResponse);


            // 변환된 객체를 로그로 출력 (디버깅용)
            log.info("naverShoppingResponse: {}", naverShoppingResponse);

            return naverShoppingResponse.toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<NaverShoppingResponse> findAllNaver() {
        return naverRepository.findAllNaver();
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
