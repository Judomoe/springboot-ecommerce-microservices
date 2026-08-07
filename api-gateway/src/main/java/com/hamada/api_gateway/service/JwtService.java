package com.hamada.api_gateway.service;

public interface JwtService {

    String extractUsername(String token);

    boolean isTokenValid(String token);

}