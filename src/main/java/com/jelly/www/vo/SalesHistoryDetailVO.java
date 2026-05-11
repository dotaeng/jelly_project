package com.jelly.www.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
public class SalesHistoryDetailVO {
    private int tradeId;                // 거래 ID
    private String productName;         // 상품 이름
    private String imageUrl;            // 상품 이미지 URL
    private int salePrice;              // 판매 가격
    private String bankName;            // 은행 이름
    private String accountNumber;       // 계좌 번호
    private String accountHolder;       // 계좌 주명
    private Timestamp tradeDate;        // 거래 일시
    private Integer productId; // 상품 ID
}