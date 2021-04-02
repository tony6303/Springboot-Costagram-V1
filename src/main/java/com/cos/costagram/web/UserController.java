package com.cos.costagram.web;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.costagram.config.auth.PrincipalDetails;
import com.cos.costagram.domain.follow.Follow;
import com.cos.costagram.domain.user.User;
import com.cos.costagram.service.FollowService;
import com.cos.costagram.service.UserService;
import com.cos.costagram.web.dto.CMRespDto;
import com.cos.costagram.web.follow.FollowRespDto;
import com.cos.costagram.web.user.UserProfileRespDto;
import com.cos.costagram.web.user.UserUpdateReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	private final UserService userService;
	private final FollowService followService;
	
	@GetMapping("/test")
	public String test() {
		return "index";
	}
	
	@GetMapping("/user/{pageUserId}/follow")
	public @ResponseBody CMRespDto<?> followList(@AuthenticationPrincipal PrincipalDetails principalDetails , @PathVariable int pageUserId){
		List<FollowRespDto> result = followService.팔로우리스트(principalDetails.getUser().getId(), pageUserId);
		
		// 팔로우의 컬렉션을 리턴해줘야함
		return new CMRespDto<>(1,result);
	}
	
	@GetMapping("/user/{id}")
	public String profile(@PathVariable int id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		UserProfileRespDto userProfileRespDto = userService.회원프로필(id, principalDetails.getUser().getId());
		model.addAttribute("dto", userProfileRespDto);
		
		return "user/profile";
	}
	
	@GetMapping("/user/{id}/profileSetting")
	public String profileSetting(@PathVariable int id) {
		return "user/profileSetting";
	}
	
	// principal 세션 바꾸는 방법
//	@PutMapping("/user/{userId}")
//	public @ResponseBody CMRespDto<?> updateUser(@PathVariable int userId, @RequestBody UserUpdateReqDto userUpdateReqDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
//		User userEntity = userService.회원정보수정(userId, userUpdateReqDto);
//		principalDetails.setUser(userEntity);
//		return new CMRespDto<>(1, null);
//	}
	
	// form 직렬화로 user 받아옴
	@PutMapping("/user/{userId}")
	public @ResponseBody CMRespDto<?> updateUser(@PathVariable int userId, User user, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println(user);
		User userEntity = userService.회원정보수정(userId, user);
		principalDetails.setUser(userEntity);
		return new CMRespDto<>(1, null);
	}
}
