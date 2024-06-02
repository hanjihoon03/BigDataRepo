package com.example.bigdataboost.repository;

import com.example.bigdataboost.model.NaverShoppingResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NaverRepository extends CrudRepository<NaverShoppingResponse, String> {
    @Query("Naver:*")
    List<NaverShoppingResponse> findAllNaver();
}
