package com.example.bigdataboost.repository;

import com.example.bigdataboost.model.PlayerYears;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<PlayerYears, Long> {
}
