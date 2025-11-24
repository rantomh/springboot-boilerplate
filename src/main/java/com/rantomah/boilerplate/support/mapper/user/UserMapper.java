package com.rantomah.boilerplate.support.mapper.user;

import com.rantomah.boilerplate.adapter.entity.User;
import com.rantomah.boilerplate.application.domain.dto.user.UserDTO;
import com.rantomah.boilerplate.core.mapper.BaseMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper implements BaseMapper<User, UserDTO> {

    private final UserStruct userMapperStruct;

    @Override
    public UserDTO toDto(User entity) {
        return userMapperStruct.toDto(entity);
    }

    @Override
    public User toEntity(UserDTO dto) {
        return userMapperStruct.toEntity(dto);
    }
}
