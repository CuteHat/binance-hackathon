package com.example.betelgeuseapi.service.facade;

import com.example.betelgeuseapi.model.CriteriaSuggestion;
import com.example.betelgeuseapi.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatGPTServiceFacade {
    private final ChatGPTService chatGPTService;

    /**
     * Generates a list of suggested criteria with their corresponding values for a given service provider name and description.
     * Uses OpenAI's GPT model to generate the suggestions.
     *
     * @param SPName        the name of the service provider
     * @param SPDescription the description of the service provider
     * @return a list of {@link CriteriaSuggestion} objects representing the suggested criteria and their values
     */
    public List<CriteriaSuggestion> recommendCriteria(String SPName, String SPDescription) {
        return chatGPTService.suggestCriteriaValues(SPName, SPDescription)
                .getChoices()
                .stream()
                .map(choice -> CriteriaSuggestion.fromJson(choice.getText().substring(choice.getText().indexOf("{"))))
                .collect(Collectors.toList());
    }
}
