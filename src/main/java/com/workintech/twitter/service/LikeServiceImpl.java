package com.workintech.twitter.service;

import com.workintech.twitter.dto.patch.LikePatchRequestDto;
import com.workintech.twitter.dto.request.LikeRequestDto;
import com.workintech.twitter.dto.response.LikeResponseDto;
import com.workintech.twitter.dto.response.UserResponseDto;
import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.repository.LikeRepository;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UserRepository; // Eklendi
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository; // ID üzerinden referans almak için eklendi
    private final UserService userService;

    @Override
    public List<LikeResponseDto> getAll() {
        return likeRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public LikeResponseDto findById(Long id) {
        return likeRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Like not found: " + id));
    }

    @Override
    @Transactional
    public LikeResponseDto create(LikeRequestDto dto) {
        // 1. Dış dünyadan sadece DTO alıyoruz
        UserResponseDto loggedInUser = userService.getLoggedInUserDto();

        // 2. Entity referanslarını alıyoruz (Veritabanına SELECT atmaz, sadece ID'yi bağlar)
        User userRef = userRepository.getReferenceById(loggedInUser.id());
        Tweet tweetRef = tweetRepository.getReferenceById(dto.tweetId());

        Like like = Like.builder()
                .tweet(tweetRef)
                .user(userRef)
                .createdAt(OffsetDateTime.now())
                .build();

        return mapToResponse(likeRepository.save(like));
    }

    @Override
    @Transactional
    public LikeResponseDto replaceOrCreate(Long id, LikeRequestDto dto) {
        UserResponseDto loggedInUser = userService.getLoggedInUserDto();

        Like like = likeRepository.findById(id).orElse(new Like());
        like.setId(id);
        like.setTweet(tweetRepository.getReferenceById(dto.tweetId()));
        like.setUser(userRepository.getReferenceById(loggedInUser.id()));

        if (like.getCreatedAt() == null) {
            like.setCreatedAt(OffsetDateTime.now());
        }

        return mapToResponse(likeRepository.save(like));
    }

    @Override
    @Transactional
    public LikeResponseDto update(Long id, LikePatchRequestDto dto) {
        // Like genelde güncellenen bir şey değildir (ya vardır ya yoktur)
        // Ancak metodunuzun çalışması için mevcut Like'ı bulup kaydetmeniz yeterli
        Like like = likeRepository.findById(id).orElseThrow(() -> new RuntimeException("Like not found"));
        return mapToResponse(likeRepository.save(like));
    }

    @Override
    public void deleteById(Long id) {
        if (!likeRepository.existsById(id)) {
            throw new RuntimeException("Like not found");
        }
        likeRepository.deleteById(id);
    }

    private LikeResponseDto mapToResponse(Like l) {
        return new LikeResponseDto(
                l.getId(),
                l.getTweet() != null ? l.getTweet().getId() : null,
                l.getUser() != null ? l.getUser().getId() : null
        );
    }
}