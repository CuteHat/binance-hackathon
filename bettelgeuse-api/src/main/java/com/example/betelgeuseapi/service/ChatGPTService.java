package com.example.betelgeuseapi.service;

import com.example.betelgeuseapi.model.OpenAIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatGPTService {
    @Value("${openai.token}")
    private String openAiToken;
    private String completionsUrl = "https://api.openai.com/v1/completions";
    private final RestTemplateBuilder restTemplateBuilder;

    /**
     * Generates suggested values for criteria in a Web3 reputation system based on
     * <p>
     * the given company name and description.
     *
     * @param companyName        The name of the company to generate criteria values for.
     * @param companyDescription The description of the company to generate criteria values for.
     * @return An {@link OpenAIResponse} object containing the suggested criteria values.
     */
    public OpenAIResponse suggestCriteriaValues(String companyName, String companyDescription) {
        String prompt = generateCriteriaValuesPrompt(companyName, companyDescription);

        ResponseEntity<OpenAIResponse> responseEntity = requestCompletion(prompt);
        log.info("responde:{}, ", responseEntity.getBody());
        return responseEntity.getBody();
    }

    /**
     * Sends a request to the OpenAI completions endpoint with the given prompt.
     *
     * @param prompt The prompt to send to the OpenAI completions endpoint.
     * @return A {@link ResponseEntity} containing an {@link OpenAIResponse} object
     * with the response from the OpenAI API.
     */
    private ResponseEntity<OpenAIResponse> requestCompletion(String prompt) {
        RestTemplate restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(openAiToken);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "text-davinci-003");
        requestBody.put("prompt", prompt);
        requestBody.put("temperature", 1);
        requestBody.put("max_tokens", 100);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.postForEntity(completionsUrl, requestEntity, OpenAIResponse.class);
    }

    /**
     * Generates a prompt for the OpenAI API with the given company name and description.
     *
     * @param companyName        The name of the company to generate a prompt for.
     * @param companyDescription The description of the company to generate a prompt for.
     * @return The generated prompt string.
     */
    private String generateCriteriaValuesPrompt(String companyName, String companyDescription) {
        return String.format("Given a Web3 Reputation System for company:%s with description:%s, we have criteria object with minValue, maxValue, and weight fields. The formula to calculate reputation score is as follows:\\n\\nReputation Score = (value - minValue) / (maxValue - minValue) * weight\\n\\nwhere 'value' is a user's score for a specific criterion, ranging from minValue to maxValue.\\n\\nPlease suggest suitable 1 criteria with values, use json format for response with keys criteriaName,criteriaDecsription,minValue,maxValue,weight", companyName, companyDescription);
    }

}
