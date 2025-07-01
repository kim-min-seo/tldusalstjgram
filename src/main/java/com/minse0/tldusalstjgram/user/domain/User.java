package com.minse0.tldusalstjgram.user.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	private int id;
	private String loginId;
	private String password;
	private String name;
	private String nickname;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	
	
	
	
}
