package com.example.backend_java.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.io.Serializable;
@Getter
public class UserPasswordRequest implements Serializable {
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    private String password;
    @NotBlank(message = "Confirm password cannot be blank")
    private String confirmPassword;
}
