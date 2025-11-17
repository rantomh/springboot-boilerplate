package com.rantomah.boilerplate.application.mapper.user;

import com.rantomah.boilerplate.application.dto.user.UserDTO;
import com.rantomah.boilerplate.application.mapper.Mapper;
import com.rantomah.boilerplate.domain.model.User;
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
