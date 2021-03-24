package com.cos.costagram.domain.image;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository  extends JpaRepository<Image, Integer>{

	// select * from image where userId in (select toUserId from follow where fromUserId = 로그인한Id) ;
	
	@Query(value = "SELECT * FROM image WHERE userId in (SELECT toUserId FROM follow WHERE fromUserId = :principalId ) order by id desc" , nativeQuery = true)
	List<Image> followFeedImage(int principalId); // prepareStatement Resultset
}
