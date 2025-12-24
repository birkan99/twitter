package com.workintech.twitter.controller;

import com.workintech.twitter.dto.request.RetweetRequestDto;
import com.workintech.twitter.dto.response.RetweetResponseDto;
import com.workintech.twitter.service.RetweetService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/retweets")
@RequiredArgsConstructor
@Validated
public class RetweetController {

    private final RetweetService retweetService;

    @GetMapping
    public List<RetweetResponseDto> findAll() {
        return retweetService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RetweetResponseDto retweet(@Valid @RequestBody RetweetRequestDto retweetRequestDto) {
        return retweetService.replaceOrCreate(null, retweetRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void undoRetweet(@Positive @PathVariable Long id) {
        retweetService.deleteById(id);
    }
}