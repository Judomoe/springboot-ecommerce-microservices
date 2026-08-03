package com.hamada.shopservice.service.impl;

import com.hamada.shopservice.entity.Cart;
import com.hamada.shopservice.repository.CartRepository;
import com.hamada.shopservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public Cart findCartById(Long id) {
        return cartRepository.findById(id).orElseThrow(()->new RuntimeException("Cart not found"));
    }

    @Override
    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart updateCart(Long id, Cart cart) {
        Cart cart1=cartRepository.findById(id).orElseThrow(()->new RuntimeException("Cart not found"));
        cart1.setTotalCost(cart.getTotalCost());
        return cartRepository.save(cart1);
    }

    @Override
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    @Override
    public List<Cart> findByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
