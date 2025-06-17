package com.example.backend_java.controller.request;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class AddressRequest implements Serializable {
    private String apartmentNumber;
    private String floor;
    private String building;
    private String streetNumber;
    private String street;
    private String city;
    private String country;
    private Integer addressType;

}
