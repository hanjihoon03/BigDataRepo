package com.example.bigdataboost.model.submodel;

public enum Category {
    FEMALE_CLOTHING("50000167"),
    MALE_CLOTHING("50000169"),
    LAPTOP("50000151"),
    DESKTOP("50000089"),
    MONITOR("50000153"),
    PERIPHERAL("50000090"),
    STORAGE_DEVICE("50000096"),
    SOFT_WARE("50000099");
    // 필요한 카테고리를 추가

    private final String code;

    Category(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
