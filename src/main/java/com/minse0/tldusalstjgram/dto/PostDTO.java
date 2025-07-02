package com.minse0.tldusalstjgram.dto;

import com.minse0.tldusalstjgram.post.domain.Post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
	
	 	private long id;
	    private String caption;
	    private String contents;
	    private String music;
	    private String tagPeople;
	    private String location;
	    private String audience;
	    private String imagePath;
	    private String nickname;
	    
	    public PostDTO(Post post, String nickname) {
	        this.id = post.getId();
	        this.caption = post.getCaption();
	        this.contents = post.getContents();
	        this.music = post.getMusic();
	        this.tagPeople = post.getTagPeople();
	        this.location = post.getLocation();
	        this.audience = post.getAudience();
	        this.imagePath = post.getImagePath();
	        this.nickname = nickname;  // nickname 할당
	    }
}
