package com.example.betelgeuseapi.repository;

import com.example.betelgeuseapi.entity.ServiceProviderCriteriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceProviderCriteriaRepository extends JpaRepository<ServiceProviderCriteriaEntity, Long> {
}
