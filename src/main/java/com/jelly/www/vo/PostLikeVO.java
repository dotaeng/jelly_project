package com.jelly.www.vo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeVO {
    private int likeId; // 좋아요 고유 ID
    private int postId; // 게시물 ID (POST 테이블 참조)
    private int userId; // 사용자 ID (USER 테이블 참조)
    private Timestamp likedAt; //좋아요를 누른 시간
    
    public PostLikeVO(int postId, int userId){
    	this.postId = postId;
    	this.userId = userId;
    }
}