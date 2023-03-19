package com.example.betelgeuseapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenAIChoice {
    private String text;
    private double confidence;
    private String index;
}
