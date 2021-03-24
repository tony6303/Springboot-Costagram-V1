package com.cos.costagram.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.costagram.domain.user.User;
import com.cos.costagram.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;
// 내부적으로 UserDetailsService 는 이미 등록이 되있다.. (시큐리티 처음 만들면 로그인화면 뜨는 이유)

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("PrincipalDetailsService : /login이 호출되면 자동 실행되어 username이 DB에 있는지 확인함");
		User principal = userRepository.findByUsername(username);
		if(principal == null) {
			return null;
		}else {
			return new PrincipalDetails(principal); // PrincipalDetails 는 UserDetails 를 상속받고있음.
			// SecurityContextHolder => Authentication 객체 내부에 담김.
		}
	}

	
}
