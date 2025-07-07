package com.minse0.tldusalstjgram.like.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.minse0.tldusalstjgram.like.domain.Like;
import com.minse0.tldusalstjgram.like.repository.LikeRepository;

import jakarta.persistence.PersistenceException;

@Service
public class LikeService {
	
	private final LikeRepository likeRepository;
	
	public LikeService(LikeRepository likeRepository) {
		this.likeRepository = likeRepository;
	}
	
	public boolean toggleLike(long userId, long postId) {
        Like existingLike = likeRepository.findByUserIdAndPostId(userId, postId);
        if (existingLike != null) {
            likeRepository.delete(existingLike);
            return false; // 좋아요 취소
        } else {
            Like like = Like.builder()
                    .userId(userId)
                    .postId(postId)
                    .createdAt(LocalDateTime.now())
                    .build();
            try {
                likeRepository.save(like);
                return true; // 좋아요 추가
            } catch (PersistenceException e) {
                return false;
            }
        }
    }
	
	public int likeCountByPostId(long postId) {
		return likeRepository.countBypostId(postId);
	}
	
	public boolean isLikedByUser(long userId, long postId) {
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }
}
