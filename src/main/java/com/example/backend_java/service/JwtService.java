package com.example.backend_java.service;

import com.example.backend_java.common.TokenType;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface JwtService {
    String generateAccessToken(long userId , String username, Collection<? extends GrantedAuthority> authorities);

    String generateRefreshToken(long userId , String username, Collection<? extends GrantedAuthority> authorities);
    String  extractUserName(String token, TokenType tokenType);

}
