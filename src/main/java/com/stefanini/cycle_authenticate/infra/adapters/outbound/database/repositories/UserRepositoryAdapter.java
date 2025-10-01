package com.stefanini.cycle_authenticate.infra.adapters.outbound.database.repositories;

import com.stefanini.cycle_authenticate.application.ports.outbound.repositories.UserRepositoryPort;
import com.stefanini.cycle_authenticate.domain.entities.User;
import com.stefanini.cycle_authenticate.domain.value_objects.Email;
import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.mappers.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {

    private JpaUserModelRepository jpaUserModelRepository;

    private UserMapper userMapper;

    public UserRepositoryAdapter(JpaUserModelRepository jpaUserModelRepository, UserMapper userMapper) {
        this.jpaUserModelRepository = jpaUserModelRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> save(User user) {
        return Optional.of(this.userMapper.toDomain(this.jpaUserModelRepository.save(this.userMapper.toModel(user))));
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return this.jpaUserModelRepository.findByEmail(email.getValue()).map(model->this.userMapper.toDomain(model));
    }

    @Override
    public Optional<User> findById(UUID id) {
        return this.jpaUserModelRepository.findById(id).map(model-> this.userMapper.toDomain(model));
    }
}
