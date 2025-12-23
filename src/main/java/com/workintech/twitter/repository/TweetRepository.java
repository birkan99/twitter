package com.workintech.twitter.repository;

import com.workintech.twitter.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
    // Bir kullanıcının tüm tweetlerini getirmek için kullanılır
    List<Tweet> findAllByUserId(Long userId); //
}