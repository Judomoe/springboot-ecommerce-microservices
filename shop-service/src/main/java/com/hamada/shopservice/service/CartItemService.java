package com.hamada.shopservice.service;

import com.hamada.shopservice.entity.CartItem;

import java.util.List;

public interface CartItemService {
    List<CartItem> getAllCartItems();

    CartItem getCartItemById(Long id);

    CartItem createCartItem(CartItem cartItem);

    CartItem updateCartItem(Long id, CartItem cartItem);

    void deleteCartItem(Long id);
}
