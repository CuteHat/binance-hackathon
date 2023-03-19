package com.example.betelgeuseapi.service;

import com.example.betelgeuseapi.entity.NftEntity;
import com.example.betelgeuseapi.model.NftListItemResponse;
import com.example.betelgeuseapi.model.RankResponse;
import com.example.betelgeuseapi.repository.NftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final NftRepository nftRepository;

    public List<NftListItemResponse> getUserNfts(String address) {
        List<NftEntity> userNfts = nftRepository.getAllByAddress(address);

        List<NftListItemResponse> response = userNfts.stream().map(NftListItemResponse::transform).collect(Collectors.toList());

        NftListItemResponse nftListItemResponse;
        Optional<RankResponse> optionalRank;
        for (int i = 0; i < response.size(); i++) {
            nftListItemResponse = response.get(i);
            optionalRank = getRankName(response.get(i).getRankLevel(), nftListItemResponse.getServiceProvider().getRanks());
            nftListItemResponse.setRankName(optionalRank.map(RankResponse::getName).orElse("Not ranked"));
        }
        return response;
    }

    public NftEntity getById(Long tokenId){
        return nftRepository.getByTokenId(BigInteger.valueOf(tokenId));
    }


    private Optional<RankResponse> getRankName(BigInteger rankLevel, List<RankResponse> ranks) {
        return ranks.stream().filter(rank -> rank.getLevel().equals(rankLevel)).findFirst();
    }

}
