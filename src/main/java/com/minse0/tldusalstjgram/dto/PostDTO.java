package com.minse0.tldusalstjgram.dto;

import java.util.List;

import com.minse0.tldusalstjgram.comment.domain.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {

    private long id;
    private long userId;
    private String loginId;

    private int likeCount;
    private boolean isLike;

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
}
