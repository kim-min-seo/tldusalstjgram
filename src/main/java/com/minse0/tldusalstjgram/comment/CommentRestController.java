package com.minse0.tldusalstjgram.comment;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minse0.tldusalstjgram.comment.domain.Comment;
import com.minse0.tldusalstjgram.comment.service.CommentService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/comments")
public class CommentRestController {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public Map<String, String> addComment(@RequestParam long postId, @RequestParam String comments, HttpSession session) {
        Map<String, String> resultMap = new HashMap<>();
        
        long userId = (Long) session.getAttribute("userId");

        boolean isAdded = commentService.addComment(postId, comments, userId);
        
        if (isAdded) {
            resultMap.put("result", "success");
            resultMap.put("userId", String.valueOf(userId));  // userId 세션에 저장된 값
        } else {
            resultMap.put("result", "fail");
        }
        
        return resultMap;
    }

    @GetMapping("/list")
    public List<Comment> getCommentsByPost(@RequestParam long postId) {
        return commentService.getCommentsByPost(postId);
    }

    @DeleteMapping("/remove")
    public Map<String, String> deleteComment(@RequestParam long commentId, HttpSession session) {
        Map<String, String> resultMap = new HashMap<>();
        
        long userId = (Long) session.getAttribute("userId");

        boolean isDeleted = commentService.deleteComment(commentId);
        
        if (isDeleted) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }

        return resultMap;
    }

    @PutMapping("/edit")
    public Map<String, String> updateComment(@RequestParam long commentId, @RequestParam String newCommentText, HttpSession session) {
        Map<String, String> resultMap = new HashMap<>();
        
        long userId = (Long) session.getAttribute("userId");

        boolean isUpdated = commentService.updateComment(commentId, newCommentText);
        
        if (isUpdated) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }

        return resultMap;
    }
}
