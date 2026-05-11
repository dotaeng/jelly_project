package com.jelly.www.vo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeVO {
    private int noticeId;        // 공지사항 ID
    private int userId;          // 작성자 ID
    private String title;        // 공지사항 제목
    private String content;      // 공지사항 내용
    private Timestamp createdAt; // 생성 날짜
    private Timestamp updatedAt; // 수정 날짜
}