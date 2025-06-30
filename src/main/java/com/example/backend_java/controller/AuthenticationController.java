package com.example.backend_java.controller;

import com.example.backend_java.controller.request.SignInRequest;
import com.example.backend_java.controller.response.TokenResponse;
import com.example.backend_java.service.AuthenticationService;
import com.example.backend_java.service.Impl.AuthenticationServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j(topic = "AuthenticationController")
@Tag(name = "Authentication Controller")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
   @Tag(name = "Access Token")
   @PostMapping("/access-token")
    public TokenResponse getAccessToken(@RequestBody SignInRequest request) {
        log.info("AccessToken method called controller");
        return authenticationService.getAccessToken(request);

    }
    @Tag(name = "Refresh Token")
    @PostMapping("/refresh-token")
    public TokenResponse getRefreshToken(@RequestBody String refreshToken) {
        log.info("Refresh Token method called");
        return authenticationService.getRefreshToken(refreshToken);

    }


}
