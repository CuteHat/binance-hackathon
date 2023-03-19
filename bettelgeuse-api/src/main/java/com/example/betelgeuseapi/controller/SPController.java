package com.example.betelgeuseapi.controller;

import com.example.betelgeuseapi.model.CriteriaSuggestion;
import com.example.betelgeuseapi.model.ServiceProviderResponse;
import com.example.betelgeuseapi.service.facade.ChatGPTServiceFacade;
import com.example.betelgeuseapi.service.facade.SPServiceFacade;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/service-provider"})
@CrossOrigin("*")
public class    SPController {
    private final SPServiceFacade spServiceFacade;
    private final ChatGPTServiceFacade chatGPTServiceFacade;

    @GetMapping
    @Operation(summary = "Get all service providers", description = "Returns a list of all available service providers.")
    public List<ServiceProviderResponse> getAll() {
        return spServiceFacade.getSPs();
    }

    @GetMapping("/address/{address}")
    @Operation(summary = "Get service providers by address", description = "Returns a list of service providers with the specified address.")
    public List<ServiceProviderResponse> getByAddress(@PathVariable(name = "address") String address) {
        return spServiceFacade.getSpsByAddress(address);
    }

    @GetMapping("/recommend/criteria/{name}/{description}")
    @Operation(summary = "Get recommended criteria", description = "Returns a list of recommended criteria based on the specified company name and description.")
    public List<CriteriaSuggestion> recommendCriterias(@PathVariable(name = "name") String name, @PathVariable(name = "description") String description) {
        return chatGPTServiceFacade.recommendCriteria(name, description);
    }


}
