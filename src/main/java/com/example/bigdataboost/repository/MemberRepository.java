package com.example.bigdataboost.repository;

import com.example.bigdataboost.model.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends CrudRepository<Member, String> {
    Optional<Member> findByMemberId(String username);
}
