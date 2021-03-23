package com.cos.costagram.domain.image;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.costagram.domain.likes.Likes;
import com.cos.costagram.domain.tag.Tag;
import com.cos.costagram.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String caption; // 할 말
	private String postImageUrl; // 이미지 주소
	
	// FK의 주인인 곳에서 적어야 됨.
	@ManyToOne // EAGER  User(1) - Image(n) 기본 EAGER , (fetch = FetchType.LAZY)  
	@JoinColumn(name = "userId")
	private User user;
	
	// Tag 가서 보면 됨 
	// 이미지 테이블이 필요한 곳은 많은데, 항상 태그가 필요하지는 않다. 그래서 LAZY 방식을 사용
	@OneToMany(mappedBy = "image") // LAZY  Image(1) - Tag(n)
	private List<Tag> tags;
	
	@OneToMany(mappedBy = "image") // Image(1) - likes(n)
	private List<Likes> likes;
	
	@CreationTimestamp
	private Timestamp createDate;
}
