package com.example.betelgeuseapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.math.BigInteger;

@Table(name = "criterias")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class ServiceProviderCriteriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private BigInteger criteriaIdentifier;

    private String name;

    private String description;

    private BigInteger weight;

    private BigInteger min;

    private BigInteger max;

}
