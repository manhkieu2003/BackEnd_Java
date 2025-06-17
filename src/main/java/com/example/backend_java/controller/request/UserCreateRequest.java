package com.example.backend_java.controller.request;

import com.example.backend_java.common.UserGender;
import com.example.backend_java.common.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
public class UserCreateRequest implements Serializable {
    @NotBlank(message = "firstname not be not blank")
    private String firstName;
    @NotBlank(message = "firstname not be not blank")
    private String lastName;
    private UserGender gender;
    private Date birthday;
    @Email(message = "email invalid")
    private String email;

    @Pattern(regexp = "^(\\+84|0)(3|5|7|8|9)[0-9]{8}$", message = "Invalid phone number")
    private String phone;
    @NotBlank(message = "username is required")
    private String username;
    private UserType userType;
    private List<AddressRequest> addresses;

}
