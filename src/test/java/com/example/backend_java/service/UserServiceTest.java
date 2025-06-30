package com.example.backend_java.service;

import com.example.backend_java.common.UserGender;
import com.example.backend_java.common.UserStatus;
import com.example.backend_java.common.UserType;
import com.example.backend_java.controller.response.UserResponse;
import com.example.backend_java.model.UserEntity;
import com.example.backend_java.repository.AddressRepository;
import com.example.backend_java.repository.UserRepository;
import com.example.backend_java.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.AllPermission;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private @Mock UserRepository userRepository;
    private @Mock AddressRepository addressRepository;
    private @Mock PasswordEncoder passwordEncoder;
    private UserService userService;
    private static UserEntity manhkd;
    private static UserEntity NgocAnh;
    @BeforeAll
   static void beforeAll() {
        // khoi tao du lieu
       manhkd = new UserEntity();
       manhkd.setId(1L);
       manhkd.setFirstName("manh");
       manhkd.setLastName("kieu");
       manhkd.setGender(UserGender.MALE);
       manhkd.setDateOfBirth(new Date());
       manhkd.setEmail("manh121123@gmail.com");
       manhkd.setPhone("0909090909");
       manhkd.setUsername("manh");
       manhkd.setPassword("password");
       manhkd.setStatus(UserStatus.ACTIVE);
       manhkd.setType(UserType.USER);
       NgocAnh = new UserEntity();
       NgocAnh.setId(2L);
       NgocAnh.setFirstName("Ngoc");
       NgocAnh.setLastName("Anh");
       NgocAnh.setGender(UserGender.MALE);
       NgocAnh.setDateOfBirth(new Date());
       NgocAnh.setEmail("ngocanh@gmail.com");
       NgocAnh.setPhone("0909090909");
       NgocAnh.setUsername("NgocAnh");
       NgocAnh.setPassword("password");
       NgocAnh.setStatus(UserStatus.ACTIVE);
       NgocAnh.setType(UserType.USER);


    }
    @BeforeEach
    void setUp() {
    // khoi tao buoc trien khai la UserService
       userService = new UserServiceImpl(userRepository,addressRepository,passwordEncoder);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveUser() {
    }

    @Test
    void getAllUsers() {
        // gia lap phuong thuc
        Pageable pageable = PageRequest.of(0, 10);
        List<UserEntity> users = List.of(manhkd, NgocAnh);
        PageImpl<UserEntity> userPage = new PageImpl<>(users);
        when(userRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(userPage);
        Page<UserResponse> result = userService.getAllUsers(pageable);
        assertEquals(2, result.getTotalElements());

        UserResponse firstUser = result.getContent().get(0);
        assertEquals("manh", firstUser.getFirstName());
        assertEquals("kieu", firstUser.getLastName());

        UserResponse secondUser = result.getContent().get(1);
        assertEquals("Ngoc", secondUser.getFirstName());
        assertEquals("Anh", secondUser.getLastName());

        verify(userRepository).findAll(pageable);
    }

    @Test
    void updateUser() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUserDetail() {
      // gia lap phuong thuc
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(manhkd));
        UserResponse result = userService.getUserDetail(100L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("manh", result.getFirstName());
        assertEquals("kieu", result.getLastName());
        verify(userRepository).findById(1L);
    }

    @Test
    void getallUsersByKeyword() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        // Giả lập user có username chứa "manh"
        UserEntity user = manhkd;
        user.setUsername("manh");

        List<UserEntity> filteredUsers = List.of(user); // chỉ có user khớp keyword "manh"
        Page<UserEntity> userPage = new PageImpl<>(filteredUsers);

        when(userRepository.findByUsernameContainingIgnoreCase(eq("manh"), eq(pageable)))
                .thenReturn(userPage);

        // Act
        Page<UserResponse> result = userService.getallUsersByKeyword("manh", pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());

        UserResponse userResponse = result.getContent().get(0);
        assertEquals("manh", userResponse.getUsername());  // đảm bảo ánh xạ đúng

        // Verify: đảm bảo repository được gọi đúng
        verify(userRepository, times(1))
                .findByUsernameContainingIgnoreCase("manh", pageable);

    }

    @Test
    void getUsersByCityAndGender() {

    }
}