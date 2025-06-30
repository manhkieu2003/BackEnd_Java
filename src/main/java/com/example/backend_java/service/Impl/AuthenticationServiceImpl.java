package com.example.backend_java.service.Impl;

import com.example.backend_java.controller.request.SignInRequest;
import com.example.backend_java.controller.response.TokenResponse;
import com.example.backend_java.exception.ForBiddenException;
import com.example.backend_java.exception.InvalidDataException;
import com.example.backend_java.model.UserEntity;
import com.example.backend_java.repository.UserRepository;
import com.example.backend_java.service.AuthenticationService;
import com.example.backend_java.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.example.backend_java.common.TokenType.REFRESH_TOKEN;

@Service
@Slf4j(topic = "AuthenticationServiceImpl")
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Override
    public TokenResponse getAccessToken(SignInRequest signInRequest) {
        log.info("getAccessToken method called service");
        try {
            // Thực hiện xác thực với username và password
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(),signInRequest.getPassword()));

            log.info("isAuthenticated = {}", authenticate.isAuthenticated());
            log.info("Authorities: {}", authenticate.getAuthorities().toString());

            // Nếu xác thực thành công, lưu thông tin vào SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            log.info("SecurityContext - Principal: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            log.info("SecurityContext - Authorities: {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            log.info("SecurityContext - Is Authenticated: {}", SecurityContextHolder.getContext().getAuthentication().isAuthenticated());

        } catch (BadCredentialsException | DisabledException e) {
            log.error("errorMessage: {}", e.getMessage());
            throw new AccessDeniedException(e.getMessage());
        }
        // Get user
        var user = userRepository.findByUsername(signInRequest.getUsername());
        log.info("User: {}", user);
        if (user == null) {
            throw new UsernameNotFoundException(signInRequest.getUsername());
        }

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getUsername(), user.getAuthorities());
        String refreshToken = jwtService.generateRefreshToken(user.getId(), user.getUsername(), user.getAuthorities());

        return TokenResponse.builder().AccessToken(accessToken).RefreshToken(refreshToken).build();
    }

    @Override
    public TokenResponse getRefreshToken(String request) {
        log.info("Get refresh token");

        if (!StringUtils.hasLength(request)) {
            throw new InvalidDataException("Token must be not blank");
        }

        try {
            // Verify token
            String userName = jwtService.extractUserName(request, REFRESH_TOKEN);

            // check user is active or inactivated
            UserEntity user = userRepository.findByUsername(userName);

            // generate new access token
            String accessToken = jwtService.generateAccessToken(user.getId(), user.getUsername(), user.getAuthorities());

            return TokenResponse.builder().AccessToken(accessToken).RefreshToken(request).build();
        } catch (Exception e) {
            log.error("Access denied! errorMessage: {}", e.getMessage());
            throw new ForBiddenException(e.getMessage());
        }
    }

}
