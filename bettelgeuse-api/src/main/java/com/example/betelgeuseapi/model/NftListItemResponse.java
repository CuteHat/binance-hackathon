package com.example.betelgeuseapi.model;

import com.example.betelgeuseapi.entity.NftEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Builder
@Data
public class NftListItemResponse {
    private BigInteger tokenId;
    private BigInteger rankLevel;
    private ServiceProviderResponse serviceProvider;
    private BigInteger xp;
    // calculated values
    private String rankName;
    private Boolean rankClaimable;
    private Boolean claimable;

    public static NftListItemResponse transform(NftEntity entity) {
        return NftListItemResponse.builder()
                .tokenId(entity.getTokenId())
                .rankLevel(entity.getRank())
                .serviceProvider(ServiceProviderResponse.transform(entity.getServiceProvider()))
                .xp(entity.getXp())
                .claimable(entity.getClaimable()) //for demo purposes
                .build();
    }
}
