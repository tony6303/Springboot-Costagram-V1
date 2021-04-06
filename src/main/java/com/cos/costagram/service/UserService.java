package com.cos.costagram.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.costagram.config.auth.PrincipalDetails;
import com.cos.costagram.domain.follow.FollowRepository;
import com.cos.costagram.domain.image.Image;
import com.cos.costagram.domain.image.ImageRepository;
import com.cos.costagram.domain.user.User;
import com.cos.costagram.domain.user.UserRepository;
import com.cos.costagram.web.dto.user.UserProfileRespDto;
import com.cos.costagram.web.dto.user.UserUpdateReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final FollowRepository followRepository;
	private final BCryptPasswordEncoder encoder;
	
		
	@Transactional(readOnly = true)
	public UserProfileRespDto 회원프로필(int userId, int principalDetails) {
		UserProfileRespDto userProfileRespDto = new UserProfileRespDto();
		
		User userEntity = userRepository.findById(userId).orElseThrow(() -> {
			return new IllegalArgumentException(); 
		});
		
		int followState = followRepository.mFollowState(principalDetails, userId);
		int followCount = followRepository.mFollowCount(userId);
		
		userProfileRespDto.setFollowState(followState == 1);
		userProfileRespDto.setFollowCount(followCount);
		userProfileRespDto.setImageCount(userEntity.getImages().size());
		
		userEntity.getImages().forEach((image)->{
			image.setLikeCount(image.getLikes().size());
		});
		
		userProfileRespDto.setUser(userEntity);
		
		return userProfileRespDto;
	}
	
	// principal 세션 바꾸는 방법
//	@Transactional
//	public User 회원정보수정(int userId, UserUpdateReqDto userUpdateReqDto) {
//		User userEntity = userRepository.findById(userId).get();
//		
//		userEntity.setUsername(userUpdateReqDto.getUsername());
//		userEntity.setName(userUpdateReqDto.getName());
//		userEntity.setWebsite(userUpdateReqDto.getWebsite());
//		userEntity.setBio(userUpdateReqDto.getBio());
//		userEntity.setEmail(userUpdateReqDto.getEmail());
//		userEntity.setPhone(userUpdateReqDto.getPhone());
//		userEntity.setGender(userUpdateReqDto.getGender());
//		
//		return userEntity;
//	}
	
	@Transactional
	public User 회원정보수정(int userId, User user) {
		User userEntity = userRepository.findById(userId).get();
		
		userEntity.setUsername(user.getUsername());
		userEntity.setName(user.getName());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setBio(user.getBio());
		userEntity.setEmail(user.getEmail());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		userEntity.setPassword(encPassword);
		
		return userEntity;
	}
}
