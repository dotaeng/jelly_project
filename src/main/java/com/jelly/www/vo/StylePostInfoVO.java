package com.jelly.www.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StylePostInfoVO {
	private int postId; // 게시물 고유 ID
    private int userId; // 작성자 ID (USER 테이블 참조)
    private String nickname;
    private String title; // 게시물 제목
    private String postImageUrl;
    private String profileImageUrl;
    private int likeCount;
    private boolean like;
}
