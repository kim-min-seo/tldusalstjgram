package com.minse0.tldusalstjgram.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minse0.tldusalstjgram.user.domain.User;
import com.minse0.tldusalstjgram.user.service.UserService;

//API 구성을 위한 Controller
@RequestMapping("/user")
@RestController		// @Controller + @ResponseBody
public class UserRestController {
	
	private final UserService userService;
	
	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/join")
	public Map<String, String> join(
			@RequestParam String loginId
			, @RequestParam String password
			, @RequestParam String name
			, @RequestParam String nickname){
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(userService.addUser(loginId, password, name, nickname)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	@GetMapping("/is-duplicated-id")
	 public Map<String, String> isDuplicatedId(@RequestParam String loginId) {
        Map<String, String> resultMap = new HashMap<>();
        if (userService.isDuplicatedId(loginId)) {
            resultMap.put("result", "fail"); 
        } else {
            resultMap.put("result", "success"); 
        }
        return resultMap;
    }
	
}