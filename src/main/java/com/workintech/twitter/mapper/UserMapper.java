package com.workintech.twitter.mapper;


import com.workintech.twitter.dto.patch.UserPatchRequestDto;
import com.workintech.twitter.dto.request.UserRequestDto;
import com.workintech.twitter.dto.response.UserResponseDto;
import com.workintech.twitter.entity.User;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class UserMapper {

    public User toEntity(UserRequestDto dto) {

        User user = new User();
        user.setEmail(dto.email());
        user.setPassword(dto.password()); // ENCODE LATER
        user.setDisplayName(dto.displayName());
        user.setCreatedAt(OffsetDateTime.now());

        return user;
    }

    public UserResponseDto toResponseDto(User user) {

        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getDisplayName(),
                user.getCreatedAt()
        );
    }

    public void updateEntity(User user, UserPatchRequestDto dto) {

        if (dto.password() != null)
            user.setPassword(dto.password());

        if (dto.displayName() != null)
            user.setDisplayName(dto.displayName());
    }
}
