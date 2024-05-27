package com.example.bigdataboost.redistest;

import com.example.bigdataboost.service.RedisService;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.assertj.core.api.Assertions.*;
import static org.awaitility.Awaitility.await;

@Slf4j
@SpringBootTest
public class RedisCrudTest {
    final String KEY = "key";
    final String VALUE = "value";
    final Duration DURATION = Duration.ofMillis(5000);

    @Autowired
    private RedisService redisService;

    @BeforeEach
    void shutDown() {
        redisService.setValues(KEY, VALUE, DURATION);
    }
    @AfterEach
    void tearDown() {
        redisService.deleteValues(KEY);
    }
    @Test
    @DisplayName("Redis에 데디터를 저장하면 정상적으로 조회")
    void saveAndFindTest() {
        //when
        String findValue = redisService.getValues(KEY);
        //then
        assertThat(VALUE).isEqualTo(findValue);
    }
    @Test
    @DisplayName("Redis에 저장한 데이터를 수정")
    void updateTest() {
        //given
        String updateValue = "updateValue";
        redisService.setValues(KEY, updateValue, DURATION);

        //when
        String findValue = redisService.getValues(KEY);

        //then
        assertThat(updateValue).isEqualTo(findValue);
        assertThat(VALUE).isNotEqualTo(findValue);
    }
    @Test
    @DisplayName("Redis에 저장된 데이터를 삭제")
    void deleteTest() {
        //when
        redisService.deleteValues(KEY);
        String findValue = redisService.getValues(KEY);

        //then
        assertThat(findValue).isEqualTo("false");
    }

    @Test
    @DisplayName("Redis에 저장된 데이터는 만료시간이 지나면 삭제")
    void expiredTest() {
        //when
        String findValue = redisService.getValues(KEY);
        await().pollDelay(Duration.ofMillis(6000)).untilAsserted(
                () -> {
                    String expiredValue = redisService.getValues(KEY);
                    assertThat(expiredValue).isNotEqualTo(findValue);
                    assertThat(expiredValue).isEqualTo("false");
                }
        );
    }
}
