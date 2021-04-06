package com.cos.costagram.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.costagram.config.auth.PrincipalDetails;
import com.cos.costagram.domain.comment.Comment;
import com.cos.costagram.domain.image.Image;
import com.cos.costagram.service.CommentService;
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
	public String feed() {		
		return "image/feed";
	}
	
	//  주소 : /image?page=0
	@GetMapping("/image")
	public @ResponseBody CMRespDto<?> image(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails, 
			@PageableDefault (size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Image> pages = imageService.팔로우피드이미지(principalDetails.getUser().getId(), pageable);
		
		return new CMRespDto<>(1, pages);
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
	
	private final CommentService commentService;
	
	@PostMapping("/image/{imageId}/comment")
	public @ResponseBody CMRespDto<?> saveComment(@RequestBody String content, @PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails){
		Comment commentEntity = commentService.댓글쓰기(content, imageId, principalDetails.getUser());
		
		return new CMRespDto<>(1, commentEntity);
	}
}
