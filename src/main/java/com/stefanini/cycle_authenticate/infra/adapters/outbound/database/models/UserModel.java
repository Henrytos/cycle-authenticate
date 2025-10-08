package com.stefanini.cycle_authenticate.infra.adapters.outbound.database.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {

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
}
