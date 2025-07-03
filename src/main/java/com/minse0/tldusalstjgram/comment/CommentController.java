package com.minse0.tldusalstjgram.comment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.minse0.tldusalstjgram.comment.service.CommentService;



@RequestMapping("/post/list")
@Controller
public class CommentController {
	
	private final CommentService commentService;
	
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@PostMapping("/comments")
	public String addComment(@RequestParam long postId, @RequestParam String comments, Model model) {
		
		commentService.addComment(postId, comments);
		
		model.addAttribute("post", commentService.getAllPostsWithComments());
		
		return "list";
	
	}
}
