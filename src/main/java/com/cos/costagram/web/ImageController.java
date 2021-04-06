package com.cos.costagram.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.costagram.config.auth.PrincipalDetails;
import com.cos.costagram.service.ImageService;
import com.cos.costagram.service.LikesService;
import com.cos.costagram.web.dto.CMRespDto;
import com.cos.costagram.web.dto.image.ImageReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ImageController {

	private final ImageService imageService;
	private final LikesService likesService;
	
	@GetMapping({"/", "/image/feed"})
	public String feed(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		// 로그인 한 사용자의 팔로우 된 사용자가 올린 이미지가 필요함.
		// test -> image1(test2) , image2(test2)  principalDetails.getUser().getId()
//		model.addAttribute("images", imageService.피드이미지());
		model.addAttribute("images", imageService.팔로우피드이미지(principalDetails.getUser().getId()));
		
		return "image/feed";
	}
	
	@GetMapping("/image/upload")
	public String upload() {
		return "image/upload";
	}
	
	@PostMapping("/image")
	public String image(ImageReqDto imageReqDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		imageService.사진업로드(imageReqDto, principalDetails);
		
		return "redirect:/user/" + principalDetails.getUser().getId();
	}

	@PostMapping("/image/{imageId}/likes")
	public @ResponseBody CMRespDto<?> like(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int imageId){
		likesService.좋아요(imageId, principalDetails.getUser().getId());
		return new CMRespDto<>(1, null);
	}
	
	@DeleteMapping("/image/{imageId}/likes")
	public @ResponseBody CMRespDto<?> unLike(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int imageId){
		likesService.싫어요(imageId, principalDetails.getUser().getId());
		return new CMRespDto<>(1, null);
	}
	
	@GetMapping("/image/explore")
	public String explore(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		model.addAttribute("images", imageService.인기사진(principalDetails.getUser().getId()));

		return "image/explore";
	}
}
