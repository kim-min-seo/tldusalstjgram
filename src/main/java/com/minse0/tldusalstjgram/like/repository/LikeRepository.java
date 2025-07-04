package com.minse0.tldusalstjgram.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minse0.tldusalstjgram.like.domain.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

	public int countBypostId(long postId);

}
