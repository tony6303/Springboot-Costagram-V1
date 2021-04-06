package com.cos.costagram.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.costagram.web.dto.comment.CommentReqDto;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	//native query
	// resultSet을 가져올수가 없다... ? insert 의 결과값은 일단 int임.
//	@Query(value = "INSERT INTO comment (content, imageId, userId, createDate) values (:content, :imageId, :userId, now())" , nativeQuery = true)
//	Comment mSave(String content, int imageId, int userId);
	
}
