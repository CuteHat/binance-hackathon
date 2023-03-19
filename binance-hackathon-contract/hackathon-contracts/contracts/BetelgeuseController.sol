// SPDX-License-Identifier: MIT

pragma solidity ^0.8.17;

import "./BetelgeuseRT.sol";
import "hardhat/console.sol";

contract BetelgeuseController {

    struct Rank {
        uint256 id;
        uint256 level;
        uint256 xpFrom;
        string name;
    }

    struct Criteria {
        uint256 id;
        string name;
        string description;
        uint256 weight;
        uint256 min;
        uint256 max;
    }

    struct ServiceProvider {
        uint256 id;
        string name;
        address owner;
        string description;
        string websiteURL;
        uint256 registeredTimestamp;
        bool isActive;
    }

    uint256 public serviceProviderId;
    uint256 public criteriaId;
    uint256 public rankId;
    uint256 public tokenId;


    mapping(uint256 => ServiceProvider) public serviceProviders;
    //providerId, <criteriaId, criteria>
    mapping(uint256 => mapping(uint256 => Criteria)) public criterias;
    mapping(uint256 => mapping(uint256 => Rank)) public ranks;

    //tokenId, rankId
    mapping(uint256 => uint256) public tokenRanks;
    mapping(uint256 => uint256) public xps;
    mapping(uint256 => uint256) public tokensToProvides;
    uint constant multiplier = 10 ** 18;

    BetelgeuseRT public NFT;


    event TokenPurchased(address indexed userAddress, uint256 tokenId, uint256 serviceProviderId, uint256 rank, string uri);
    event ServiceProviderCreated(
        ServiceProvider provider,
        Rank[] ranks,
        Criteria[] criterias
    );
    event XpPushed(uint256 indexed tokenId, uint256 indexed serviceProviderId, uint256 indexed criteriaId, uint256 added,
        uint256 totalValue);
    event TokenLeveledUp(uint256 indexed tokenId, uint256 serviceProviderId, uint256 tokenRank, string uri);


    constructor(address _betelgeuseRTAddress) {
        NFT = BetelgeuseRT(_betelgeuseRTAddress);
        serviceProviderId = 1;
        criteriaId = 1;
        rankId = 1;
        tokenId = 1;
    }


    function registerProvider(string memory _name,
        string memory _description,
        string memory _websiteURL,
        Criteria[] memory _criterias,
        Rank[] memory _ranks) external {

        ServiceProvider memory newProvider = ServiceProvider(
            serviceProviderId,
            _name,
            msg.sender,
            _description,
            _websiteURL,
            block.timestamp,
            true
        );

        require(_ranks.length == 5, "rank size be 5");

        for (uint256 i = 0; i < _ranks.length; i++) {
            require(_ranks[i].level == i + 1, "level error");
            _ranks[i].id = rankId;
            ranks[serviceProviderId][rankId] = _ranks[i];
            rankId = rankId + 1;
        }

        for (uint256 i = 0; i < _criterias.length; i++) {
            _criterias[i].id = criteriaId;
            _criterias[i].min = _criterias[i].min * multiplier;
            _criterias[i].max = _criterias[i].max * multiplier;
            criterias[serviceProviderId][criteriaId] = _criterias[i];
            criteriaId = criteriaId + 1;
        }

        serviceProviders[serviceProviderId] = newProvider;


        serviceProviderId = serviceProviderId + 1;
        emit ServiceProviderCreated(newProvider, _ranks, _criterias);
    }


    function mint(uint256 _serviceProviderId, string memory uri) external {
        NFT.mint(msg.sender, tokenId, uri);
        emit TokenPurchased(msg.sender, tokenId, _serviceProviderId, 0, uri);
        tokensToProvides[tokenId] = _serviceProviderId;
        tokenId = tokenId + 1;
    }

    function pushXp(uint256 _tokenId, uint256 _serviceProviderId, uint256 _criteriaId, uint256 value)
    external {
        require(criterias[_serviceProviderId][_criteriaId].id == _criteriaId, "invalid values passed");
        require(serviceProviders[_serviceProviderId].owner == msg.sender, "invalid caller");
        Criteria memory c = criterias[_serviceProviderId][_criteriaId];
        value = value * multiplier;
        require(c.min <= value && c.max >= value, "value not in bounds");
        uint256 v = (multiplier * (value - c.min)) / (c.max - c.min);
        v = v * c.weight;
        xps[_tokenId] = xps[tokenId] + v;
        emit XpPushed(_tokenId, _serviceProviderId, _criteriaId, v, xps[_tokenId]);
    }

    function levelUp(uint256 _tokenId, uint256 _serviceProviderId, uint256[] memory _serviceProviderRankingIds, string memory uri) public {
        require(tokensToProvides[_serviceProviderId] == _tokenId, "invalid token");
        uint256 tokenCurrXp = xps[_tokenId];
        require(_serviceProviderRankingIds.length == 5, "size not accepted");
        for (uint256 i = _serviceProviderRankingIds.length - 1; i >= 0; i--) {
            Rank memory spRank = ranks[_serviceProviderId][_serviceProviderRankingIds[i]];
            console.log(i, spRank.id);
            require(spRank.id != 0, "invalid rank id");
            if (spRank.xpFrom <= tokenCurrXp) {
                tokenRanks[_tokenId] = spRank.level;
                NFT.setTokenURI(_tokenId, uri);
                emit TokenLeveledUp(_tokenId, _serviceProviderId, tokenRanks[_tokenId], uri);
                return;
            }
        }
    }


}