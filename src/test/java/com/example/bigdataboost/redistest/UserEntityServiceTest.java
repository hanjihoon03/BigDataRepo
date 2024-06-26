package com.example.bigdataboost.redistest;

import com.example.bigdataboost.model.UserEntity;
import com.example.bigdataboost.repository.UserRepository;
import com.example.bigdataboost.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//redisUserServiceTest
public class UserEntityServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        //mockito 초기화
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetUserById() {
        //given
        UserEntity userEntity = new UserEntity("1", "Test", 10);
        //when
        //UserRepository의 findById 메서드가 호출되었을 때, 해당 User 객체를 반환하도록 설정
        when(userRepository.findById("1")).thenReturn(Optional.of(userEntity));

        Optional<UserEntity> foundUser = userService.getUserById("1");

        //then
        assertTrue(foundUser.isPresent());
        assertEquals("Test",foundUser.get().getName());
        //UserRepository의 findById 메서드가 한 번 호출되었는지 확인
        verify(userRepository, times(1)).findById("1");
    }
    @Test
    public void testSaveUser() {
        //given
        UserEntity userEntity = new UserEntity("1", "Test", 10);
        //when
        //UserRepository의 save 메서드가 호출되었을 때, 해당 User 객체를 반환하도록 설정
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserEntity savedUserEntity = userService.saveUser(userEntity);

        //then
        assertEquals("Test", savedUserEntity.getName());
        //UserRepository의 save 메서드가 한 번 호출되었는지 확인
        verify(userRepository, times(1)).save(userEntity);
    }
    @Test
    public void testDeleteUser() {
        //when
        //UserRepository의 deleteById 메서드가 호출될 때 아무 작업도 하지 않도록 설정
        doNothing().when(userRepository).deleteById("1");

        userService.deleteUser("1");

        //then
        //UserRepository의 deleteById 메서드가 한 번 호출되었는지 확인
        verify(userRepository, times(1)).deleteById("1");
    }
}
