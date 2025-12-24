package com.workintech.twitter.controller;

import com.workintech.twitter.dto.patch.CommentPatchRequestDto;
import com.workintech.twitter.dto.request.CommentRequestDto;
import com.workintech.twitter.dto.response.CommentResponseDto;
import com.workintech.twitter.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Validated
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public List<CommentResponseDto> findAll() {
        return commentService.getAll();
    }

    @GetMapping("/{id}")
    public CommentResponseDto findById(@Positive @PathVariable Long id) {
        return commentService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto create(@Valid @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.create(commentRequestDto);
    }

    @PatchMapping("/{id}")
    public CommentResponseDto update(@Positive @PathVariable Long id,
                                     @Valid @RequestBody CommentPatchRequestDto commentPatchRequestDto) {
        return commentService.update(id, commentPatchRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable Long id) {
        commentService.deleteById(id);
    }
}