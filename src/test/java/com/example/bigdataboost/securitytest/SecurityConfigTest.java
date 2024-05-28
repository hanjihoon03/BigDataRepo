package com.example.bigdataboost.securitytest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenAccessApiWithoutAuth_thenOk() throws Exception {
        // 인증 없이 /api/test 엔드포인트에 접근할 때 200 OK 상태를 검증.
        mockMvc.perform(get("/api/test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void whenAccessSecureWithoutAuth_thenUnauthorized() throws Exception {
        // 인증 없이 /api/secure 엔드포인트에 접근할 때 401 Unauthorized 상태를 검증.
        mockMvc.perform(get("/secure")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
    @Test
    void whenAccessSecureWithAuth_thenOk() throws Exception {
        // HTTP Basic 인증을 사용하여 /api/secure 엔드포인트에 접근할 때 200 OK 상태를 검증.
        mockMvc.perform(get("/api/secure")
                .with(httpBasic("user", "password"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void whenAccessSecureWithMockUser_thenOk() throws Exception {
        // @WithMockUser 어노테이션을 사용하여 모의 사용자를 설정하고 /api/secure 엔드포인트에 접근할 때 200 OK 상태를 검증.
        mockMvc.perform(get("/api/secure")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
