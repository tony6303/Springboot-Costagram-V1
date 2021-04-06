package com.cos.costagram.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.costagram.config.auth.PrincipalDetails;
import com.cos.costagram.domain.image.Image;
import com.cos.costagram.domain.image.ImageRepository;
import com.cos.costagram.domain.tag.Tag;
import com.cos.costagram.domain.tag.TagRepository;
import com.cos.costagram.utils.TagUtils;
import com.cos.costagram.web.dto.image.ImageReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {
	
	// Value $ -> yml에 접근할 수 있음
	@Value("${file.path}")
	private String uploadFolder;

	private final ImageRepository imageRepository;
	private final TagRepository tagRepository;
	
	public List<Image> 피드이미지(){
		
		// 1. principalId 로 내가 팔로우 하고있는 사용자를 찾아내야함 (1개이상 이니깐 컬렉션)
		// select * from image where userId in (select toUserId from follow where fromUserId = 로그인한Id) ;
		
		return imageRepository.findAll();
	}
	
	public List<Image> 팔로우피드이미지(int principalId){
		List<Image> images = imageRepository.followFeedImage(principalId);
		
		// like의 상태와 카운트를 처리하는 로직, image 에 likeState 변수를 새로 만들었음
		// 뷰에서 연산을 하는것은 위험
		images.forEach((image)->{
			int likeCount = image.getLikes().size();
			image.setLikeCount(likeCount);
			
			image.getLikes().forEach((like)->{
				if(like.getUser().getId()== principalId) {
					image.setLikeState(true);
				}
			});
		});
		
		
		return images;
	}

	@Transactional
	public void 사진업로드(ImageReqDto imageReqDto, PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID();
		
		String imageFileName = uuid + "_" +imageReqDto.getFile().getOriginalFilename();
		System.out.println("파일명:" +imageFileName); 
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		System.out.println("파일경로:" +imageFilePath);
		
		// 통신 작업에는 트라이캐치 필수
		try {
			Files.write(imageFilePath, imageReqDto.getFile().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 1. ImageEntity의 Tag는 주인이 아니다. 
		// ImageEntity를 통해서 Tag를 Save할 수 없다.!!!! (DB매핑 순서 떄문에)
		
		// 1. 이미지저장
		Image image = imageReqDto.toEntity(imageFileName, principalDetails.getUser());
		Image imageEntity = imageRepository.save(image);
		
		// 2. Tag 저장
		List<Tag> tags = TagUtils.parsingToTag(imageReqDto.getTags(), imageEntity);
		tagRepository.saveAll(tags);
		
	}
	
	@Transactional(readOnly = true)
	public List<Image> 인기사진(int principalId){
		return imageRepository.mExplore(principalId);
	}
}
