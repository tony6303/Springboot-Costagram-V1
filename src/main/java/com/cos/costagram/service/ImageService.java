package com.cos.costagram.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cos.costagram.domain.image.Image;
import com.cos.costagram.domain.image.ImageRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	
	public List<Image> 피드이미지(){
		
		// 1. principalId 로 내가 팔로우 하고있는 사용자를 찾아내야함 (1개이상 이니깐 컬렉션)
		// select * from image where userId in (select toUserId from follow where fromUserId = 로그인한Id) ;
		
		return imageRepository.findAll();
	}
	
	public List<Image> 팔로우피드이미지(int principalId){
		
		return imageRepository.followFeedImage(principalId);
	}
}
