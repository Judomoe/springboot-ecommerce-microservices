package com.hamada.walletservice.dto;

import com.hamada.walletservice.entity.Wallet;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class CreateUserRequest {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

//    @PositiveOrZero
//    private Double balance;
    private Wallet wallet;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String name, String email, Wallet wallet) {
        this.name = name;
        this.email = email;
//        this.balance = balance;
        this.wallet=wallet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public Double getBalance() {
//        return balance;
//    }
//
//    public void setBalance(Double balance) {
//        this.balance = balance;
//    }


    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
