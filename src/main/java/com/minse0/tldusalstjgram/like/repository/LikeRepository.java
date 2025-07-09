package com.minse0.tldusalstjgram.like.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.minse0.tldusalstjgram.like.domain.Like;

import jakarta.transaction.Transactional;

public interface LikeRepository extends JpaRepository<Like, Long> {

	public int countBypostId(long postId);
	
	public Like findByUserIdAndPostId(long userId, long postId);
    public boolean existsByUserIdAndPostId(long userId, long postId);
    
    @Modifying
    @Transactional
 
    public void deleteByPostId(@Param("postId") long postId);



}
