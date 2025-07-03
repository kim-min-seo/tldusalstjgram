package com.minse0.tldusalstjgram.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.minse0.tldusalstjgram.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	
	// WHERE `userId` = #{}
		//public List<Post> findByUserId(long userId, Sort sort);
		
		public Page<Post> findAll(Pageable pageable);
}
