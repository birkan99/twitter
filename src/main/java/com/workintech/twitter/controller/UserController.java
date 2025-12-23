package com.workintech.twitter.controller;

import com.workintech.twitter.dto.patch.UserPatchRequestDto;
import com.workintech.twitter.dto.request.UserRequestDto;
import com.workintech.twitter.dto.response.UserResponseDto;
import com.workintech.twitter.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    // GET ALL
    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.getAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public UserResponseDto findById(
            @Positive @PathVariable Long id
    ) {
        return userService.findById(id);
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto create(
            @Valid @RequestBody UserRequestDto userRequestDto
    ) {
        return userService.create(userRequestDto);
    }

    // PUT
    @PutMapping("/{id}")
    public UserResponseDto replaceOrCreate(
            @Positive @PathVariable Long id,
            @Valid @RequestBody UserRequestDto userRequestDto
    ) {
        return userService.create(userRequestDto);
    }

    // PATCH
    @PatchMapping("/{id}")
    public UserResponseDto update(
            @Positive @PathVariable Long id,
            @Valid @RequestBody UserPatchRequestDto userPatchRequestDto
    ) {
        return userService.update(id, userPatchRequestDto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Positive @PathVariable Long id
    ) {
        userService.deleteById(id);
    }
}
