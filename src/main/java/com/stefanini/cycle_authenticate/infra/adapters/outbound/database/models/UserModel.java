package com.stefanini.cycle_authenticate.infra.adapters.outbound.database.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "users")
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

}
