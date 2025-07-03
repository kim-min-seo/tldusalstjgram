package com.minse0.tldusalstjgram.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.minse0.tldusalstjgram.common.Filemanager;
import com.minse0.tldusalstjgram.dto.PostDTO;
import com.minse0.tldusalstjgram.post.domain.Post;
import com.minse0.tldusalstjgram.post.repository.PostRepository;
import com.minse0.tldusalstjgram.user.domain.User;
import com.minse0.tldusalstjgram.user.repository.UserRepository;

import jakarta.persistence.PersistenceException;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

   
    public List<PostDTO> getPostLists(long userId, Pageable pageable) {
        // createdAt 기준으로 내림차순 정렬을 추가하여 pageable을 생성
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc("createdAt")));

        // 페이징된 게시글 리스트 가져오기
        Page<Post> postPage = postRepository.findAll(sortedPageable);

        // 결과를 PostDTO로 변환하여 반환
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : postPage.getContent()) {
            User user = userRepository.findById(post.getUserId());
            PostDTO postDTO = new PostDTO(post, user.getNickname());
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
                .userId(userId)
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
}
