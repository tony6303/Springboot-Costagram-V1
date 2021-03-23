package com.cos.costagram.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.costagram.web.auth.dto.UserJoinReqDto;

public interface UserRepository extends JpaRepository<User, Integer> {

}
