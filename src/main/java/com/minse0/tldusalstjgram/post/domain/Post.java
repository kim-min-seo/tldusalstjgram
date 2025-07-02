package com.minse0.tldusalstjgram.post.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="`post`")
@Entity
@Getter
@Setter
public class Post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private long id;
	private long userId;
	private String contents;
	private String imagePath;
	private String caption;
	private String music;
	private String tagPeople;
	private String location;
	private String audience;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
