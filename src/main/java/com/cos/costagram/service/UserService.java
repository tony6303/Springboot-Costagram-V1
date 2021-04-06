package com.cos.costagram.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cos.costagram.config.auth.PrincipalDetails;
import com.cos.costagram.domain.follow.FollowRepository;
import com.cos.costagram.domain.image.Image;
import com.cos.costagram.domain.image.ImageRepository;
import com.cos.costagram.domain.tag.Tag;
import com.cos.costagram.domain.user.User;
import com.cos.costagram.domain.user.UserRepository;
import com.cos.costagram.utils.TagUtils;
import com.cos.costagram.web.dto.image.ImageReqDto;
import com.cos.costagram.web.dto.user.UserProfileRespDto;
import com.cos.costagram.web.dto.user.UserUpdateReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final FollowRepository followRepository;
	private final BCryptPasswordEncoder encoder;
	
	
	// Value $ -> yml에 접근할 수 있음
	@Value("${file.path}")
	private String uploadFolder;
		
	@Transactional
	public User 회원사진업로드(MultipartFile profileImageFile, PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID();
		
		// 유저 서비스에서 그대로 갖고왔습니다
		// String imageFileName = uuid + "_" +imageReqDto.getFile().getOriginalFilename();
		String imageFileName = uuid + "_" +profileImageFile.getOriginalFilename();
		System.out.println("파일명:" +imageFileName); 
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		System.out.println("파일경로:" +imageFilePath);
		
		// 통신 작업에는 트라이캐치 필수
		try {
			Files.write(imageFilePath, profileImageFile.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		User userEntity = userRepository.findById(principalDetails.getUser().getId()).get();
		userEntity.setProfileImageUrl(imageFileName);
		
		return userEntity;
	}
	
		
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
