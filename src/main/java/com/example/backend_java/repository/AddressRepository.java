package com.example.backend_java.repository;

import com.example.backend_java.model.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    AddressEntity findByUserIdAndAddressType(Long userId, Integer addressType);

    @Query("select a from AddressEntity a where a.addressType = ?1 and a.user.status='ACTIVE'")
    List<AddressEntity>  findActiveUserAddressesByType(Integer addressType);
}
