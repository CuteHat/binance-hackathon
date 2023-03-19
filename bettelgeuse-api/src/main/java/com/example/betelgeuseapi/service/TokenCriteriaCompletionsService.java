package com.example.betelgeuseapi.service;

import com.example.betelgeuseapi.entity.NftEntity;
import com.example.betelgeuseapi.entity.ServiceProviderCriteriaEntity;
import com.example.betelgeuseapi.entity.ServiceProviderEntity;
import com.example.betelgeuseapi.entity.TokenCriteriaCompletionEntiy;
import com.example.betelgeuseapi.model.contract.BetelgeuseController;
import com.example.betelgeuseapi.repository.NftRepository;
import com.example.betelgeuseapi.repository.ServiceProviderRepository;
import com.example.betelgeuseapi.repository.TokenCriteriaCompletionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TokenCriteriaCompletionsService {
    private final TokenCriteriaCompletionRepository repository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final NftRepository nftRepository;

    public void registerCompletion(BetelgeuseController.XpPushedEventResponse event) {
        BigInteger tokenId = event.tokenId;
        ServiceProviderEntity serviceProvider = serviceProviderRepository.getByContractIdentifier(event.serviceProviderId);

        Set<ServiceProviderCriteriaEntity> criterias = serviceProvider.getCriterias();
        ServiceProviderCriteriaEntity criteria = null;
        for (ServiceProviderCriteriaEntity serviceProviderCriteriaEntity : criterias) {
            if (serviceProviderCriteriaEntity.getCriteriaIdentifier().equals(event.criteriaId)) {
                criteria = serviceProviderCriteriaEntity;
                break;
            }
        }

        TokenCriteriaCompletionEntiy tokenCriteriaCompletionEntiy = new TokenCriteriaCompletionEntiy();
        tokenCriteriaCompletionEntiy.setCriteria(criteria);
        tokenCriteriaCompletionEntiy.setTotalValue(event.totalValue.divide(BigInteger.TEN.pow(18)));

        tokenCriteriaCompletionEntiy.setAdded(event.added.divide(BigInteger.TEN.pow(18)));
        tokenCriteriaCompletionEntiy.setServiceProvider(serviceProvider);
        tokenCriteriaCompletionEntiy.setTokenId(tokenId);

        repository.save(tokenCriteriaCompletionEntiy);

        NftEntity nft = nftRepository.getByTokenId(tokenId);
        nft.setXp(event.totalValue.divide(BigInteger.TEN.pow(18)));
        nftRepository.save(nft);
    }
}
