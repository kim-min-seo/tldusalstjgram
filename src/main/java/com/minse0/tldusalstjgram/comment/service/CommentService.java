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

   
    public boolean addComment(long postId, String commentText, long userId) {
    	Post post = postRepository.findById(postId).orElse(null);
    	    
        if (post == null) {
            return false; 
        }
        User user = userRepository.findById(userId);
        if (user == null) {
            return false; 
        }
        
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setComments(commentText);
        
        try {
            commentRepository.save(comment);
        } catch (Exception e) {
            return false; 
        }
        return true; 
    }

    
    public boolean deleteComment(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            return false; 
        }

        commentRepository.delete(comment);
        return true; 
    }

    
    public List<Comment> getCommentsByPost(long postId) {
        return commentRepository.findByPostId(postId);
    }

    

}
