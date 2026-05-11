package com.jelly.www.vo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CommentVO {
	private int commentId;
	private int postId;
	private int userId;
	private String content;
	private int likesCount;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public CommentVO(int postId, int userId, String content) {
		this.postId = postId;
		this.userId = userId;
		this.content = content;
	}
}
