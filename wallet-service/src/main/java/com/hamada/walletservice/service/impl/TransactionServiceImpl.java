package com.hamada.walletservice.service.impl;

import com.hamada.walletservice.entity.Transaction;
import com.hamada.walletservice.entity.Wallet;
import com.hamada.walletservice.repository.TransactionRepository;
import com.hamada.walletservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getTransactionsByWallet(Long id) {
        return transactionRepository.findByWalletId(id);
    }

//    @Override
//    public List<Transaction> getTransactionsByUser(Long userId) {
//        return transactionRepository.findByUserId(userId);
//    }


}
