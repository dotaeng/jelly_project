package com.jelly.www.vo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserVO {
	private int userId;             // 사용자 고유 ID
    private String userName;        // 사용자 이름
    private String nickname;        // 닉네임
    private String email;           // 이메일
    private String password;        // 비밀번호
    private String phoneNumber;     // 전화번호
    private String birthDate;       // 생년월일
    private String kakaoId;         // 카카오 로그인 ID
    private String naverId;         // 네이버 로그인 ID
    private String profileImage;    // 프로필 이미지
    private int followerCount;      // 팔로워 수
    private int followingCount;     // 팔로잉 수
    private Timestamp createdAt;    // 생성 날짜
    private Timestamp updatedAt;    // 수정 날짜
}
