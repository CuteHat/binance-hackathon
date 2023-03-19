package com.example.betelgeuseapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.math.BigInteger;

@Table(name = "token_criteria_completions")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class TokenCriteriaCompletionEntiy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigInteger tokenId;

    @ManyToOne
    @JoinColumn(name = "service_provider_id")
    private ServiceProviderEntity serviceProvider;

    @ManyToOne
    @JoinColumn(name = "criteria_id")
    private ServiceProviderCriteriaEntity criteria;

    private BigInteger added;

    private BigInteger totalValue;

    private String txHash;
}
