package com.example.bigdataboost.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash("Naver")
public class NaverShoppingResponse {

    @Id
    @Column(updatable = false, unique = true, nullable = false)
    private String id;
    private String title;
    @Builder.Default
    private List<ShoppingData> data = new ArrayList<>();

}
