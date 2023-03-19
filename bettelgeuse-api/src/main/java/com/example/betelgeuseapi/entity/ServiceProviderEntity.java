package com.example.betelgeuseapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Table(name = "service_providers")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class ServiceProviderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private BigInteger contractIdentifier;
    private String name;
    private String owner;
    private String description;
    private String websiteURL;
    private Timestamp registeredTimestamp;
    private boolean isActive;

    @OneToMany(fetch=FetchType.EAGER)
    private Set<ServiceProviderRankEntity> ranks;

    @OneToMany(fetch=FetchType.EAGER)
    private Set<ServiceProviderCriteriaEntity> criterias;

}
