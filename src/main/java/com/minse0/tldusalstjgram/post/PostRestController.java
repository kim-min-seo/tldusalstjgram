package com.minse0.tldusalstjgram.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.minse0.tldusalstjgram.post.service.PostService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@RestController
public class PostRestController {
	
	private PostService postService;
	
	public PostRestController(PostService postService) {
		this.postService = postService;
	}
	
	@PostMapping("/create")
	public Map<String, String> createPost(
				@RequestParam String caption,
			    @RequestParam String contents,
			    @RequestParam String music,
			    @RequestParam String tagPeople,
			    @RequestParam String location,
			    @RequestParam String audience,
			    @RequestParam(required=false) MultipartFile imageFile,
			    HttpSession session){
		
		long userId = (Long) session.getAttribute("userId");
		
		Map<String, String> resultMap = new HashMap<>();
		if(postService.addPost(userId, caption, contents, music, tagPeople, location, audience,imageFile)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
}

