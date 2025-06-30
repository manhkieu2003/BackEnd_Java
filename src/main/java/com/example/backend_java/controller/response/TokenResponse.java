package com.example.backend_java.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenResponse {
    private String AccessToken;
    private String RefreshToken;
}
