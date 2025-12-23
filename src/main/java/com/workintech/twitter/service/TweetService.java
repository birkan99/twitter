package com.workintech.twitter.service;

import com.workintech.twitter.dto.patch.TweetPatchRequestDto;
import com.workintech.twitter.dto.request.TweetRequestDto;
import com.workintech.twitter.dto.response.TweetResponseDto;
import java.util.List;

public interface TweetService {
    List<TweetResponseDto> getAll();
    List<TweetResponseDto> findAllByUserId(Long userId);
    TweetResponseDto findById(Long id);
    TweetResponseDto create(TweetRequestDto tweetRequestDto);
    TweetResponseDto replaceOrCreate(Long id, TweetRequestDto tweetRequestDto);
    TweetResponseDto update(Long id, TweetPatchRequestDto tweetPatchRequestDto);
    void deleteById(Long id);
}