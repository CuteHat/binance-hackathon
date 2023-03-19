package com.example.betelgeuseapi.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriteriaSuggestion {
    private String criteriaName;
    private String criteriaDescription;
    private int minValue;
    private int maxValue;
    private double weight;

    public static CriteriaSuggestion fromJson(String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, CriteriaSuggestion.class);
    }
}
