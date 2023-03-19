package com.example.betelgeuseapi.model;

import com.example.betelgeuseapi.entity.ServiceProviderCriteriaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaResponse {
    private Long id;
    private BigInteger criteriaIdentifier;
    private String name;
    private String description;

    private BigInteger weight;

    private BigInteger min;

    private BigInteger max;

    public static CriteriaResponse transform(ServiceProviderCriteriaEntity entity) {
        return new CriteriaResponse(entity.getId(),
                entity.getCriteriaIdentifier(),
                entity.getName(),
                entity.getDescription(),
                entity.getWeight(),
                entity.getMin(),
                entity.getMax());
    }

}
