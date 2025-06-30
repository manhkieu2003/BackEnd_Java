package com.example.backend_java.service;

import com.example.backend_java.controller.request.SignInRequest;
import com.example.backend_java.controller.response.TokenResponse;

public interface AuthenticationService {
    TokenResponse getAccessToken(SignInRequest signInRequest);
    TokenResponse getRefreshToken(String request);

}
