package com.example.bigdataboost.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Player implements Serializable {
    private String signature;
    private String lastName;
    private String firstName;
    private String position;
    private int birthYear;
    private int debutYear;

    public String toString() {
        return "PLAYER:ID=" + signature + ",Last Name=" + lastName +
                ",First Name=" + firstName + ",Position=" + position +
                ",Birth Year=" + birthYear + ",DebutYear=" +
                debutYear;
    }
}
