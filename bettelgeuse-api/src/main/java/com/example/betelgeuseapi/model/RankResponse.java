package com.example.betelgeuseapi.model;

import com.example.betelgeuseapi.entity.ServiceProviderRankEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankResponse {
    private Long id;
    private BigInteger rankIdentifier;
    private BigInteger level;
    private BigInteger xpFrom;
    private String name;

    public static RankResponse transform(ServiceProviderRankEntity entity) {
        return new RankResponse(
                entity.getId(),
                entity.getRankIdentifier(),
                entity.getLevel(),
                entity.getXpFrom(),
                entity.getName()
        );
    }
}
