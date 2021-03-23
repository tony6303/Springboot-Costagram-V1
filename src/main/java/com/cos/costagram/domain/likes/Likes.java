package com.cos.costagram.domain.likes;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.costagram.domain.image.Image;
import com.cos.costagram.domain.tag.Tag;
import com.cos.costagram.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Likes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne // Image (1) - Likes (n)
	@JoinColumn(name = "imageId")  
	private Image image;  // 어떤 이미지에 좋아요 했는지.
	
	@ManyToOne // User (1) - Likes (n)
	@JoinColumn(name = "userId")  
	private User user;
	
	@CreationTimestamp
	private Timestamp createDate;
}
