package com.minse0.tldusalstjgram.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	@ResponseBody
	@GetMapping("/hello")
	public String helloWorld() {
		return "Hello World!!";
	}
	
	@GetMapping("hello/thymeleaf")
	public String hello() {
		return "hello/hello";
	}
}
