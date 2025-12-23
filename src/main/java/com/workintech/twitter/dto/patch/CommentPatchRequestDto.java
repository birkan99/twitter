package com.workintech.twitter.dto.patch;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;

public record CommentPatchRequestDto(
        @JsonProperty("content") @Size(max = 200) String content
) {}