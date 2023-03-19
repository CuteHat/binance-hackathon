package com.example.betelgeuseapi.service;

import com.example.betelgeuseapi.entity.NftEntity;
import com.example.betelgeuseapi.repository.NftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class NFTService {
    private final NftRepository nftRepository;
    private final SPService spService;


    public void register(BigInteger tokenId, BigInteger serviceProviderId, String userAddress, BigInteger rank, String uri) {
        if (nftRepository.getByTokenId(tokenId) == null) {
            NftEntity NftEntity = new NftEntity();
            NftEntity.setServiceProvider(spService.getBySpIndetifier(serviceProviderId));
            NftEntity.setTokenId(tokenId);
            NftEntity.setAddress(userAddress);
            NftEntity.setUrl(uri);
            NftEntity.setRank(rank);
            nftRepository.save(NftEntity);
        }
    }
}
