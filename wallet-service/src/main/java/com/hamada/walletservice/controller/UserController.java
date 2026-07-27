package com.hamada.walletservice.controller;

import com.hamada.walletservice.dto.*;
import com.hamada.walletservice.entity.User;
import com.hamada.walletservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@Valid @RequestBody RegisterUserRequest request){
        User user=new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setBalance(0.0);
        user.setPassword(request.getPassword());
        return userService.createUser(user);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody CreateUserRequest request){
        User user=new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setBalance(request.getBalance());
        return userService.updateUser(id,user);
    }

    @PostMapping("/{id}/deposit")
    public User deposit(@PathVariable Long id, @Valid @RequestBody DepositRequest request){
        return userService.deposit(id,request.getAmount());
    }

    @PostMapping("/{id}/withdraw")
    public User withdraw(@PathVariable Long id, @Valid @RequestBody WithdrawRequest request){
        return userService.withdraw(id,request.getAmount());
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request){
        return new LoginResponse(userService.login(request.getEmail(), request.getPassword()));
    }
}
