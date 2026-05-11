package com.jelly.www.vo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountVO {
	private int accountId; // 계좌 고유 ID
    private int userId; // 사용자 ID
    private String bankName; // 은행 이름
    private String accountNumber; // 계좌 번호
    private String accountHolder; // 계좌 주명
    private boolean isDefault; // 기본 계좌 여부
    private String createdAt; // 생성 날짜
    private String updatedAt; // 수정 날짜
}
