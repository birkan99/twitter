package com.workintech.twitter.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegisterResponseDto(
        @JsonProperty("email") String email,
        @JsonProperty("message") String message
) {}