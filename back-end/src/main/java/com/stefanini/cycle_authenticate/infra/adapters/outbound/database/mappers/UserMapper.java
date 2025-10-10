package com.stefanini.cycle_authenticate.infra.adapters.outbound.database.mappers;

import com.stefanini.cycle_authenticate.domain.entities.User;
import com.stefanini.cycle_authenticate.domain.value_objects.Email;
import com.stefanini.cycle_authenticate.domain.value_objects.Password;
import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.models.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.configureMappings();
    }

    private void configureMappings() {
       this.modelMapper.typeMap(UserModel.class, User.class).addMappings( modelMapper->{
           modelMapper.using(ctx-> new Email((String) ctx.getSource()))
                   .map(UserModel::getEmail, User::setEmail);

           modelMapper.using(ctx -> new Password((String) ctx.getSource()))
                   .map(UserModel::getPassword, User::setPassword);
       });

        this.modelMapper.typeMap(User.class, UserModel.class).addMappings(mapper -> {
            mapper
                    .using(ctx->{
                        Password password = (Password) ctx.getSource();
                        return password != null ? password.getValue() : null;
                    })
                    .map(User::getPassword, UserModel::setPassword);

            mapper
                    .using(ctx->{
                        Email email = (Email) ctx.getSource();
                        return email != null ? email.getValue() : null;
                    })
                    .map(User::getEmail, UserModel::setEmail);
        });
    }

    public User toDomain(UserModel userModel) {
        if (userModel == null) {
            return null;
        }

        return this.modelMapper.map(userModel, User.class);
    }

    public UserModel toModel(User user) {
        if (user == null) {
            return null;
        }

        return this.modelMapper.map(user, UserModel.class);
    }
}