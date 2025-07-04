package com.minse0.tldusalstjgram.comment;





import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.minse0.tldusalstjgram.comment.service.CommentService;
import com.minse0.tldusalstjgram.user.domain.User;
import com.minse0.tldusalstjgram.user.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post/list")
@Controller
public class CommentController {

    private final CommentService commentService;
    private final UserRepository userRepository;

    public CommentController(CommentService commentService, UserRepository userRepository) {
        this.commentService = commentService;
        this.userRepository = userRepository;
    }

    @PostMapping("/comments")
    public String addComment(
    		 @RequestParam long postId
    		,@RequestParam String comments
    		
    		, HttpSession session
    		, RedirectAttributes redirectAttributes
    		, Model model) {
    	
    	Long userId = (Long) session.getAttribute("userId");
        
    	if (userId == null) {
            redirectAttributes.addFlashAttribute("error", "로그인 후 댓글을 작성할 수 있습니다.");
            return "redirect:/user/login-view";  
        }
       
    	boolean isAdded = commentService.addComment(postId, comments, userId);
        
    	User user = userRepository.findById(userId);
    	 if (user == null) {
    	        redirectAttributes.addFlashAttribute("error", "유저 정보를 찾을 수 없습니다.");
    	        return "redirect:/user/login-view"; 
    	    }
    	model.addAttribute("user", user);
        
        if (isAdded) {
            redirectAttributes.addAttribute("postId", postId);  
            return "redirect:/post/list-view";  
        } else {
            redirectAttributes.addFlashAttribute("error", "댓글 추가 실패");
            return "redirect:/post/create";  
        }
    }
}
