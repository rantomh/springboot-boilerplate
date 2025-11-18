package com.rantomah.boilerplate.infrastructure.mapper.user;

import com.rantomah.boilerplate.application.domain.dto.user.UserDTO;
import com.rantomah.boilerplate.application.domain.entities.User;
import com.rantomah.boilerplate.core.mapper.ConfigMapper;
import org.mapstruct.Mapper;

@Mapper(config = ConfigMapper.class)
public interface UserMapperStruct {

    UserDTO toDto(User entity);

    User toEntity(UserDTO dto);
}
