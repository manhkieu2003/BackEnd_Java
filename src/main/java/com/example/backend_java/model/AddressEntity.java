package com.example.backend_java.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "tbl_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressEntity extends AbstractEntity<Long> {
    @Column(name = "apartment_number", length = 255)
    private String apartmentNumber;

    @Column(length = 255)
    private String floor;

    @Column(length = 255)
    private String building;

    @Column(name = "street_number", length = 255)
    private String streetNumber;

    @Column(length = 255)
    private String street;

    @Column(length = 255)
    private String city;

    @Column(length = 255)
    private String country;

    @Column(name = "address_type")
    private Integer addressType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;


}
