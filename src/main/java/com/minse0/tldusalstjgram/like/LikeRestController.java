package com.minse0.tldusalstjgram.like;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minse0.tldusalstjgram.like.service.LikeService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/post")
public class LikeRestController {
	
	private LikeService likeService;
	
	public LikeRestController(LikeService likeService) {
        this.likeService = likeService;
    }
	
	
	@PostMapping("/like")
	public Map<String, Object> like(
			@RequestParam long postId
			, HttpSession session
			){
		
		Map<String, Object> resultMap = new HashMap<>();
		
		
		 try {
	            Long userId = (Long) session.getAttribute("userId");

	            if (userId == null) {
	                resultMap.put("result", "fail");
	                resultMap.put("reason", "로그인 안함");
	                return resultMap;
	            }

	            boolean isLiked = likeService.toggleLike(userId, postId);
	            int likeCount = likeService.likeCountByPostId(postId);

	            resultMap.put("result", "success");
	            resultMap.put("isLike", isLiked);
	            resultMap.put("likeCount", likeCount);

	        } catch (Exception e) {
	            resultMap.put("result", "fail");
	            resultMap.put("reason", e.getMessage());
	        }

	        return resultMap;
	}
}
