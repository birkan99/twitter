package com.workintech.twitter.repository;

import com.workintech.twitter.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByTweetIdAndUserId(Long tweetId, Long userId); //
    boolean existsByTweetIdAndUserId(Long tweetId, Long userId); //

    @Modifying
    @Transactional
    void deleteByTweetIdAndUserId(Long tweetId, Long userId); //
}