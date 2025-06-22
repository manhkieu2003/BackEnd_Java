package com.example.backend_java.service.Impl;

import com.example.backend_java.controller.response.AddressResponse;
import com.example.backend_java.model.AddressEntity;
import com.example.backend_java.repository.AddressRepository;
import com.example.backend_java.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<AddressResponse> getActiveUserAddressesByType(Integer AddressType) {
        List<AddressEntity> addressEntities = addressRepository.findActiveUserAddressesByType(AddressType);
        return addressEntities.stream()
                .map(this::convertToAddressResponse)
                .collect(Collectors.toList());
    }
    private AddressResponse convertToAddressResponse(AddressEntity addressEntity) {
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setApartmentNumber(addressEntity.getApartmentNumber());
        addressResponse.setFloor(addressEntity.getFloor());
        addressResponse.setBuilding(addressEntity.getBuilding());
        addressResponse.setStreetNumber(addressEntity.getStreetNumber());
        addressResponse.setStreet(addressEntity.getStreet());
        addressResponse.setCity(addressEntity.getCity());
        addressResponse.setCountry(addressEntity.getCountry());
        return addressResponse;
    }


}
