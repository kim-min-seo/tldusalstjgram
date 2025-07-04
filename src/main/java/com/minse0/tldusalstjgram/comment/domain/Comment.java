package com.minse0.tldusalstjgram.comment.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.minse0.tldusalstjgram.post.domain.Post;
import com.minse0.tldusalstjgram.user.domain.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="`user_comments`")
@Entity
@Getter
@Setter
public class Comment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
	private User user;

	
	@ManyToOne
    @JoinColumn(name = "postId", referencedColumnName = "id", nullable = false)
    private Post post;
	private String comments;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
