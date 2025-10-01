package com.stefanini.cycle_authenticate.application.ports.outbound.repositories;

import com.stefanini.cycle_authenticate.domain.entities.User;
import com.stefanini.cycle_authenticate.domain.value_objects.Email;

import java.util.UUID;

public interface UserRepositoryPort {
    User save(User user);
    User findByEmail(Email email);
    User findById(UUID id);
}
