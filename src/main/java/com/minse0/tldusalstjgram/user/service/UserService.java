package com.minse0.tldusalstjgram.user.service;

import org.springframework.stereotype.Service;

import com.minse0.tldusalstjgram.common.SHA256HashingEncoder;
import com.minse0.tldusalstjgram.user.domain.User;
import com.minse0.tldusalstjgram.user.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	public UserService( UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	// 사용자 추가 기능
		public boolean addUser(
				String loginId
				, String password
				, String name
				, String nickname) {
			
			
			String hashingPassword = SHA256HashingEncoder.encode(password);
			
			int count = userRepository.insertUser(loginId, hashingPassword, name, nickname);
			
			if(count == 1) {
				return true;
			} else {
				return false;
			}
		}
		
	     public boolean isDuplicatedId(String loginId) {
	    	 return userRepository.countUserByLoginId(loginId) > 0;
		    }
	     
	     public User getUser(String loginId, String pasword) {
	 		
	 		String hashingPassword = SHA256HashingEncoder.encode(pasword);
	 		
	 		return userRepository.selectUser(loginId, hashingPassword);
	 	}
		 
		 

}
