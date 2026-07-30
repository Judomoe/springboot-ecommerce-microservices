package com.hamada.walletservice.repository;

import com.hamada.walletservice.entity.Transaction;
import com.hamada.walletservice.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByWalletId(Long id);
}
