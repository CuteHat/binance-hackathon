package com.example.betelgeuseapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InfuraUploadResponse {
    @JsonProperty("Name")
    private String Name;
    @JsonProperty("Hash")
    private String Hash;
    @JsonProperty("Size")
    private String Size;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getHash() {
        return Hash;
    }

    public void setHash(String hash) {
        Hash = hash;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    @Override
    public String toString() {
        return "InfuraFileUploadResponse{" +
                "Name='" + Name + '\'' +
                ", Hash='" + Hash + '\'' +
                ", Size='" + Size + '\'' +
                '}';
    }
}
