package com.hamada.walletservice.controller;

import com.hamada.walletservice.entity.Transaction;
import com.hamada.walletservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("wallets/{walletId}/transactions")
    public List<Transaction> getTransactionsByUser(@PathVariable Long walletId){
        return transactionService.getTransactionsByWallet(walletId);
    }
}
