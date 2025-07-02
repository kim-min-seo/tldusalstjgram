package com.minse0.tldusalstjgram.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	
	public List<PostDTO> getPostLists(long userID){
		
		List<Post> posts = postRepository.findByUserId(userID);
		List<PostDTO> postDTOs = new ArrayList<>();
		
		for(Post post : posts) {
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
		 String nickname = user.getNickname();


		    
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

	
	public List<Post> getPostList(long userId) {
	    return postRepository.findByUserId(userId);
	}
	
	public Post getPost(long id) {
		Optional<Post> optionalPost =  postRepository.findById(id);
		
		if(optionalPost.isPresent()) {
			return optionalPost.get();
		} else {
			return null;
		}
	}
}
