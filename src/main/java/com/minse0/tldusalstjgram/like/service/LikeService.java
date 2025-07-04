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
	
	public boolean addLike(
			long userId
			, long postId
			) {
		Like like = Like.builder()
		.userId(userId)
		.postId(postId)
		.createdAt(LocalDateTime.now()) 
		.build();
		
		try {
			likeRepository.save(like);
        } catch (PersistenceException e) {
            return false;
        }

        return true;
		
		
	}
	
	public int likeCountByPostId(long postId) {
		return likeRepository.countBypostId(postId);
	}
}
