package com.jelly.www.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileInfoVO {
    private int userId;            // 사용자 고유 ID
    private String userName;       // 사용자 이름
    private String nickname;       // 사용자 닉네임
    private String email;          // 사용자 이메일
    private String phoneNumber;    // 사용자 전화번호
    private String profileImage;   // 사용자 프로필 이미지
}