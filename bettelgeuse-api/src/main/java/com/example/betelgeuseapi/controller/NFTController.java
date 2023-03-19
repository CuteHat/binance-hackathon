package com.example.betelgeuseapi.controller;

import com.example.betelgeuseapi.model.InfuraUploadResponse;
import com.example.betelgeuseapi.service.facade.NFTServiceFacade;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/nft/metadata"})
@CrossOrigin("*")
public class NFTController {
    private final NFTServiceFacade nftServiceFacade;

    @PostMapping("/{tokenId}/{level}/{rankName}/{xp}")
    @Operation(summary = "Create NFT metadata")
    public InfuraUploadResponse create(@PathVariable(name="tokenId") int tokenId,
                                       @PathVariable(name="level") int level,
                                       @PathVariable(name="rankName") String rankName,
                                       @PathVariable(name="xp") int xp) {
        return nftServiceFacade.createMetadata(tokenId,level, rankName, xp);
    }

    @PostMapping
    @Operation(summary = "Create NFT metadata")
    public InfuraUploadResponse createFirst() {
        return nftServiceFacade.createMetadata(0,0,"0",0);
    }


}
