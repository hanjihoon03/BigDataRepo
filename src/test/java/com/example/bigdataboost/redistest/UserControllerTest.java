package com.example.bigdataboost.redistest;

import com.example.bigdataboost.controller.UserController;
import com.example.bigdataboost.model.User;
import com.example.bigdataboost.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testGetUser() throws Exception {
        //given
        //사용자 서비스가 특정 ID를 가진 사용자를 반환하도록 설정
        User user = new User("1", "Test", 10);
        when(userService.getUserById("1")).thenReturn(Optional.of(user));
        //when
        //GET 요청을 /api/1 에 보내고 결과를 검증
        mockMvc.perform(get("/api/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.age").value(10));
        //then
        //getUserById 메소드가 정확히 한 번 호출되었는지 확인
        verify(userService, times(1)).getUserById("1");
    }
    @Test
    void testCreateUser() throws Exception {
        //given
        //사용자 서비스가 사용자를 저장하고 반환하도록 설정
        User user = new User("1", "Test", 10);
        when(userService.saveUser(any(User.class))).thenReturn(user);
        //when
        //POST 요청을 /api 에 JSON 본문과 함께 보내고 결과를 검증
        mockMvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"name\":\"Test\",\"age\":10}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.age").value(10));
        //then
        //saveUser 메소드가 정확히 한 번 호출되었는지 확인
        verify(userService, times(1)).saveUser(any(User.class));
    }
    @Test
    void testDeleteUser() throws Exception {
        //given
        //사용자 서비스가 특정 ID의 사용자 삭제를 처리하도록 설정
        doNothing().when(userService).deleteUser("1");
        //when
        //DELETE 요청을 /api/1 에 보내고 결과를 검증
        mockMvc.perform(delete("/api/1"))
                .andExpect(status().isNoContent());
        //then
        //deleteUser 메소드가 정확히 한 번 호출되었는지 확인
        verify(userService, times(1)).deleteUser("1");
    }
}
