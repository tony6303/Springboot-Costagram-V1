package com.cos.costagram.web.auth.dto;

import com.cos.costagram.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserJoinReqDto {

	private String username;
	private String password;
	private String email;
	private String name;
	
	public User toEntity() {
		return User.builder()
			.username(username)
			.password(password)
			.email(email)
			.name(name)
			.build();
	}
}
