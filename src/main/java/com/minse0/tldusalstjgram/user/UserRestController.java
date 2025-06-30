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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
	
	
	@PostMapping("/login")
	public Map<String, String> login(
			@RequestParam String loginId
			, @RequestParam String password
			, HttpServletRequest request){
		
		User user = userService.getUser(loginId, password);
		
		Map<String, String> resultMap = new HashMap<>();
		if(user != null) {
			resultMap.put("result", "success");
			
			// 세션을 관리하는 객체
			// 요청한 대상 클라이언트의 세션
			HttpSession session = request.getSession();
			
			// 로그인이 되었다.
			// 사용자 정보를 저장
			// 세션은 모든 요청에서 접근하고 사용할 수 있다.
			// 세션에 userId 라는 키에 값이 저장되어 있으면 로그인된 상태다.
			
			session.setAttribute("userId", user.getId());
			session.setAttribute("userName", user.getName());
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
}