package com.example.betelgeuseapi.repository;

import com.example.betelgeuseapi.entity.ServiceProviderEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface ServiceProviderRepository extends JpaRepository<ServiceProviderEntity, Long> {
    Optional<ServiceProviderEntity> findByContractIdentifier(BigInteger contractIdentifier);

    List<ServiceProviderEntity> findAllByOwner(String address, Sort sort);

    ServiceProviderEntity getByContractIdentifier(BigInteger serviceProviderId);
}
