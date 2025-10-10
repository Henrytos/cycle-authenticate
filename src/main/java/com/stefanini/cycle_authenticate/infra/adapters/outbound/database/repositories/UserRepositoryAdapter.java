package com.stefanini.cycle_authenticate.infra.adapters.outbound.database.repositories;

import com.stefanini.cycle_authenticate.application.ports.outbound.repositories.UserRepositoryPort;
import com.stefanini.cycle_authenticate.domain.entities.User;
import com.stefanini.cycle_authenticate.domain.value_objects.Email;
import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.mappers.UserMapper;
import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.models.UserModel;
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
        UserModel userModel = this.userMapper.toModel(user);

        userModel = this.jpaUserModelRepository.save(userModel);

        User userSaved = this.userMapper.toDomain(userModel);
        return Optional.of(userSaved);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        Optional<UserModel> userModel = this.jpaUserModelRepository.findByEmail(email.getValue());
        if(userModel.isEmpty()){
            return Optional.empty();
        }
        User user = this.userMapper.toDomain(userModel.get());
        return Optional.of(user);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return this.jpaUserModelRepository.findById(id).map(model-> this.userMapper.toDomain(model));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<UserModel> userModel = this.jpaUserModelRepository.findByUsername(username);

        if(userModel.isEmpty()) return Optional.empty();

        return Optional.of(this.userMapper.toDomain(userModel.get()));
    }
}
