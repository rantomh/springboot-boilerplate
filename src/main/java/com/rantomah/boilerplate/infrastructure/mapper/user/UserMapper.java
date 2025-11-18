package com.rantomah.boilerplate.infrastructure.mapper.user;

import com.rantomah.boilerplate.application.domain.dto.user.UserDTO;
import com.rantomah.boilerplate.application.domain.entities.User;
import com.rantomah.boilerplate.core.mapper.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper implements Mapper<User, UserDTO> {

    private final UserMapperStruct userMapperStruct;

    @Override
    public UserDTO toDto(User entity) {
        UserDTO dto = userMapperStruct.toDto(entity);
        return dto;
    }

    @Override
    public User toEntity(UserDTO dto) {
        return userMapperStruct.toEntity(dto);
    }
}
