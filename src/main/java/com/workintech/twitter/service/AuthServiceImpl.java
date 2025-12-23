package com.workintech.twitter.service;

import com.workintech.twitter.dto.request.LoginRequestDto;
import com.workintech.twitter.dto.request.RegisterRequestDto;
import com.workintech.twitter.dto.response.AuthResponseDto;
import com.workintech.twitter.dto.response.RegisterResponseDto;
import com.workintech.twitter.dto.response.UserResponseDto;
import com.workintech.twitter.entity.Role;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.repository.UserRepository;
import com.workintech.twitter.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    @Transactional
    public RegisterResponseDto register(RegisterRequestDto dto) {
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Hata: Bu email adresi zaten kayıtlı!");
        }

        User user = User.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .displayName(dto.displayName())
                .role(Role.USER) // Enum kullanımı daha güvenli
                .createdAt(OffsetDateTime.now())
                .build();

        userRepository.save(user);

        return new RegisterResponseDto(user.getEmail(), "Kullanıcı kaydı başarıyla oluşturuldu.");
    }
/*
    @Override
    public AuthResponseDto login(LoginRequestDto dto) {
        // AuthenticationManager şifreyi otomatik kontrol eder
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
        );

        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        String jwtToken = jwtService.generateToken(String.valueOf(user));

// AuthServiceImpl içindeki login metodunun ilgili kısmı:

        UserResponseDto userResponse = new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getDisplayName(),
                user.getCreatedAt()
        );

        return new AuthResponseDto(jwtToken, userResponse);
    }

 */

    @Override
    public AuthResponseDto login(LoginRequestDto dto) {
        // 1. Kimlik doğrulama
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
        );

        // 2. Kullanıcıyı bul
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        // 3. Token Üret (Burada JwtService kullandığından emin ol, JwtUtil değil)
        String jwtToken = jwtService.generateToken(user.getEmail());

        // 4. Response hazırlama
        UserResponseDto userResponse = new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getDisplayName(),
                user.getCreatedAt()
        );

        return new AuthResponseDto(jwtToken, userResponse);
    }

}