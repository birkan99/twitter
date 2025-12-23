package com.workintech.twitter.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record RetweetRequestDto(
        @JsonProperty("tweet_id") @NotNull(message = "Retweet edilecek tweet ID'si boş olamaz") Long tweetId
) {}