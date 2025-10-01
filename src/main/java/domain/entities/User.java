package domain.entities;

import domain.value_objects.Email;

import java.time.LocalDate;
import java.util.UUID;

public class User {

    private UUID id;

    private String username;

    private Email email;

    private LocalDate date_of_brith;

    public User() {
    }

    public User(UUID id, String username, Email email, LocalDate date_of_brith) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.date_of_brith = date_of_brith;
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

    public LocalDate getDate_of_brith() {
        return date_of_brith;
    }

    public void setDate_of_brith(LocalDate date_of_brith) {
        this.date_of_brith = date_of_brith;
    }
}
