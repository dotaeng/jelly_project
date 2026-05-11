package com.jelly.www.vo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StyleCommentVO {
	private int commentId;
	private int postId;
	private int userId;
	private String content;
	private int likesCount;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private String profileImage;
	private String nickname;
	private int commentCount;
}
