package com.workintech.twitter.service;

import com.workintech.twitter.dto.patch.TweetPatchRequestDto;
import com.workintech.twitter.dto.request.TweetRequestDto;
import com.workintech.twitter.dto.response.TweetResponseDto;
import com.workintech.twitter.dto.response.UserResponseDto;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UserRepository; // Referans için eklendi
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository; // ID üzerinden referans bağlamak için
    private final UserService userService;

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
        // 1. Giriş yapan kullanıcının DTO'sunu alıyoruz
        UserResponseDto loggedInUser = userService.getLoggedInUserDto();

        // 2. Sadece ID'yi kullanarak referans (proxy) oluşturuyoruz
        User userRef = userRepository.getReferenceById(loggedInUser.id());

        Tweet tweet = Tweet.builder()
                .content(dto.content())
                .user(userRef) // Entity sızdırmadan ID bağladık
                .createdAt(OffsetDateTime.now())
                .build();

        return mapToResponse(tweetRepository.save(tweet));
    }

    @Override
    @Transactional
    public TweetResponseDto replaceOrCreate(Long id, TweetRequestDto dto) {
        UserResponseDto loggedInUser = userService.getLoggedInUserDto();

        Tweet tweet = tweetRepository.findById(id).orElse(new Tweet());
        tweet.setId(id);
        tweet.setContent(dto.content());
        tweet.setUser(userRepository.getReferenceById(loggedInUser.id()));

        if(tweet.getCreatedAt() == null) tweet.setCreatedAt(OffsetDateTime.now());

        return mapToResponse(tweetRepository.save(tweet));
    }

    @Override
    @Transactional
    public TweetResponseDto update(Long id, TweetPatchRequestDto dto) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tweet bulunamadı"));

        // GÜVENLİK KONTROLÜ: DTO'dan gelen ID ile tweet sahibinin ID'sini karşılaştırıyoruz
        UserResponseDto loggedInUser = userService.getLoggedInUserDto();
        if(!tweet.getUser().getId().equals(loggedInUser.id())) {
            throw new RuntimeException("Bu tweeti güncelleme yetkiniz yok!");
        }

        if(dto.content() != null) tweet.setContent(dto.content());

        return mapToResponse(tweetRepository.save(tweet));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tweet bulunamadı"));

        // GÜVENLİK KONTROLÜ
        UserResponseDto loggedInUser = userService.getLoggedInUserDto();
        if(!tweet.getUser().getId().equals(loggedInUser.id())) {
            throw new RuntimeException("Bu tweeti silme yetkiniz yok!");
        }

        tweetRepository.delete(tweet);
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