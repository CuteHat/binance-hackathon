package com.example.betelgeuseapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

@Table(name = "nfts")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NftEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private ServiceProviderEntity serviceProvider;
    private String address;
    private BigInteger tokenId;
    private BigInteger xp;
    private String url;
    private BigInteger rank;
    private Boolean claimable;
}
