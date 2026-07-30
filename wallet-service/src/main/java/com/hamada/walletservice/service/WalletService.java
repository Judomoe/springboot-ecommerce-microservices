package com.hamada.walletservice.service;

import com.hamada.walletservice.entity.Wallet;

import java.util.List;

public interface WalletService {
    Wallet getAllWallets(Long userId);
}
