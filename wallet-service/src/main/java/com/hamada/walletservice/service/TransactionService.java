package com.hamada.walletservice.service;

import com.hamada.walletservice.entity.Transaction;
import com.hamada.walletservice.entity.Wallet;

import java.util.List;

public interface TransactionService {
    Transaction saveTransaction(Transaction transaction);
    List<Transaction> getAllTransactions();
//    List<Transaction> getTransactionsByUser(Long userId);
    List<Transaction> getTransactionsByWallet(Long id);
}
