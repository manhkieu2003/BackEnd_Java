package com.example.backend_java.controller;

import com.example.backend_java.controller.response.AddressResponse;
import com.example.backend_java.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @GetMapping("/AddressType/{AddressType}")
    public ResponseEntity<List<AddressResponse>> getActiveUserAddressesByType(@PathVariable  Integer AddressType) {
        return ResponseEntity.ok(addressService.getActiveUserAddressesByType(AddressType));
    }
}
