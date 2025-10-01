package com.stefanini.cycle_authenticate.domain.entities;

import com.stefanini.cycle_authenticate.domain.value_objects.Email;
import com.stefanini.cycle_authenticate.domain.value_objects.Password;

import java.time.LocalDate;
import java.util.UUID;

public class User {

    private UUID id;

    private String username;

    private Email email;

    private Password password;

    private LocalDate dateOfBrith;

    public User() {
    }

    public User( String username, Email email, Password password, LocalDate dateOfBrith) {
        this.id = null;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dateOfBrith = dateOfBrith;
    }

    public User(UUID id, String username, Email email, Password password, LocalDate dateOfBrith) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dateOfBrith = dateOfBrith;
    }


    public UUID getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public LocalDate getDateOfBrith() {
        return dateOfBrith;
    }

    public void setDateOfBrith(LocalDate dateOfBrith) {
        this.dateOfBrith = dateOfBrith;
    }
}
