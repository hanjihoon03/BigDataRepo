package com.example.bigdataboost.service;

import com.example.bigdataboost.model.UserEntity;
import com.example.bigdataboost.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Cacheable(value = "userCache", key = "#id")
    public Optional<UserEntity> getUserById(String id) {
        return userRepository.findById(id);
    }

    @CacheEvict(value = "userCache", key = "#user.id")
    public UserEntity saveUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
    @CacheEvict(value = "userCache", key = "#id")
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

}
