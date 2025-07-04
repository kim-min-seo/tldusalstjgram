package com.minse0.tldusalstjgram.dto;

import java.util.List;

import com.minse0.tldusalstjgram.comment.domain.Comment;
import com.minse0.tldusalstjgram.post.domain.Post;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PostDTO {
	
	 	private long id;
	 	private long userId;
	 	private String loginId;
	 	
	 	private int likeCount;
	 	private boolean isLike;
	 	//댓글 목록
	 	private List<Comment> commentList;
	 	
	    private String caption;
	    private String contents;
	    private String music;
	    private String tagPeople;
	    private String location;
	    private String audience;
	    private String imagePath;
	    private String nickname;
	    private List<Comment> comments;
	    
	    public PostDTO(Post post, String nickname, List<Comment> comments) {
	        this.id = post.getId();
	        this.caption = post.getCaption();
	        this.contents = post.getContents();
	        this.music = post.getMusic();
	        this.tagPeople = post.getTagPeople();
	        this.location = post.getLocation();
	        this.audience = post.getAudience();
	        this.imagePath = post.getImagePath();
	        this.nickname = nickname;  
	        this.comments = comments;
	    }
}
