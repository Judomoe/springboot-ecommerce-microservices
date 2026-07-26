package com.hamada.walletservice.service;

import com.hamada.walletservice.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction saveTransaction(Transaction transaction);
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsByUser(Long userId);
}
