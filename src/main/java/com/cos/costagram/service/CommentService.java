package com.cos.costagram.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.costagram.domain.comment.Comment;
import com.cos.costagram.domain.comment.CommentRepository;
import com.cos.costagram.domain.image.Image;
import com.cos.costagram.domain.user.User;
import com.cos.costagram.web.dto.comment.CommentReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final CommentRepository commentRepository;

	@Transactional
	public Comment 댓글쓰기(String content, int imageId, User principal) {
		Image image = Image.builder()
				.id(imageId)
				.build();
		
		Comment comment = Comment.builder()
				.content(content)
				.image(image)
				.user(principal)    // 객체를 쉽게 넣는 방법?
				.build();
		
		return commentRepository.save(comment);
	}
	
	@Transactional
	public int 댓글삭제(int commentId, int userId) {
		Comment commentEntity = commentRepository.findById(commentId).get(); // 컨트롤 어드바이스 익셉션이 알아서 작동
		if(commentEntity.getUser().getId() == userId) {
			commentRepository.deleteById(commentId);
			return 1;
		}
		return -1;	
	}
}
