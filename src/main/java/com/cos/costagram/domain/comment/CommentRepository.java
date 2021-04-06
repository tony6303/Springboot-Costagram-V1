package com.cos.costagram.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.costagram.web.dto.comment.CommentReqDto;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	//native query
	@Query(value = "INSERT INTO comment (content, imageId, userId, createDate) values (:content, :imageId, :userId, now())" , nativeQuery = true)
	int mSave(CommentReqDto commentReqDto);
	
}
