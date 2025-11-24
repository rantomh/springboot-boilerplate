package com.rantomah.boilerplate.support.mapper.user;

import com.rantomah.boilerplate.adapter.entity.User;
import com.rantomah.boilerplate.application.domain.dto.user.UserDTO;
import com.rantomah.boilerplate.core.mapper.ConfigMapper;
import org.mapstruct.Mapper;

@Mapper(config = ConfigMapper.class)
public interface UserStruct {

    UserDTO toDto(User entity);

    User toEntity(UserDTO dto);
}
