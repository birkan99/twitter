package com.workintech.twitter.dto.patch;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;

public record TweetPatchRequestDto(
        @JsonProperty("content")
        @Size(max = 280)
        String content
) {}