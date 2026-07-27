package com.hamada.walletservice.service.impl;

import com.hamada.walletservice.entity.User;
import com.hamada.walletservice.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private static final String SECRET_KEY="ThisIsMyVerySecretKeyForWalletServiceAuthentication123456789HamadaHelal3amakYala";

    private static final long EXPIRATION_TIME=1000*60*60*24;

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    private boolean isTokenExpired(String token){
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    @Override
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(User user, String token) {
        Claims claims=extractAllClaims(token);
        String temail=claims.getSubject();
        String uemail=user.getEmail();
        if(temail.equals(uemail)){
            if(isTokenExpired(token)){
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
}
