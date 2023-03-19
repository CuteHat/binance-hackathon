package com.example.betelgeuseapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Metadata {
    private String imageUrl;
    private Integer rank;
    private String rankName;
    private BigDecimal xp;
}
