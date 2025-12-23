package com.workintech.twitter.repository;

import com.workintech.twitter.entity.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RetweetRepository extends JpaRepository<Retweet, Long> {
    Optional<Retweet> findByOriginalTweetIdAndUserId(Long originalTweetId, Long userId); //

    @Modifying
    @Transactional
    void deleteByOriginalTweetIdAndUserId(Long originalTweetId, Long userId); //
}