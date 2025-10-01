package com.stefanini.cycle_authenticate.infra.adapters.outbound.database.mappers;

import com.stefanini.cycle_authenticate.domain.entities.User;
import com.stefanini.cycle_authenticate.domain.value_objects.Email;
import com.stefanini.cycle_authenticate.domain.value_objects.Password;
import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.models.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * this.mapper.typeMap(UserEntity.class, User.class).addMappings(
     * mapper ->{
     * mapper.map(UserEntity::getName, User::setName);
     * mapper.map(src-> Email.of(src.getEmail()) ,User::setEmail);
     * mapper.map(UserEntity::getPassword, User::setPassword);
     * mapper.map(src-> Role.valueOf(src.getRole()),User::setRole);
     * }
     * );
     *
     */
    public User toDomain(UserModel userModel) {
        this.modelMapper.typeMap(UserModel.class, User.class).addMappings(
                modelMapper -> {
                    modelMapper.map(model -> new Email(model.getEmail()), User::setEmail);
                    modelMapper.map(model -> new Password(model.getPassword()), User::setPassword);
                }
        );

        return this.modelMapper.map(userModel, User.class);
    }

    public UserModel toModel(User user) {
        this.modelMapper.typeMap(User.class, UserModel.class).addMappings(
                modelMapper -> {
                    modelMapper.map(entity -> entity.getEmail().getValue(), UserModel::setEmail);
                    modelMapper.map(entity -> entity.getPassword().getValue(), UserModel::setPassword);

                }
        );

        return this.modelMapper.map(user, UserModel.class);
    }
}
