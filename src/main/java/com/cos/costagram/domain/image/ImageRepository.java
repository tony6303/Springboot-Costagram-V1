package com.cos.costagram.domain.image;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.boot.archive.internal.ExplodedArchiveDescriptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository  extends JpaRepository<Image, Integer>{

	// select * from image where userId in (select toUserId from follow where fromUserId = 로그인한Id) ;
	// 이미지를 모두 조회한다 ( 팔로우중인 유저 where 로그인한 사람  )	
	@Query(value = "SELECT * FROM image WHERE userId in (SELECT toUserId FROM follow WHERE fromUserId = :principalId ) order by id desc" , nativeQuery = true)
	Page<Image> followFeedImage(int principalId, Pageable pageable); // prepareStatement Resultset
	
	@Query(value = "select * from image where id in (select imageId from (select imageId, count(imageId) likeCount from likes group by imageId order by 2 desc) t) and userId != :principalId " , nativeQuery = true)
	List<Image> mExplore(int principalId); 
}
