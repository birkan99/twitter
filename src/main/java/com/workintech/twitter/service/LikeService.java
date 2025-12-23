package com.workintech.twitter.service;

import com.workintech.twitter.dto.patch.LikePatchRequestDto;
import com.workintech.twitter.dto.request.LikeRequestDto;
import com.workintech.twitter.dto.response.LikeResponseDto;


import java.util.List;

public interface LikeService {

    List<LikeResponseDto> getAll();
    LikeResponseDto findById(Long id);
    LikeResponseDto create(LikeRequestDto likeRequestDto);
    LikeResponseDto replaceOrCreate(Long id, LikeRequestDto likeRequestDto);
    LikeResponseDto update(Long id, LikePatchRequestDto likePatchRequestDto);
    void deleteById(Long id);
}

