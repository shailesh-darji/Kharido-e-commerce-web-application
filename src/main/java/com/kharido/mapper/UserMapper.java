package com.kharido.mapper;

import com.kharido.dto.UserDto;
import com.kharido.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    public User userDtoToUser(UserDto userDto);

    public UserDto userToUserDto(User user);
}
