package com.workintech.twitter.service;

import com.workintech.twitter.dto.patch.RetweetPatchRequestDto;
import com.workintech.twitter.dto.request.*;
import com.workintech.twitter.dto.response.RetweetResponseDto;

import java.util.List;

public interface RetweetService {

    //GET
    List<RetweetResponseDto> getAll();

    //GET
    RetweetResponseDto findById(Long id);

    //POST


    //PUT
    RetweetResponseDto replaceOrCreate(Long id, RetweetRequestDto retweetRequestDto);

    //PATCH /tek bir alan güncellemek için
    RetweetResponseDto update(Long id, RetweetPatchRequestDto retweetPatchRequestDto);

    //DELETE
    void deleteById(Long id);
}
