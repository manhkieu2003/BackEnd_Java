package com.example.backend_java.service.Impl;

import com.example.backend_java.common.UserStatus;
import com.example.backend_java.controller.request.UserCreateRequest;
import com.example.backend_java.controller.request.UserPasswordRequest;
import com.example.backend_java.controller.request.UserUpdateRequest;
import com.example.backend_java.controller.response.UserResponse;
import com.example.backend_java.exception.InvalidDataException;
import com.example.backend_java.exception.NoMatchException;
import com.example.backend_java.exception.ResourceNotfoundException;
import com.example.backend_java.model.AddressEntity;
import com.example.backend_java.model.UserEntity;
import com.example.backend_java.repository.AddressRepository;
import com.example.backend_java.repository.UserRepository;
import com.example.backend_java.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public UserResponse saveUser(UserCreateRequest userCreateRequest) {
        log.info("UserServiceImpl saveUser");
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userCreateRequest.getFirstName());
        userEntity.setLastName(userCreateRequest.getLastName());
        userEntity.setGender(userCreateRequest.getGender());
        userEntity.setDateOfBirth(userCreateRequest.getBirthday());
        if(userRepository.existsByEmail(userCreateRequest.getEmail())) {
           throw new InvalidDataException("email already exists");
        }
        userEntity.setEmail(userCreateRequest.getEmail());
        userEntity.setPhone(userCreateRequest.getPhone());
        if(userRepository.existsByUsername(userCreateRequest.getUsername())) {
            throw new InvalidDataException("username already exists");
        }
        userEntity.setUsername(userCreateRequest.getUsername());
        userEntity.setType(userCreateRequest.getUserType());
        userEntity.setStatus(UserStatus.NONE);
        userRepository.save(userEntity);
         if(userEntity.getId()!=null && userCreateRequest.getAddresses() != null){
             List<AddressEntity> addressEntities = new ArrayList<>();
             userCreateRequest.getAddresses().forEach(address -> {
                 AddressEntity addressEntity1 = new AddressEntity();
                 addressEntity1.setApartmentNumber(address.getApartmentNumber());
                 addressEntity1.setFloor(address.getFloor());
                 addressEntity1.setBuilding(address.getBuilding());
                 addressEntity1.setStreetNumber(address.getStreetNumber());
                 addressEntity1.setStreet(address.getStreet());
                 addressEntity1.setCity(address.getCity());
                 addressEntity1.setCountry(address.getCountry());
                 addressEntity1.setAddressType(address.getAddressType());
                 addressEntity1.setUser(userEntity);
                 addressEntities.add(addressEntity1);

             });
                     addressRepository.saveAll(addressEntities);
                     log.info("Address saveUser successfully");
         }
     return convertToUserResponse(userEntity);
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<UserEntity> userEntities = userRepository.findAll(pageable);
        return userEntities.map(this::convertToUserResponse);
    }

    @Override
    public UserResponse updateUser(long UserId ,UserUpdateRequest userUpdateRequest) {
        log.info(" updateUser: {}",userUpdateRequest);
        // get user by id
        UserEntity userEntity = getUserEntity(UserId);
        userEntity.setFirstName(userUpdateRequest.getFirstName());
        userEntity.setLastName(userUpdateRequest.getLastName());
        userEntity.setGender(userUpdateRequest.getGender());
        userEntity.setDateOfBirth(userUpdateRequest.getBirthday());
        userEntity.setEmail(userUpdateRequest.getEmail());
        userEntity.setPhone(userUpdateRequest.getPhone());
        userRepository.save(userEntity);
        log.info(" updateUser successfully: {}",userEntity);
        List<AddressEntity> addressEntities = new ArrayList<>();
        // save address
        userUpdateRequest.getAddresses().forEach(address -> {
            AddressEntity addressEntity1 = addressRepository.findByUserIdAndAddressType(userEntity.getId(), address.getAddressType());
         if(addressEntity1==null){
             addressEntity1 = new AddressEntity();
         }
            addressEntity1.setApartmentNumber(address.getApartmentNumber());
            addressEntity1.setFloor(address.getFloor());
            addressEntity1.setBuilding(address.getBuilding());
            addressEntity1.setStreetNumber(address.getStreetNumber());
            addressEntity1.setStreet(address.getStreet());
            addressEntity1.setCity(address.getCity());
            addressEntity1.setCountry(address.getCountry());
            addressEntity1.setAddressType(address.getAddressType());
            addressEntity1.setUser(userEntity);
            addressEntities.add(addressEntity1);
        });
        addressRepository.saveAll(addressEntities);
        return convertToUserResponse(userEntity);
    }

    @Override
    public void changePassword(long userId,UserPasswordRequest userPasswordRequest) {
         UserEntity userEntity = getUserEntity(userId);
         if(userPasswordRequest.getPassword().equals(userPasswordRequest.getConfirmPassword())){
             //update and bcryt password
             userEntity.setPassword(passwordEncoder.encode(userPasswordRequest.getPassword()));
        } else{
             throw new NoMatchException("Password and confirm password do not match");
         }
         userRepository.save(userEntity);
         log.info(" changePassword successfully: {}",userEntity);
    }

    @Override
    public void deleteUser(long userId) {
     log.info("UserServiceImpl deleteUser: {}",userId);
     UserEntity userEntity = getUserEntity(userId);
      userEntity.setStatus(UserStatus.INACTIVE);
      userRepository.save(userEntity);
      log.info("UserServiceImpl deleteUser successfully: {}",userEntity);
    }

    @Override
    public UserResponse getUserDetail(long userId) {
        log.info("UserServiceImpl getUserDetail: {}",userId);
       UserEntity userEntity = userRepository.findById(userId).orElseThrow(()->new ResourceNotfoundException("User not found"));
       return convertToUserResponse(userEntity);
    }

    @Override
    public Page<UserResponse> getallUsersByKeyword(String keyword, Pageable pageable) {
        Page<UserEntity> userEntities = userRepository.findByUsernameContainingIgnoreCase(keyword.trim(),pageable);
        return userEntities.map(this::convertToUserResponse);
    }

    private UserResponse convertToUserResponse(UserEntity userEntity) {
        UserResponse response = new UserResponse();
        response.setId(userEntity.getId());
        response.setFirstName(userEntity.getFirstName());
        response.setLastName(userEntity.getLastName());
        response.setGender(userEntity.getGender());
        response.setBirthday(userEntity.getDateOfBirth());
        response.setUsername(userEntity.getUsername());
        response.setEmail(userEntity.getEmail());
        response.setPhone(userEntity.getPhone());
        return response;
    }
    private UserEntity getUserEntity(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new ResourceNotfoundException("User not found"));
    }
}
