package com.hamada.walletservice.service.impl;

import com.hamada.walletservice.entity.Transaction;
import com.hamada.walletservice.entity.TransactionType;
import com.hamada.walletservice.entity.User;
import com.hamada.walletservice.exception.ResourceNotFoundException;
import com.hamada.walletservice.repository.TransactionRepository;
import com.hamada.walletservice.repository.UserRepository;
import com.hamada.walletservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public User createUser(User user) {
        boolean heh=userRepository.existsByEmail(user.getEmail());
        if(heh){
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User does not exist"));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(Long id,User user){
        User hamada=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User does not exist"));

        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        hamada.setName(user.getName());
        hamada.setEmail(user.getEmail());
        hamada.setBalance(user.getBalance());
        return userRepository.save(hamada);
    }

    @Override
    public User withdraw(Long id, Double amount){
        User hamada=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User does not exist"));
        if(hamada.getBalance()>=amount){
            hamada.setBalance(hamada.getBalance()-amount);
        }
        else{
            throw new RuntimeException("Balance not enough");
        }
        Transaction transaction=new Transaction();
        transaction.setUser(hamada);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
        return userRepository.save(hamada);
    }

    @Override
    public User deposit(Long id, Double amount){
        User hemeda=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User does not exist"));
        hemeda.setBalance(hemeda.getBalance()+amount);
        Transaction transaction=new Transaction();
        transaction.setUser(hemeda);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
        return userRepository.save(hemeda);
    }
}
