package com.cos.costagram.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.costagram.domain.comment.CommentRepository;
import com.cos.costagram.web.dto.comment.CommentReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final CommentRepository commentRepository;

	@Transactional
	public void 댓글쓰기(int userId, CommentReqDto commentReqDto) {
		
	}
	
	@Transactional
	public void 댓글삭제() {
		
	}
}
