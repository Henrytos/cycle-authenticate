package com.stefanini.cycle_authenticate.infra.adapters.outbound.database.models;

import com.stefanini.cycle_authenticate.domain.value_objects.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel implements UserDetails {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;

    private String email;

    @Column(name = "password_hash")
    private String password;

    @Column(name = "date_of_brith")
    private LocalDate dateOfBrith;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole;

    public UserModel(String username, String email, String password, LocalDate dateOfBrith) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.dateOfBrith = dateOfBrith;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dateOfBrith=" + dateOfBrith +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();

        if (this.userRole.getRole().equals("ROLE_ADMIN"))
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(this.userRole.getRole()));

        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return simpleGrantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
