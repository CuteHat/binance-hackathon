package com.example.betelgeuseapi.controller;

import com.example.betelgeuseapi.model.NftListItemResponse;
import com.example.betelgeuseapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/user/{address}"})
@CrossOrigin("*")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get all NFT items for a user")
    @GetMapping("/nft")
    public List<NftListItemResponse> getNftItems(@PathVariable String address) {
        return userService.getUserNfts(address.toLowerCase());
    }
}
