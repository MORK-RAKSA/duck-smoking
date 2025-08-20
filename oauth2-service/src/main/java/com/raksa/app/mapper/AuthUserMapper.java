package com.raksa.app.mapper;

import com.raksa.app.dto.UserResponseDto;
import com.raksa.app.model.AuthUser;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface AuthUserMapper {
    UserResponseDto toResponseDto(AuthUser entity);
}
