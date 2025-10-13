package com.stefanini.cycle_authenticate.infra.adapters.outbound.database.repositories;


import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserModelRepository extends JpaRepository<UserModel, UUID> {

    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findByUsername(String username);

}
