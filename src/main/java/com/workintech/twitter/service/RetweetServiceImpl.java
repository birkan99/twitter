package com.workintech.twitter.service;

import com.workintech.twitter.dto.patch.RetweetPatchRequestDto;
import com.workintech.twitter.dto.request.RetweetRequestDto;
import com.workintech.twitter.dto.response.RetweetResponseDto;
import com.workintech.twitter.dto.response.UserResponseDto;
import com.workintech.twitter.entity.Retweet;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.repository.RetweetRepository;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UserRepository; // Eklendi
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RetweetServiceImpl implements RetweetService {
    private final RetweetRepository retweetRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository; // Referans oluşturmak için eklendi
    private final UserService userService;

    @Override
    public List<RetweetResponseDto> getAll() {
        return retweetRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public RetweetResponseDto findById(Long id) {
        return retweetRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Retweet bulunamadı: " + id));
    }

    @Transactional
    public RetweetResponseDto create(RetweetRequestDto dto) {
        // 1. Giriş yapan kullanıcının DTO bilgisini al (Entity sızmaz)
        UserResponseDto loggedInUser = userService.getLoggedInUserDto();

        // 2. İlişki kurmak için Proxy/Referans Entity oluştur (Performanslı ve Güvenli)
        User userRef = userRepository.getReferenceById(loggedInUser.id());
        Tweet tweetRef = tweetRepository.getReferenceById(dto.tweetId());

        Retweet rt = Retweet.builder()
                .originalTweet(tweetRef)
                .user(userRef)
                .createdAt(OffsetDateTime.now())
                .build();

        return mapToResponse(retweetRepository.save(rt));
    }

    @Override
    @Transactional
    public RetweetResponseDto replaceOrCreate(Long id, RetweetRequestDto dto) {
        UserResponseDto loggedInUser = userService.getLoggedInUserDto();

        Retweet rt = retweetRepository.findById(id).orElse(new Retweet());
        rt.setId(id);
        rt.setOriginalTweet(tweetRepository.getReferenceById(dto.tweetId()));
        rt.setUser(userRepository.getReferenceById(loggedInUser.id()));

        if (rt.getCreatedAt() == null) {
            rt.setCreatedAt(OffsetDateTime.now());
        }

        return mapToResponse(retweetRepository.save(rt));
    }

    @Override
    @Transactional
    public RetweetResponseDto update(Long id, RetweetPatchRequestDto dto) {
        Retweet rt = retweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Retweet bulunamadı"));
        // Retweet içeriği genelde değişmez ancak save metodunu çağırmak yeterli
        return mapToResponse(retweetRepository.save(rt));
    }

    @Override
    public void deleteById(Long id) {
        if (!retweetRepository.existsById(id)) {
            throw new RuntimeException("Retweet bulunamadı");
        }
        retweetRepository.deleteById(id);
    }

    private RetweetResponseDto mapToResponse(Retweet r) {
        return new RetweetResponseDto(
                r.getId(),
                r.getOriginalTweet() != null ? r.getOriginalTweet().getId() : null,
                r.getUser() != null ? r.getUser().getId() : null,
                r.getCreatedAt()
        );
    }
}