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
	public void deleteLikesByPostId(long postId) {
        likeRepository.deleteByPostId(postId);
    }
	
	public boolean toggleLike(long userId, long postId) {
        Like existingLike = likeRepository.findByUserIdAndPostId(userId, postId);
        if (existingLike != null) {
            likeRepository.delete(existingLike);
            return false; 
        } else {
            Like like = Like.builder()
                    .userId(userId)
                    .postId(postId)
                    .createdAt(LocalDateTime.now())
                    .build();
            try {
                likeRepository.save(like);
                return true; 
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
