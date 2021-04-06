package com.cos.costagram.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.costagram.config.auth.PrincipalDetails;
import com.cos.costagram.domain.comment.Comment;
import com.cos.costagram.domain.comment.CommentRepository;
import com.cos.costagram.service.CommentService;
import com.cos.costagram.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentController {
	
	private final CommentService commentService;

	@DeleteMapping("/comment/{commentId}")
	public CMRespDto<?> deleteComment(@PathVariable int commentId, @AuthenticationPrincipal PrincipalDetails principalDetails){
		int result = commentService.댓글삭제(commentId, principalDetails.getUser().getId());
		return new CMRespDto<>(result, null);
	}
	
}
