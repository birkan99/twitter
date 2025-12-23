package com.workintech.twitter.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentRequestDto(
        @JsonProperty("tweet_id") @NotNull(message = "Tweet ID belirtilmelidir") Long tweetId,
        @JsonProperty("content") @NotBlank(message = "Yorum içeriği boş olamaz") @Size(max = 200) String content
) {}