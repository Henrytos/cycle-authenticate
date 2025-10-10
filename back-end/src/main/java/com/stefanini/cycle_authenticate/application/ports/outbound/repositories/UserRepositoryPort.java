package com.stefanini.cycle_authenticate.application.ports.outbound.repositories;

import com.stefanini.cycle_authenticate.domain.entities.User;
import com.stefanini.cycle_authenticate.domain.value_objects.Email;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {
    Optional<User> save(User user);
    Optional<User> findByEmail(Email email);
    Optional<User> findById(UUID id);
    Optional<User> findByUsername(String username);
}
