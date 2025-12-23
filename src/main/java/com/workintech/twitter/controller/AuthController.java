package com.workintech.twitter.controller;

import com.workintech.twitter.dto.request.LoginRequestDto;
import com.workintech.twitter.dto.request.RegisterRequestDto;
import com.workintech.twitter.dto.response.AuthResponseDto;
import com.workintech.twitter.dto.response.RegisterResponseDto;
import com.workintech.twitter.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    // Manager ve JwtService yerine direkt AuthService'i çağırıyoruz
    private final AuthService authService;

    @PostMapping("/register")
    public RegisterResponseDto register(@RequestBody @Valid RegisterRequestDto dto) {
        return authService.register(dto);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody @Valid LoginRequestDto dto) {
        // Tüm mantık (authenticate, user bulma, token üretme) Service içinde
        return authService.login(dto);
    }
}