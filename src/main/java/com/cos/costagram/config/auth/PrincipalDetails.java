package com.cos.costagram.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.costagram.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails {
	
	private User user;
	private Map<String, Object> attributes; // OAuth 제공자로부터 받은 회원정보
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		System.out.println("ROLE 검증 하는 중");
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		// DB에 ROLE_USER 라고 저장하는건 별로 좋은방식이 아닌거같아요. 꺼내와서 쓸 때 붙여줍니다.
		collectors.add(()->{ return "ROLE_"+user.getRole();});
		return collectors;
	}
	

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	

}
