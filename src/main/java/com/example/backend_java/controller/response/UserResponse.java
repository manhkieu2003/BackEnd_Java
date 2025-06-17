package com.example.backend_java.controller.response;

import com.example.backend_java.common.UserGender;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class UserResponse implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private UserGender gender;
    private Date birthday;
    private String username;
    private String email;
    private String phone;


}
