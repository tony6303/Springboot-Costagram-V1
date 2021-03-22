package com.cos.costagram.domain.user;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 30, unique = true)
	private String username;
	
	@JsonIgnore
	private String password;
	
	private String name;
	private String website;
	private String bio;
	private String email;
	private String phone;
	private String gender;
	
	private String profileImage;
	private String role; // USER , ADMIN
	private String provider; // 제공자 naver, facebook ...
	
	@CreationTimestamp
	private Timestamp createDate;
}
