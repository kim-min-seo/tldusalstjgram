package com.minse0.tldusalstjgram.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.minse0.tldusalstjgram.comment.domain.Comment;
import com.minse0.tldusalstjgram.comment.repository.CommentRepository;
import com.minse0.tldusalstjgram.post.domain.Post;
import com.minse0.tldusalstjgram.post.repository.PostRepository;

@Service
public class CommentService {
	
	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	
	public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
	}
	
	public void addComment(long postId, String commentText) {
		Post post = postRepository.findById(postId).orElseThrow();
		Comment comment = new Comment();
		comment.setPost(post);
		comment.setComments(commentText);
		commentRepository.save(comment);
	}
	
	public void deleteComment(long commentId) {
		Comment comment = commentRepository.findById(commentId).orElseThrow();
		commentRepository.delete(comment);
	}
	public List<Post> getAllPostsWithComments() {
        return postRepository.findAll(); 
    }
	
	public List<Comment> getCommetnsByPost(long postId){
		return commentRepository.findByPostId(postId);
	}
}
