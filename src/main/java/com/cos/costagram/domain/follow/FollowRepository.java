package com.cos.costagram.domain.follow;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepository extends JpaRepository<Follow, Integer> {

	// insert, delete @Transactional 없으면 작동안함
	// update @Modifying 없으면 작동안함
	// 참고만 해두자 
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO follow(fromUserID, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())" , nativeQuery = true)
	int mFollow(int fromUserId, int toUserId); // prepareStatement Resultset
	
	
	@Modifying
	@Query(value = "DELETE FROM follow WHERE fromUserId = :fromUserId AND toUserId = :toUserId" , nativeQuery = true)
	int mUnFollow(int fromUserId, int toUserId); // prepareStatement Resultset
	
	@Query(value = "select count(*) from follow where fromUserId = :principalId and toUserId = :userId", nativeQuery = true)
	int mFollowState(int principalId, int userId);
	
	@Query(value = "select count(*) from follow where fromUserId = :userId", nativeQuery = true)
	int mFollowCount(int userId);
	
}
