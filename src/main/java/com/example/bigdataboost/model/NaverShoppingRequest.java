package com.example.bigdataboost.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class NaverShoppingRequest {
    private String timeUnit = "date";
    private String category;
}
