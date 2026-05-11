package com.jelly.www.vo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSaveVO {
    private int postSaveId; // 게시물 저장 고유 ID
    private int userId; // 사용자 ID (USER 테이블 참조)
    private int postId; // 게시물 ID (POST 테이블 참조)
    private Timestamp savedAt; //저장을 누른 시간
    
    public PostSaveVO(int postId, int userId){
    	this.postId = postId;
    	this.userId = userId;
    }
}