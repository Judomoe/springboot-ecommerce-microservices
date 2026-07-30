package com.hamada.walletservice.service.impl;

import com.hamada.walletservice.entity.Wallet;
import com.hamada.walletservice.repository.WalletRepository;
import com.hamada.walletservice.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Wallet getAllWallets(Long userId) {
        return walletRepository.findWalletByUserId(userId);
    }
}
