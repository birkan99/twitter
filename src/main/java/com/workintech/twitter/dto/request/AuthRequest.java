package com.workintech.twitter.dto.request;

public record AuthRequest(
        String username,
        String password,
        String email
) {}