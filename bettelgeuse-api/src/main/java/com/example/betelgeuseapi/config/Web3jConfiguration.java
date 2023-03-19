package com.example.betelgeuseapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3jConfiguration {

    @Value(value = "${web3j.client-address}")
    private String nodeAddress;

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(nodeAddress));
    }
}
