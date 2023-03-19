package com.example.betelgeuseapi.service;

import com.example.betelgeuseapi.entity.ServiceProviderCriteriaEntity;
import com.example.betelgeuseapi.entity.ServiceProviderEntity;
import com.example.betelgeuseapi.entity.ServiceProviderRankEntity;
import com.example.betelgeuseapi.model.contract.BetelgeuseController;
import com.example.betelgeuseapi.repository.ServiceProviderCriteriaRepository;
import com.example.betelgeuseapi.repository.ServiceProviderRankRepository;
import com.example.betelgeuseapi.repository.ServiceProviderRepository;
import com.example.betelgeuseapi.util.HandledExceptionFactory;
import com.example.betelgeuseapi.util.TimestampUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SPService {
    private final ServiceProviderRepository serviceProviderRepository;
    private final ServiceProviderRankRepository serviceProviderRankRepository;
    private final ServiceProviderCriteriaRepository serviceProviderCriteriaRepository;


    public ServiceProviderEntity register(BetelgeuseController.ServiceProvider serviceProvider,
                                          List<BetelgeuseController.Rank> ranks,
                                          List<BetelgeuseController.Criteria> criterias) {
        Optional<ServiceProviderEntity> sp = serviceProviderRepository.findByContractIdentifier(serviceProvider.id);
        if (sp.isPresent())
            return sp.get();
        List<ServiceProviderRankEntity> rankEntities = registerRanks(ranks);
        List<ServiceProviderCriteriaEntity> criteriaEntities = registerCriterias(criterias);
        return register(serviceProvider.id, serviceProvider.name, serviceProvider.description, serviceProvider.owner, serviceProvider.websiteURL, TimestampUtils.formatBlockTime(serviceProvider.registeredTimestamp), serviceProvider.isActive, rankEntities, criteriaEntities);
    }

    private List<ServiceProviderRankEntity> registerRanks(List<BetelgeuseController.Rank> ranks) {
        List<ServiceProviderRankEntity> res = new ArrayList<>();
        for (int i = 0; i < ranks.size(); i++) {
            ServiceProviderRankEntity entity = new ServiceProviderRankEntity();
            entity.setRankIdentifier(ranks.get(i).id);
            entity.setLevel(ranks.get(i).level);
            entity.setXpFrom(ranks.get(i).xpFrom);
            entity.setName(ranks.get(i).name);
            res.add(entity);
        }
        return serviceProviderRankRepository.saveAll(res);
    }


    private List<ServiceProviderCriteriaEntity> registerCriterias(List<BetelgeuseController.Criteria> criterias) {
        List<ServiceProviderCriteriaEntity> res = new ArrayList<>();
        for (BetelgeuseController.Criteria criteria : criterias) {
            ServiceProviderCriteriaEntity entity = new ServiceProviderCriteriaEntity();
            entity.setCriteriaIdentifier(criteria.id);
            entity.setMin(criteria.min.divide(BigInteger.TEN.pow(18)));
            entity.setMax(criteria.max.divide(BigInteger.TEN.pow(18)));
            entity.setWeight(criteria.weight);
            entity.setDescription(criteria.description);
            entity.setName(criteria.name);
            res.add(entity);
        }
        return serviceProviderCriteriaRepository.saveAll(res);
    }

    /**
     * Registers a new service provider in the database, if it does not exist already.
     *
     * @param contractIdentifier  contract identifier of the service provider
     * @param name                name of the service provider
     * @param description         description of the service provider
     * @param owner               owner of the service provider
     * @param websiteURL          website URL of the service provider
     * @param registeredTimestamp registered timestamp of the service provider
     * @param isActive            is the service provider active
     * @param rankEntities
     * @param criteriaEntities
     * @return the service provider entity
     */
    public ServiceProviderEntity register(BigInteger contractIdentifier, String name, String description, String owner, String websiteURL, Timestamp registeredTimestamp, boolean isActive, List<ServiceProviderRankEntity> rankEntities, List<ServiceProviderCriteriaEntity> criteriaEntities) {
        Optional<ServiceProviderEntity> serviceProvider = serviceProviderRepository.findByContractIdentifier(contractIdentifier);
        if (serviceProvider.isPresent())
            return serviceProvider.get();

        ServiceProviderEntity entity = new ServiceProviderEntity();
        entity.setContractIdentifier(contractIdentifier);
        entity.setName(name);
        entity.setOwner(owner);
        entity.setDescription(description);
        entity.setWebsiteURL(websiteURL);
        entity.setRegisteredTimestamp(registeredTimestamp);
        entity.setActive(isActive);
        entity.setRanks(new HashSet<>(rankEntities));
        entity.setCriterias(new HashSet<>(criteriaEntities));
        return serviceProviderRepository.save(entity);
    }

    public ServiceProviderEntity deactivate(BigInteger contractIdentifier) {
        ServiceProviderEntity entity = serviceProviderRepository.findByContractIdentifier(contractIdentifier)
                .orElseThrow(() -> {
                    log.error("Service Provider with contract identifier:{} does not exist", contractIdentifier);
                    return HandledExceptionFactory.getHandledException("Service Provider with given contract identifier does not exist");
                });
        entity.setActive(false);
        return serviceProviderRepository.save(entity);
    }

    public ServiceProviderEntity activate(BigInteger contractIdentifier) {
        ServiceProviderEntity entity = serviceProviderRepository.findByContractIdentifier(contractIdentifier)
                .orElseThrow(() -> {
                    log.error("Service Provider with contract identifier:{} does not exist", contractIdentifier);
                    return HandledExceptionFactory.getHandledException("Service Provider with given contract identifier does not exist");
                });
        entity.setActive(true);
        return serviceProviderRepository.save(entity);
    }

    public List<ServiceProviderEntity> getAll(Sort sort) {
        return serviceProviderRepository.findAll(sort);
    }

    public List<ServiceProviderEntity> getByAddress(String address, Sort sort) {
        return serviceProviderRepository.findAllByOwner(address, sort);
    }

    public ServiceProviderEntity getBySpIndetifier(BigInteger serviceProviderId) {
        return serviceProviderRepository.getByContractIdentifier(serviceProviderId);
    }
}
