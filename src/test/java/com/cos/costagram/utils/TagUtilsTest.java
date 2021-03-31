package com.cos.costagram.utils;

public class TagUtilsTest {

	// 왜 Tag에 공백이 들어갔는지 테스트
	public void 태그파싱_테스트() {
		String tags = "#만화 #도라에몽";
		
		String[] temp = tags.split("#");
		System.out.println(tags.length());
	}
}
