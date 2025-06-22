package com.example.backend_java.service;

import com.example.backend_java.common.UserGender;
import com.example.backend_java.controller.request.UserCreateRequest;
import com.example.backend_java.controller.request.UserPasswordRequest;
import com.example.backend_java.controller.request.UserUpdateRequest;
import com.example.backend_java.controller.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserResponse saveUser(UserCreateRequest userCreateRequest);
    Page<UserResponse> getAllUsers(Pageable  pageable);
    UserResponse updateUser(long userId,UserUpdateRequest userUpdateRequest);
    void changePassword(long userId ,UserPasswordRequest userPasswordRequest);
    void deleteUser(long userId);
    UserResponse getUserDetail(long userId);
    Page<UserResponse> getallUsersByKeyword(String keyword,Pageable pageable);
    Page<UserResponse>  getUsersByCityAndGender(String city, UserGender gender, Pageable pageable);
}
