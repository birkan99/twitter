package com.workintech.twitter.service;

import com.workintech.twitter.dto.patch.CommentPatchRequestDto;
import com.workintech.twitter.dto.request.CommentRequestDto;
import com.workintech.twitter.dto.response.CommentResponseDto;
import java.util.List;

public interface CommentService {

    //GET
    List<CommentResponseDto> getAll();

    //GET
    CommentResponseDto findById(Long id);

    //POST
    CommentResponseDto create(CommentRequestDto commentRequestDto);


    //PUT
    CommentResponseDto replaceOrCreate(Long id, CommentRequestDto commentRequestDto);


    //PATCH /tek bir alan güncellemek için
    CommentResponseDto update(Long id, CommentPatchRequestDto commentPatchRequestDto);


    //DELETE
    void deleteById(Long id);
}
