package com.example.bigdataboost.redistest;

import com.example.bigdataboost.model.User;
import com.example.bigdataboost.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api";
        userRepository.deleteAll(); // 테스트 전 데이터 초기화
    }

    @Test
    void testGetUser() {
        // given
        User user = new User("1", "Test", 10);
        userRepository.save(user);

        // when
        ResponseEntity<User> response = restTemplate.getForEntity(baseUrl + "/1", User.class);

        // then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getId()).isEqualTo("1");
        assertThat(response.getBody().getName()).isEqualTo("Test");
        assertThat(response.getBody().getAge()).isEqualTo(10);
    }

    @Test
    void testCreateUser() {
        // given
        User user = new User("1", "Test", 10);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(user, headers);

        // when
        ResponseEntity<User> response = restTemplate.postForEntity(baseUrl, request, User.class);

        // then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getId()).isEqualTo("1");
        assertThat(response.getBody().getName()).isEqualTo("Test");
        assertThat(response.getBody().getAge()).isEqualTo(10);
    }

    @Test
    void testDeleteUser() {
        // given
        User user = new User("1", "Test", 10);
        userRepository.save(user);

        // when
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<Void> response = restTemplate.exchange(baseUrl + "/1", HttpMethod.DELETE, request, Void.class);

        // then
        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        assertThat(userRepository.findById("1")).isEmpty();
    }
}
