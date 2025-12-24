package com.workintech.twitter.service;

import com.workintech.twitter.dto.patch.TweetPatchRequestDto;
import com.workintech.twitter.dto.request.TweetRequestDto;
import com.workintech.twitter.dto.response.TweetResponseDto;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exceptions.NotFoundException;
import com.workintech.twitter.exceptions.UserNotFoundException;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UserRepository;
import com.workintech.twitter.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @Override
    public List<TweetResponseDto> getAll() {
        return tweetRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<TweetResponseDto> findAllByUserId(Long userId) {
        return tweetRepository.findAllByUserId(userId).stream().map(this::mapToResponse).toList();
    }

    @Override
    public TweetResponseDto findById(Long id) {
        return tweetRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Tweet bulunamadı: " + id));
    }

    @Override
    @Transactional
    public TweetResponseDto create(TweetRequestDto dto) {
        // 1. JWT ile gelen kullanıcıyı SecurityContext'ten güvenli bir şekilde al
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email;
        if (principal instanceof CustomUserDetails) {
            email = ((CustomUserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        // 2. Email ile gerçek kullanıcıyı veritabanından getir
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Oturum açan kullanıcı bulunamadı!"));

        // 3. Tweet'i bu gerçek kullanıcıyla ilişkilendir
        Tweet tweet = Tweet.builder()
                .content(dto.content())
                .user(user)
                .createdAt(OffsetDateTime.now())
                .build();

        return mapToResponse(tweetRepository.save(tweet));
    }

    @Override
    @Transactional
    public TweetResponseDto update(Long id, TweetPatchRequestDto dto) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tweet bulunamadı"));

        // GÜVENLİK: Sadece tweet sahibi güncelleyebilir
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!tweet.getUser().getEmail().equals(currentEmail)) {
            throw new RuntimeException("Bu tweeti güncelleme yetkiniz yok!");
        }

        if (dto.content() != null) tweet.setContent(dto.content());
        return mapToResponse(tweetRepository.save(tweet));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tweet bulunamadı"));

        // GÜVENLİK: Sadece tweet sahibi silebilir
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!tweet.getUser().getEmail().equals(currentEmail)) {
            throw new RuntimeException("Bu tweeti silme yetkiniz yok!");
        }

        tweetRepository.delete(tweet);
    }

    @Override
    @Transactional
    public TweetResponseDto replaceOrCreate(Long id, TweetRequestDto dto) {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(currentEmail).orElseThrow();

        Tweet tweet = tweetRepository.findById(id).orElse(new Tweet());
        tweet.setId(id);
        tweet.setContent(dto.content());
        tweet.setUser(user);
        if (tweet.getCreatedAt() == null) tweet.setCreatedAt(OffsetDateTime.now());

        return mapToResponse(tweetRepository.save(tweet));
    }

    private TweetResponseDto mapToResponse(Tweet tweet) {
        return new TweetResponseDto(
                tweet.getId(),
                tweet.getContent(),
                tweet.getCreatedAt(),
                tweet.getUser() != null ? tweet.getUser().getId() : null,
                tweet.getUser() != null ? tweet.getUser().getDisplayName() : "Anonim"
        );
    }
}