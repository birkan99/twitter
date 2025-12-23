package com.workintech.twitter.controller;

import com.workintech.twitter.dto.patch.TweetPatchRequestDto;
import com.workintech.twitter.dto.request.TweetRequestDto;
import com.workintech.twitter.dto.response.TweetResponseDto;
import com.workintech.twitter.service.TweetService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tweet")
@RequiredArgsConstructor
@Validated
public class TweetController {

    @Autowired
    private final TweetService tweetService;

    @GetMapping("/{id}")
    public TweetResponseDto findById(@Positive @PathVariable Long id) {
        return tweetService.findById(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public TweetResponseDto create(@Valid @RequestBody TweetRequestDto tweetRequestDto){

        return tweetService.create(tweetRequestDto);
    }

    @PutMapping("/{id}")
    public TweetResponseDto replaceOrCreate(@Positive @PathVariable("id") Long id,
                                            @Valid @RequestBody TweetRequestDto tweetRequestDto){

        return tweetService.replaceOrCreate(id, tweetRequestDto);
    }

    @PatchMapping("/{id}") //
    public TweetResponseDto update(@Positive @PathVariable("id") Long id,
                                   @Valid @RequestBody TweetPatchRequestDto tweetPatchRequestDto){

        return tweetService.update(id, tweetPatchRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void delete(@Positive @PathVariable("id") Long id){

        tweetService.deleteById(id);
    }
}
