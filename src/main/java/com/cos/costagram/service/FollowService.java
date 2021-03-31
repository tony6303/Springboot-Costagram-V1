package com.cos.costagram.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.costagram.domain.follow.Follow;
import com.cos.costagram.domain.follow.FollowRepository;
import com.cos.costagram.web.follow.FollowRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FollowService {

	private final FollowRepository followRepository;
	private final EntityManager em;
	
//	public List<Follow> 팔로우리스트(int userId){
//		// System.out.println(followRepository.findByFromUserId(userId));
//		return followRepository.findByFromUserId(userId);
//	}
	
	//UserController 에서 사용중
	public List<FollowRespDto> 팔로우리스트(int principalId, int pageUserId){
		
		// 문자열에 변수넣으려면.. 쌍따옴표로 끊어서 해야겠네 ...
		// 아니오 물음표 문법으로 가능합니다. 그리고 쌍따옴표로 하면 인젝션 가능성이 있어서 위험
		StringBuffer sb = new StringBuffer();
		sb.append("select u.id userId, u.username, u.profileImageUrl, ");
		sb.append("if ( (select true from follow where fromuserid =? and touserid = u.id) ,true , false ) followState, "); //principalDetails.user.id
		sb.append("if(u.id = ?, true, false) equalState "); // principalDetails.user.id (로그인 된 유저의 아이디)
		sb.append("from follow f inner join user u on u.id = f.toUserId ");
		sb.append("where f.fromUserId = ?"); // pageUserId

		// import persistance 
		Query query = em.createNativeQuery(sb.toString())
				.setParameter(1, principalId)
				.setParameter(2, principalId)
				.setParameter(3, pageUserId);
		
		JpaResultMapper result = new JpaResultMapper();
		List<FollowRespDto> followRespdto =  result.list(query, FollowRespDto.class);
		
		return followRespdto;
	}
	
	@Transactional 
	public int 팔로우(int fromUserId, int toUserId) {
		return followRepository.mFollow(fromUserId, toUserId);
	}
	
	@Transactional
	public int 언팔로우(int fromUserId, int toUserId) {
		return followRepository.mUnFollow(fromUserId, toUserId);
	}
}
