package com.cos.costagram.utils;

import java.util.ArrayList;
import java.util.List;

import com.cos.costagram.domain.image.Image;
import com.cos.costagram.domain.tag.Tag;

public class TagUtils {

	public static List<Tag> parsingToTag(String tags, Image imageEntity){
		String temp[] = tags.split("#"); // #여행 #바다
		List<Tag> list = new ArrayList<>();
		
		// for(String tagName : temp) { //  temp 값(tags의 개수) 만큼 실행 됨
		for(int i=1; i<temp.length; i++) { // 파싱할때 0번지에 공백이 들어와서 1번지로 시작함
			Tag tag = Tag.builder()
				.name(temp[i].trim())
				.image(imageEntity)
				.build();
			list.add(tag);
		}
		return list;
	}
}
