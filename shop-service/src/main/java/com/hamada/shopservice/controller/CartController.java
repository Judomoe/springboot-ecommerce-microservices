package com.hamada.shopservice.controller;

import com.hamada.shopservice.entity.Cart;
import com.hamada.shopservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public List<Cart> getAllCarts(){
        return cartService.getAllCarts();
    }

    @GetMapping("/{id}")
    public Cart findCartById(@PathVariable Long id){
        return cartService.findCartById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Cart> findByUserId(@PathVariable Long userId){
        return cartService.findByUserId(userId);
    }

    @PostMapping
    public Cart createCart(@RequestBody Cart cart){
        return cartService.createCart(cart);
    }

    @PutMapping("/{id}")
    public Cart updateCart(@PathVariable Long id,@RequestBody Cart cart){
        return cartService.updateCart(id,cart);
    }

    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable Long id){
        cartService.deleteCart(id);
    }
}
