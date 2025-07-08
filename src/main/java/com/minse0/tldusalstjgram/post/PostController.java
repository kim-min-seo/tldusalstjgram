package com.minse0.tldusalstjgram.post;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.minse0.tldusalstjgram.comment.service.CommentService;
import com.minse0.tldusalstjgram.dto.PostDTO;
import com.minse0.tldusalstjgram.post.domain.Post;
import com.minse0.tldusalstjgram.post.service.PostService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@Controller
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/create")
    public String showCreateForm() {
        return "post/input";
    }

    @GetMapping("/list-view")
    public String getPostList(
            Model model, 
            HttpSession session,
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size  
    ) {
        long userId = (Long) session.getAttribute("userId");
        
       
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "createdAt"));
        
        List<PostDTO> postDTOs = postService.getPostLists(userId, pageable);
        
        
   
        model.addAttribute("posts", postDTOs);
        return "post/list";
    }

    @PostMapping("/create-post")
    public String createPost(
    		 @RequestParam String caption,
    		 @RequestParam String contents,
    		 @RequestParam String music,
    		 @RequestParam String tagPeople,
    		 @RequestParam String location,
    		 @RequestParam String audience,
    		 @RequestParam MultipartFile imageFile,
    		 	HttpSession session
    		 
    ) {
        long userId = (Long) session.getAttribute("userId");

        
        if (contents == null || contents.isEmpty()) {
            return "redirect:/post/create";  
        }

       
        if (postService.addPost(userId, caption, contents, music, tagPeople, location, audience, imageFile)) {
            return "redirect:/post/list-view";
        }

        
        return "redirect:/post/create";
    }
    @GetMapping("/update")
    public String showUpdateForm(@RequestParam long postId, Model model) {
        Post post = postService.getPost(postId);
        if (post == null) return "redirect:/post/list-view";
        model.addAttribute("post", post);
        return "post/input"; // 기존 작성 페이지 재사용
    }
    
    @PostMapping("/update-post")
    public String updatePost(
        @RequestParam long postId,
        @RequestParam String caption,
        @RequestParam String contents,
        @RequestParam String music,
        @RequestParam String tagPeople,
        @RequestParam String location,
        @RequestParam String audience,
        @RequestParam(required=false) MultipartFile imageFile,
        HttpSession session
    ) {
        long userId = (Long) session.getAttribute("userId");

        if (postService.updatePost(postId, userId, caption, contents, music, tagPeople, location, audience, imageFile)) {
            return "redirect:/post/list-view";
        }
        return "redirect:/post/update?postId=" + postId;
    }


}
