package com.hamada.walletservice.controller;

import com.hamada.walletservice.entity.Wallet;
import com.hamada.walletservice.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wallets")
public class WalletController {
    @Autowired
    private WalletService walletService;
    @GetMapping("/users/{userId}")
    public Wallet getAllWallets(@PathVariable Long userId){
        return walletService.getAllWallets(userId);
    }
}
