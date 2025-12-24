package com.workintech.twitter.controller;

import com.workintech.twitter.dto.request.LikeRequestDto;
import com.workintech.twitter.dto.response.LikeResponseDto;
import com.workintech.twitter.service.LikeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
@Validated
public class LikeController {

    private final LikeService likeService;

    @GetMapping
    public List<LikeResponseDto> findAll() {
        return likeService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LikeResponseDto like(@Valid @RequestBody LikeRequestDto likeRequestDto) {
        return likeService.create(likeRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlike(@Positive @PathVariable Long id) {
        likeService.deleteById(id);
    }
}