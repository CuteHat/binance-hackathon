package com.example.betelgeuseapi.repository;

import com.example.betelgeuseapi.entity.TokenCriteriaCompletionEntiy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenCriteriaCompletionRepository extends JpaRepository<TokenCriteriaCompletionEntiy, Long> {

}