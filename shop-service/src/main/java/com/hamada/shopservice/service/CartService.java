package com.hamada.shopservice.service;

import com.hamada.shopservice.entity.Cart;

import java.util.List;

public interface CartService {

    List<Cart> getAllCarts();

    Cart findCartById(Long id);

    Cart createCart(Cart cart);

    Cart updateCart(Long id,Cart cart);

    void deleteCart(Long id);

    List<Cart> findByUserId(Long userId);
}
