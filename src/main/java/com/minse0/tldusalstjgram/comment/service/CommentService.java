package com.minse0.tldusalstjgram.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.minse0.tldusalstjgram.comment.domain.Comment;
import com.minse0.tldusalstjgram.comment.repository.CommentRepository;
import com.minse0.tldusalstjgram.post.domain.Post;
import com.minse0.tldusalstjgram.post.repository.PostRepository;
import com.minse0.tldusalstjgram.user.domain.User;
import com.minse0.tldusalstjgram.user.repository.UserRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // 댓글 추가 (boolean 반환)
    public boolean addComment(long postId, String commentText, long userId) {
    	Post post = postRepository.findById(postId).orElse(null);
    	    
        if (post == null) {
            return false; // 게시글이 없으면 실패
        }
        User user = userRepository.findById(userId);
        if (user == null) {
            return false; // 유저가 없으면 실패
        }
        
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setComments(commentText);
        commentRepository.save(comment);
        try {
            commentRepository.save(comment);
        } catch (Exception e) {
            return false; // 예외 발생 시 실패
        }
        return true; // 댓글 추가 성공
    }

    // 댓글 삭제 (boolean 반환)
    public boolean deleteComment(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            return false; // 댓글이 없으면 실패
        }

        commentRepository.delete(comment);
        return true; // 댓글 삭제 성공
    }

    // 특정 게시글의 댓글 조회
    public List<Comment> getCommentsByPost(long postId) {
        return commentRepository.findByPostId(postId);
    }

    // 댓글 수정 (boolean 반환)
    public boolean updateComment(long commentId, String newCommentText) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            return false; // 댓글이 없으면 실패
        }

        comment.setComments(newCommentText);
        commentRepository.save(comment);
        return true; // 댓글 수정 성공
    }
}
