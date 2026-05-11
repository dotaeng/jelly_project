package com.jelly.www.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostImageVO {
	private int postImageId; // 게시물 이미지 고유 ID
	private String postImageUrl; // 게시물 이미지 URL
	private int postId; // 게시물 ID (POST 테이블 참조)
	private int postImageOrder; // 이미지 순서
//	private String postImagePath; // 게시물 이미지 path, local 에 어디 있는지 
}