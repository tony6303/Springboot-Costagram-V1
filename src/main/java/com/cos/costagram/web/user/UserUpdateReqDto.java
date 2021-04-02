package com.cos.costagram.web.user;

import lombok.Data;

@Data
public class UserUpdateReqDto {

	private String username;
	private String name;
	private String website;
	private String bio;
	private String email;
	private String phone;
	private String gender;
}
