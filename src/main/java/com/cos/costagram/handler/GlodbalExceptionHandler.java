package com.cos.costagram.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.costagram.web.dto.CMRespDto;


@ControllerAdvice
@RestController
public class GlodbalExceptionHandler {

	@ExceptionHandler(value = IllegalArgumentException.class)
	public CMRespDto<?> IllegalArgumentException(Exception e){
		System.out.println(e.getMessage());
		return new CMRespDto<>(-1, "없는 유저입니다.");
	}
}
