package com.slippery.rentalmanagementsystem.service.impl;

import com.slippery.rentalmanagementsystem.model.UserPrincipal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private final Long expirationTime =86400000L;
    private final String jwtString ="06cad4199ce832cdc009606b02538a5bec4e351afc10d7720986c76b072351b1eecef2e219c5431a5a6330eb5fcf8a71ba86c218dd957f650608da38121ebdb0a04ce1db7b1d10fe32640356d1ed7b54745d85a710a45908a72af06bf8c5f6d44aa505d8da70a5fe834a7a88e2ab202f7d8b7a57cc417d94e9e48a4aad851664b6f7854702815d2318eb937d1eec9bacf20d6d782e11a9b701c137a05fa937af2c44ca3c4fd1c5e476552158fcaa826a777fe36a1e4921c6a6d1776d8fdd85c13894f30a63f0d12dbdb1a930a30505eeb3e7b5f9be305e5d0376b29db311dc74d1e6eab19b759cbaeac40a345d519f878e3fc6a39097a8c45b39c2a3567e3064";

    private SecretKey generateKey(){
        byte[] keyBytes = Base64.getDecoder().decode(jwtString);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    protected String generateJwtToken(String username){
        Map<String,Object> claims =new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+expirationTime))
                .and()
                .signWith(generateKey())
                .compact();
    }
}
