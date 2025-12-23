package com.workintech.twitter.service;

import com.workintech.twitter.dto.patch.UserPatchRequestDto;
import com.workintech.twitter.dto.request.UserRequestDto;
import com.workintech.twitter.dto.response.UserResponseDto;
import com.workintech.twitter.entity.User;

import com.workintech.twitter.exceptions.UserNotFoundException;
import com.workintech.twitter.mapper.UserMapper;
import com.workintech.twitter.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponseDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDto)
                .toList();
    }

    @Override
    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found, id: " + id));

        return userMapper.toResponseDto(user);
    }

    @Override
    public UserResponseDto create(UserRequestDto dto) {

        if (userRepository.existsByEmail(dto.email()))
            throw new RuntimeException("Email already exists");

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.password()));

        user = userRepository.save(user);
        return userMapper.toResponseDto(user);
    }


    @Override
    public UserResponseDto update(Long id,  @Valid UserPatchRequestDto dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found, id: " + id));

        if (dto.password() != null)
            user.setPassword(passwordEncoder.encode(dto.password()));

        userMapper.updateEntity(user, dto);
        userRepository.save(user);

        return userMapper.toResponseDto(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserResponseDto getLoggedInUserDto() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            throw new RuntimeException("Giriş yapmış kullanıcı bulunamadı.");
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı: " + email));

        return userMapper.toResponseDto(user);
    }
}
