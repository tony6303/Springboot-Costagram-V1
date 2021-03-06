package com.cos.costagram.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.costagram.service.AuthService;
import com.cos.costagram.utils.Script;
import com.cos.costagram.web.dto.auth.UserJoinReqDto;

import lombok.RequiredArgsConstructor;

// auth
@RequiredArgsConstructor
@Controller
public class AuthController {
	private final AuthService authService;

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "auth/loginForm";
	}
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "auth/joinForm";
	}
	
	@PostMapping("/auth/join") // @RequestBody 를 안써서 x-www-urlencoded 로 들어옴.
	public @ResponseBody String join(UserJoinReqDto userJoinReqDto) {
		authService.회원가입(userJoinReqDto.toEntity());
		// request.sendRedirect
		return Script.href("성공~", "/auth/loginForm");
		//return "redirect:/auth/loginForm";
	}
	
}
