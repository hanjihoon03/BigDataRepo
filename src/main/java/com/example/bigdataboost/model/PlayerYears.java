package com.example.bigdataboost.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data // 데이터 가공 후 새로운 csv 파일에 담을 객체
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PlayerYears implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String signature;
    private String lastName;
    private String firstName;
    private String position;
    private int birthYear;
    private int debutYear;

    private int yearsExperience;

    public PlayerYears(Player item) {
        this.signature = item.getSignature();
        this.lastName = item.getLastName();
        this.firstName = item.getFirstName();
        this.position = item.getPosition();
        this.birthYear = item.getBirthYear();
        this.debutYear = item.getDebutYear();
        this.yearsExperience = LocalDateTime.now().getYear() - item.getDebutYear();
    }


    public String toString() {
        return "PLAYER:ID=" + signature + ",Last Name=" + lastName +
                ",First Name=" + firstName + ",Position=" + position +
                ",Birth Year=" + birthYear + ",DebutYear=" +
                debutYear;
    }
}
