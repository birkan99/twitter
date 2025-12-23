package com.workintech.twitter.service;

import com.workintech.twitter.dto.patch.CommentPatchRequestDto;
import com.workintech.twitter.dto.request.CommentRequestDto;
import com.workintech.twitter.dto.response.CommentResponseDto;
import com.workintech.twitter.dto.response.UserResponseDto;
import com.workintech.twitter.entity.Comment;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.repository.CommentRepository;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository; // Entity referansı almak için eklendi
    private final UserService userService;

    @Override
    public List<CommentResponseDto> getAll() {
        return commentRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public CommentResponseDto findById(Long id) {
        return commentRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Comment not found: " + id));
    }

    @Override
    @Transactional
    public CommentResponseDto create(CommentRequestDto dto) {
        // 1. Dışarıdan sadece DTO alıyoruz (Güvenli)
        UserResponseDto loggedInUser = userService.getLoggedInUserDto();

        // 2. Veritabanı ilişkisi için Entity referansları oluşturuyoruz (Proxy)
        // getReferenceById veritabanına gidip tüm veriyi çekmez, sadece ID'yi bağlar.
        User userRef = userRepository.getReferenceById(loggedInUser.id());
        Tweet tweetRef = tweetRepository.getReferenceById(dto.tweetId());

        Comment comment = Comment.builder()
                .content(dto.content())
                .tweet(tweetRef)
                .user(userRef)
                .createdAt(OffsetDateTime.now())
                .build();

        return mapToResponse(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public CommentResponseDto replaceOrCreate(Long id, CommentRequestDto dto) {
        UserResponseDto loggedInUser = userService.getLoggedInUserDto();

        Comment comment = commentRepository.findById(id).orElse(new Comment());
        comment.setId(id);
        comment.setContent(dto.content());
        comment.setTweet(tweetRepository.getReferenceById(dto.tweetId()));
        comment.setUser(userRepository.getReferenceById(loggedInUser.id()));

        if (comment.getCreatedAt() == null) {
            comment.setCreatedAt(OffsetDateTime.now());
        }

        return mapToResponse(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public CommentResponseDto update(Long id, CommentPatchRequestDto dto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (dto.content() != null) {
            comment.setContent(dto.content());
        }

        return mapToResponse(commentRepository.save(comment));
    }

    @Override
    public void deleteById(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new RuntimeException("Comment not found");
        }
        commentRepository.deleteById(id);
    }

    private CommentResponseDto mapToResponse(Comment c) {
        // Null kontrolü yaparak güvenli bir dönüş sağlıyoruz
        return new CommentResponseDto(
                c.getId(),
                c.getContent(),
                c.getTweet() != null ? c.getTweet().getId() : null,
                c.getUser() != null ? c.getUser().getId() : null,
                c.getUser() != null ? c.getUser().getDisplayName() : "Unknown User",
                c.getCreatedAt()
        );
    }
}