package com.example.betelgeuseapi.repository;

import com.example.betelgeuseapi.entity.NftEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;


public interface NftRepository extends JpaRepository<NftEntity, Long> {
    NftEntity getByTokenId(BigInteger tokenId);

    List<NftEntity> getAllByAddress(String userAddress);
}
