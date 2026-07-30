package com.hamada.walletservice.repository;

import com.hamada.walletservice.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Long> {
    Wallet findWalletByUserId(Long userId);

}
