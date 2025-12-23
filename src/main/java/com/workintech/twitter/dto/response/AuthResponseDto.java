package com.workintech.twitter.dto.response;

public record AuthResponseDto(
        String token,
        UserResponseDto user
) {}