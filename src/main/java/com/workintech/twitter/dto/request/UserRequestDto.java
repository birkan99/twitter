package com.workintech.twitter.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDto(

        @Email
        @NotBlank
        String email,

        @NotBlank
        String password,

        @NotBlank
        @JsonProperty("display_name")
        String displayName
) {}
