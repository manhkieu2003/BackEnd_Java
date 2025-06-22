package com.example.backend_java.service;

import com.example.backend_java.controller.response.AddressResponse;

import java.util.List;

public interface AddressService {
    List<AddressResponse> getActiveUserAddressesByType(Integer AddressType);
}
