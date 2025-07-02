package com.minse0.tldusalstjgram.post;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

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
    public String getPostList(Model model, HttpSession session) {
        long userId = (Long) session.getAttribute("userId");
        model.addAttribute("posts", postService.getPostList(userId));
        return "post/list";
    }

    @PostMapping("/create-post")
    public String createPost(String caption, String contents, String music, String tagPeople, String location, String audience, HttpSession session, MultipartFile imageFile) {
        
        long userId = (Long) session.getAttribute("userId");

        if (postService.addPost(userId, caption, contents, music, tagPeople, location, audience, imageFile)) {
            return "redirect:/post/list-view";
        }
        return "redirect:/post/create";
    }
}
