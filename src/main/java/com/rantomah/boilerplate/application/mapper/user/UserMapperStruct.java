package com.rantomah.boilerplate.application.mapper.user;

import com.rantomah.boilerplate.application.dto.user.UserDTO;
import com.rantomah.boilerplate.application.mapper.ConfigMapper;
import com.rantomah.boilerplate.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(config = ConfigMapper.class)
public interface UserMapperStruct {

    UserDTO toDto(User entity);

    User toEntity(UserDTO dto);
}
