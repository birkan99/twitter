package com.workintech.twitter.dto.patch;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserPatchRequestDto(
        String password,
        @JsonProperty("display_name")
        String displayName
) {}

