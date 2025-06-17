package com.example.backend_java.controller.request;

import com.example.backend_java.common.UserGender;
import com.example.backend_java.common.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Getter
@ToString
public class UserUpdateRequest implements Serializable {
    @NotBlank(message = "firstname not be not blank")
    private String firstName;
    @NotBlank(message = "firstname not be not blank")
    private String lastName;
    private UserGender gender;
    private Date birthday;
    @Email(message = "email invalid")
    private String email;
    private String phone;
    private String username;
    private List<AddressRequest> addresses;


}
