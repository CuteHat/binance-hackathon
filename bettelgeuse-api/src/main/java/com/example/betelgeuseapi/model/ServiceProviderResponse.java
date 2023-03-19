package com.example.betelgeuseapi.model;

import com.example.betelgeuseapi.entity.ServiceProviderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceProviderResponse {
    private Long id;
    private BigInteger contractIdentifier;
    private String name;
    private String owner;
    private String description;
    private String websiteURL;
    private boolean isActive;
    private List<CriteriaResponse> criterias;
    private List<RankResponse> ranks;

    public static ServiceProviderResponse transform(ServiceProviderEntity entity) {
        return new ServiceProviderResponse(
                entity.getId(),
                entity.getContractIdentifier(),
                entity.getName(),
                entity.getOwner(),
                entity.getDescription(),
                entity.getWebsiteURL(),
                entity.isActive(),
                entity.getCriterias().stream().map(CriteriaResponse::transform).collect(Collectors.toList()),
                entity.getRanks().stream().map(RankResponse::transform).collect(Collectors.toList())
        );
    }
}
