package com.jelly.www.vo;

import java.security.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowVO {
	private int followId; // 팔로우 고유 ID
	private int followerId; // 팔로워 사용자 ID (USER 테이블 참조)
	private int followingId; // 팔로잉 사용자 ID (USER 테이블 참조)
	private Timestamp createdAt; // 팔로우 생성 날짜
	
	public FollowVO(int followerId, int followingId){
    	this.followerId = followerId;
    	this.followingId = followingId;
    }
}