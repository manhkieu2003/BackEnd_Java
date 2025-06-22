package com.example.backend_java.repository;

import com.example.backend_java.common.UserGender;
import com.example.backend_java.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(
            "select i from UserEntity i where i.status='NONE' and lower(i.username) like lower(concat('%', ?1, '%'))  ORDER BY LOWER(i.username) ASC"
    )
    Page<UserEntity> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    //Tìm tất cả người dùng có  địa chỉ ở ... và giới tính ...
    @Query("select i from UserEntity i join AddressEntity a on i.id = a.user.id where a.city=?1 and i.gender=?2")
    Page<UserEntity> findUsersByCityAndGender(String city, UserGender gender, Pageable pageable);
}
