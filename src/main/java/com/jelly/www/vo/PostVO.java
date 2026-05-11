package com.jelly.www.vo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostVO {
	private int postId; // 게시물 고유 ID
    private int userId; // 작성자 ID (USER 테이블 참조)
    private int styleCategory; // 스타일 카테고리 ID (1~7)
    private String title; // 게시물 제목
    private String content; // 게시물 내용
    private String thumbnailImageUrl; // 썸네일 이미지
    private int likeCount; // 좋아요 수
    private int commentCount; // 댓글 수
    private int viewCount; // 조회수
    private int saveCount; // 저장 수
    private Timestamp createdAt; // 게시물 생성 날짜
    private Timestamp updatedAt; // 게시물 수정 날짜
}