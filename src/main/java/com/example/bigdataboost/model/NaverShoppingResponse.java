package com.example.bigdataboost.model;

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
    private String id;
    private String startDate;
    private String endDate;
    private String timeUnit;
    @Builder.Default
    private List<ShoppingResult> results = new ArrayList<>();

    @Override
    public String toString() {
        return "NaverShoppingResponse{" +
                "id='" + id + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", timeUnit='" + timeUnit + '\'' +
                ", results=" + results +
                '}';
    }
}
