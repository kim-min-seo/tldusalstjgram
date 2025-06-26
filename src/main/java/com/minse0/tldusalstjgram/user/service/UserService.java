package com.minse0.tldusalstjgram.user.service;

import org.springframework.stereotype.Service;

import com.minse0.tldusalstjgram.common.MD5HashingEncoder;
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
			
			
			String hashingPassword = MD5HashingEncoder.encode(password);
			
			int count = userRepository.insertUser(loginId, hashingPassword, name, nickname);
			
			if(count == 1) {
				return true;
			} else {
				return false;
			}
		}
}
