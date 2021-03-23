package com.cos.costagram.service;

import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.costagram.domain.user.User;
import com.cos.costagram.domain.user.UserRepository;
import com.cos.costagram.web.auth.dto.UserJoinReqDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	// security config 에 BCrypt 가 Bean으로 등록되어있어서 사용이 가능
	private final BCryptPasswordEncoder encoder;
	
	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword(); // password 원문
		String encPassword = encoder.encode(rawPassword); // 해쉬
		user.setPassword(encPassword);
		user.setRole("USER");
		userRepository.save(user);
	}
	
}
