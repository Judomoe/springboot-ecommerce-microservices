package com.hamada.walletservice.service;

import com.hamada.walletservice.entity.User;

public interface JwtService {
    String generateToken(User user);
    boolean isTokenValid(User user, String token);
    String extractUsername(String token);
}
