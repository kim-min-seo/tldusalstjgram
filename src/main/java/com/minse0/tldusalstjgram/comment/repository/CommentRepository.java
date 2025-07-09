package com.minse0.tldusalstjgram.comment.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.minse0.tldusalstjgram.comment.domain.Comment;

import jakarta.transaction.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	public List<Comment> findByPostId(long postId);
	
	@Modifying
	@Transactional
	
	public void deleteByPostId(@Param("postId") long postId);

	
	

}
