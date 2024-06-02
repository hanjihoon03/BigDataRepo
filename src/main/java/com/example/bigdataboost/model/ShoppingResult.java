package com.example.bigdataboost.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingResult {
    private String title;
    private List<String> category;
    private List<ShoppingData> data = new ArrayList<>();

}
