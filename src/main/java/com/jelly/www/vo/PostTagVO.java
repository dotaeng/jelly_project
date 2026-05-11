package com.jelly.www.vo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostTagVO {
	private int postTagId; // 상품 태그 고유 ID
	private int postId; // 게시물 ID (POST 테이블 참조)
	private int productId; // 상품 ID (PRODUCT 테이블 참조)
	private Timestamp created_at; // 상품 태그 생성 날짜
}