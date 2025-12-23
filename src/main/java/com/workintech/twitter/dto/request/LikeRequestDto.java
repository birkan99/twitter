package com.workintech.twitter.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record LikeRequestDto(
        @JsonProperty("tweet_id") @NotNull Long tweetId
) {}