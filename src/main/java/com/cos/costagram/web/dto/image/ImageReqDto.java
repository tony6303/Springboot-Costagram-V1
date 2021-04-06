package com.cos.costagram.web.dto.image;

import org.springframework.web.multipart.MultipartFile;

import com.cos.costagram.domain.image.Image;
import com.cos.costagram.domain.user.User;

import lombok.Data;

@Data
public class ImageReqDto {
	private MultipartFile file;
	private String tags;
	private String caption;
	
	public Image toEntity(String postImageUrl, User userEntity) {
		return Image.builder()
			.caption(caption)
			.postImageUrl(postImageUrl)
			.user(userEntity)
			.build();
	}
	
}
