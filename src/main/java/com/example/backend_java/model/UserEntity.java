package com.example.backend_java.model;

import com.example.backend_java.common.UserGender;
import com.example.backend_java.common.UserStatus;
import com.example.backend_java.common.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "tbl_user")
@ToString
public class UserEntity extends AbstractEntity<Long> implements UserDetails,Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 255)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 255)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserGender gender;

    @Column(length = 15)
    private String phone;

    @Column(length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String username;

    @Column(length = 255)
    private String password;

    @Enumerated(EnumType.STRING)

    @Column(nullable = false)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType type;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<UserHasRole> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<GroupHasUser> groups = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //  1 get role
        List<Role> rolelist = roles.stream().map(UserHasRole::getRole).toList();
        // 2 get rolename
        List<String> rolenames = rolelist.stream().map(Role::getName).toList();
        // 3 add role name to authority

        return rolenames.stream().map(SimpleGrantedAuthority::new).toList();
      //  return rolenames.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())).toList();
       // return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return UserStatus.ACTIVE.equals(status);
    }
}
