package com.example.backend_java.controller;

import com.example.backend_java.common.UserGender;
import com.example.backend_java.controller.request.UserCreateRequest;
import com.example.backend_java.controller.request.UserPasswordRequest;
import com.example.backend_java.controller.request.UserUpdateRequest;
import com.example.backend_java.controller.response.UserResponse;
import com.example.backend_java.service.UserService;
import com.example.backend_java.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "User Controller")
public class UserController {
    @Autowired
    private UserService userService;
    @Operation(summary = "Test api", description = "Lay danh sach user")
    @GetMapping("/list")
    public ResponseEntity<List<UserResponse>>getList(@ParameterObject Pageable pageable) {
        Page<UserResponse> userResponsePage = userService.getAllUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(),userResponsePage);
       return new ResponseEntity<>(userResponsePage.getContent(),headers,HttpStatus.OK);
    }

    @GetMapping("/list/kw")
    public ResponseEntity<List<UserResponse>>getListbyKeyword(@RequestParam(required = false,defaultValue = "") String keyword,@ParameterObject Pageable pageable) {
        Page<UserResponse> userResponsePage = userService.getallUsersByKeyword(keyword,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(),userResponsePage);
        return new ResponseEntity<>(userResponsePage.getContent(),headers,HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest)
    {
    log.info("createUser method called :{}",userCreateRequest);
       return ResponseEntity.ok(userService.saveUser(userCreateRequest));
    }
            @PutMapping("/update/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId,@Valid @RequestBody UserUpdateRequest userUpdateRequest)
    {
        return ResponseEntity.ok(userService.updateUser(userId,userUpdateRequest));
    }
    @PatchMapping("/update/{userId}/change-pwd")
    public ResponseEntity<Void> changePassword(@PathVariable Long userId,@Valid @RequestBody UserPasswordRequest userPasswordRequest)
    {
        userService.changePassword(userId,userPasswordRequest);
        return ResponseEntity.accepted().build();
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId)
    {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserDetail(@PathVariable long userId) {
        return ResponseEntity.ok(userService.getUserDetail(userId));
    }
    @GetMapping("/city")
    public ResponseEntity<List<UserResponse>> getUsersByCityAndGender(@RequestParam String city, @RequestParam UserGender gender, @ParameterObject Pageable pageable) {
        Page<UserResponse> userResponsePage = userService.getUsersByCityAndGender(city,gender,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(),userResponsePage);
        return new ResponseEntity<>(userResponsePage.getContent(),headers,HttpStatus.OK);
    }



}
