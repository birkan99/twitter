package com.workintech.twitter.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TweetRequestDto(
        @JsonProperty("content")
        @NotBlank(message = "Tweet içeriği boş olamaz")
        @Size(max = 280, message = "Tweet en fazla 280 karakter olabilir")
        String content
) {}