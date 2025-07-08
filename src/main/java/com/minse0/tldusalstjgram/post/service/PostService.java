package com.minse0.tldusalstjgram.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.minse0.tldusalstjgram.comment.domain.Comment;
import com.minse0.tldusalstjgram.comment.service.CommentService;
import com.minse0.tldusalstjgram.common.Filemanager;
import com.minse0.tldusalstjgram.dto.PostDTO;
import com.minse0.tldusalstjgram.like.service.LikeService;
import com.minse0.tldusalstjgram.post.domain.Post;
import com.minse0.tldusalstjgram.post.repository.PostRepository;
import com.minse0.tldusalstjgram.user.domain.User;
import com.minse0.tldusalstjgram.user.repository.UserRepository;

import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentService commentService;
    private final LikeService likeService;

    
    
    public List<Post> getAllPosts() {
        return postRepository.findAll(); 
    }


   
    public List<PostDTO> getPostLists(long userId, Pageable pageable) {
        
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc("createdAt")));

        
        Page<Post> postPage = postRepository.findAll(sortedPageable);

     
        List<PostDTO> postDTOs = new ArrayList<>();
        
        for (Post post : postPage.getContent()) {
            int likeCount = likeService.likeCountByPostId(post.getId());
            List<Comment> comments = commentService.getCommentsByPost(post.getId());
            String nickname = post.getUser().getNickname();

            PostDTO postDTO = PostDTO.builder()
                .id(post.getId())
                .userId(post.getUser().getId())
                .caption(post.getCaption())
                .contents(post.getContents())
                .music(post.getMusic())
                .tagPeople(post.getTagPeople())
                .location(post.getLocation())
                .audience(post.getAudience())
                .imagePath(post.getImagePath())
                .nickname(nickname)
                .comments(comments)
                .likeCount(likeCount)
                .build();

            postDTOs.add(postDTO);
        }

        return postDTOs;
    }

   
    public boolean addPost(
            long userId,
            String caption,
            String contents,
            String music,
            String tagPeople,
            String location,
            String audience,
            MultipartFile file) {

        if (contents == null || contents.isEmpty()) {
            return false;
        }
        User user = userRepository.findById(userId);
        if (user == null) {
            return false;
        }

        String imagePath = null;
        if (file != null && !file.isEmpty()) {
            imagePath = Filemanager.saveFile(userId, file);
        }

        Post post = Post.builder()
        		.user(user)
                .caption(caption)
                .contents(contents)
                .imagePath(imagePath)
                .music(music)
                .tagPeople(tagPeople)
                .location(location)
                .audience(audience)
                .build();

        try {
            postRepository.save(post);
        } catch (PersistenceException e) {
            return false;
        }

        return true;
    }

    
    public Post getPost(long id) {
        return postRepository.findById(id).orElse(null);
    }
   
    
    public boolean deletePost(long postId, long userId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null || post.getUser().getId() != userId) return false;
        
       
        String imagePath = post.getImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            Filemanager.removeFile(imagePath);
        }
        commentService.deleteCommentsByPostId(postId);
        likeService.deleteLikesByPostId(postId);
        postRepository.delete(post);
        return true;
    }
    
    public boolean updatePost(long postId, long userId,
            String caption, String contents, String music,
            String tagPeople, String location, String audience,
            MultipartFile file) {
        
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null || post.getUser().getId() != userId) return false;

        post.setCaption(caption);
        post.setContents(contents);
        post.setMusic(music);
        post.setTagPeople(tagPeople);
        post.setLocation(location);
        post.setAudience(audience);

        if (file != null && !file.isEmpty()) {
            String imagePath = Filemanager.saveFile(userId, file);
            post.setImagePath(imagePath);
        }

        postRepository.save(post);
        return true;
    }


}
