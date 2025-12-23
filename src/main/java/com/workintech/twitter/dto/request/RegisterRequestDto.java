package com.workintech.twitter.dto.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDto(

        @Email
        @NotBlank
        String email,

        @NotBlank
        String password,

        @NotBlank
        String displayName
) {}
