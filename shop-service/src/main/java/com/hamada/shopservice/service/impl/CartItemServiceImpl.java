package com.hamada.shopservice.service.impl;

import com.hamada.shopservice.entity.CartItem;
import com.hamada.shopservice.repository.CartItemRepository;
import com.hamada.shopservice.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    @Override
    public CartItem getCartItemById(Long id) {
        return cartItemRepository.findById(id).orElseThrow(()->new RuntimeException("Cart item not found"));
    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setUnitPrice(cartItem.getProduct().getPrice());
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateCartItem(Long id, CartItem cartItem) {
        CartItem cartItem1=cartItemRepository.findById(id).orElseThrow(()->new RuntimeException("Cart item not found"));
        cartItem1.setQuantity(cartItem.getQuantity());
        cartItem1.setUnitPrice(cartItem.getUnitPrice());
        return cartItemRepository.save(cartItem1);
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }
}
