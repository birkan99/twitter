package com.workintech.twitter.service;

import com.workintech.twitter.dto.patch.LikePatchRequestDto;
import com.workintech.twitter.dto.request.LikeRequestDto;
import com.workintech.twitter.dto.response.LikeResponseDto;
import com.workintech.twitter.dto.response.UserResponseDto;
import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exceptions.ConflictException;
import com.workintech.twitter.exceptions.ForbiddenException;
import com.workintech.twitter.exceptions.NotFoundException;
import com.workintech.twitter.repository.LikeRepository;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public List<LikeResponseDto> getAll() {
        return likeRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public LikeResponseDto findById(Long id) {
        return likeRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new NotFoundException(id + " ID'li beğeni kaydı bulunamadı."));
    }

    @Override
    @Transactional
    public LikeResponseDto create(LikeRequestDto dto) {
        UserResponseDto loggedInUser = userService.getLoggedInUserDto();

        // MANTIK: Aynı kullanıcı aynı tweeti tekrar beğenemez
        if(likeRepository.existsByTweetIdAndUserId(dto.tweetId(), loggedInUser.id())) {
            throw new ConflictException("Bu tweeti zaten beğendiniz.");
        }

        Like like = new Like();
        like.setTweet(tweetRepository.getReferenceById(dto.tweetId()));
        like.setUser(userRepository.getReferenceById(loggedInUser.id()));
        like.setCreatedAt(OffsetDateTime.now());

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
        Like like = likeRepository.findById(id).orElseThrow(() -> new RuntimeException("Like not found"));
        return mapToResponse(likeRepository.save(like));
    }

    @Override
    public void deleteById(Long id) {
        Like like = likeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Beğeni bulunamadı"));

        UserResponseDto loggedInUser = userService.getLoggedInUserDto();
        // GÜVENLİK: Başkasının beğenisini kaldıramazsınız
        if(!like.getUser().getId().equals(loggedInUser.id())) {
            throw new ForbiddenException("Bu işlemi yapma yetkiniz yok.");
        }
        likeRepository.delete(like);
    }

    private LikeResponseDto mapToResponse(Like l) {
        if (l == null) return null;

        return new LikeResponseDto(
                l.getId(),
                l.getTweet() != null ? l.getTweet().getId() : null,
                l.getUser() != null ? l.getUser().getId() : null
        );
    }
}