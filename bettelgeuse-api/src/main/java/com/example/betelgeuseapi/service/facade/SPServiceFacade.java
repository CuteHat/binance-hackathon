package com.example.betelgeuseapi.service.facade;

import com.example.betelgeuseapi.entity.ServiceProviderEntity;
import com.example.betelgeuseapi.model.ServiceProviderResponse;
import com.example.betelgeuseapi.service.SPService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SPServiceFacade {
    private final SPService spService;

    /**
     * Retrieve all Service Providers from the database and return them as a list of ServiceProviderResponse
     * objects sorted by descending registered timestamp.
     *
     * @return a list of ServiceProviderResponse objects representing all Service Providers in the database.
     */
    public List<ServiceProviderResponse> getSPs() {
        Sort sort = Sort.by(Sort.Direction.DESC, ServiceProviderEntity.Fields.registeredTimestamp);
        return spService.getAll(sort).stream().map(ServiceProviderResponse::transform).collect(Collectors.toList());
    }

    /**
     * Retrieve all Service Providers associated with the specified Ethereum address and return them as a list
     * of ServiceProviderResponse objects sorted by descending registered timestamp.
     *
     * @param address the Ethereum address associated with the Service Providers to retrieve.
     * @return a list of ServiceProviderResponse objects representing all Service Providers associated with the
     * specified Ethereum address.
     */
    public List<ServiceProviderResponse> getSpsByAddress(String address) {
        Sort sort = Sort.by(Sort.Direction.DESC, ServiceProviderEntity.Fields.registeredTimestamp);
        return spService.getByAddress(address, sort).stream().map(ServiceProviderResponse::transform).collect(Collectors.toList());
    }
}
