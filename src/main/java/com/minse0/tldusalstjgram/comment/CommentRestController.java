package com.minse0.tldusalstjgram.comment;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minse0.tldusalstjgram.comment.domain.Comment;
import com.minse0.tldusalstjgram.comment.repository.CommentRepository;
import com.minse0.tldusalstjgram.comment.service.CommentService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/comments")
public class CommentRestController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

    public CommentRestController(CommentService commentService, CommentRepository commentRepository) {
        this.commentService = commentService;
        this.commentRepository = commentRepository;
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
        
        // 세션에서 userId 가져오기
        Long userId = (Long) session.getAttribute("userId");

        // 로그인 여부 확인
        if (userId == null) {
            resultMap.put("result", "fail");
            resultMap.put("message", "로그인 후 사용 가능합니다.");
            return resultMap;
        }

        // 댓글 찾기
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            resultMap.put("result", "fail");
            resultMap.put("message", "댓글을 찾을 수 없습니다.");
            return resultMap;
        }

        // 댓글 작성자와 삭제 요청자가 동일한지 확인
        if (userId != comment.getUser().getId()) {
            resultMap.put("result", "fail");
            resultMap.put("message", "삭제 권한이 없습니다.");
            return resultMap;
        }

       
        boolean isDeleted = commentService.deleteComment(commentId);
        
        if (isDeleted) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }

        return resultMap;
    }


    
}
