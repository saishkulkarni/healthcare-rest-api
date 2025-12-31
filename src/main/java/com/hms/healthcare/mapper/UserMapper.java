package com.hms.healthcare.mapper;

import org.mapstruct.Mapper;

import com.hms.healthcare.dto.UserResponseDto;
import com.hms.healthcare.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserResponseDto toUserResponseDto(User user);

}