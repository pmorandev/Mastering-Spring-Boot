package com.pmoran.orderapi.converters;

import com.pmoran.orderapi.dtos.SignupDTO;
import com.pmoran.orderapi.dtos.UserDTO;
import com.pmoran.orderapi.entity.User;

public class UserConverter extends AbstractConverter<User,UserDTO>{

    @Override
    public User fromDto(UserDTO dto) {
        if (dto == null) return null;
        return User.builder()
                .userName(dto.getUsername())
                .id(dto.getId())
                .build();
    }

    @Override
    public UserDTO fromEntity(User entity) {
        if (entity == null) return null;
        return UserDTO.builder()
                .username(entity.getUserName())
                .id(entity.getId())
                .build();
    }

    public User fromSignup(SignupDTO dto){
        if (dto == null) return null;
        return User.builder()
                .userName(dto.getUsername())
                .password(dto.getPassword())
                .build();
    }
}
