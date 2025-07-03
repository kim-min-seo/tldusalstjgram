package com.minse0.tldusalstjgram.post;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.minse0.tldusalstjgram.dto.PostDTO;
import com.minse0.tldusalstjgram.post.service.PostService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
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

       
        Pageable pageable = PageRequest.of(page, size);

      
        List<PostDTO> postDTOs = postService.getPostLists(userId, pageable);

       
        model.addAttribute("posts", postDTOs);
        return "post/list";
    }

    @PostMapping("/create-post")
    public String createPost(
            String caption,
            String contents,
            String music,
            String tagPeople,
            String location,
            String audience,
            HttpSession session,
            MultipartFile imageFile
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
}
