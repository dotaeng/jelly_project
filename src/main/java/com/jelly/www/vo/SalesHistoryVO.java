package com.jelly.www.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesHistoryVO {
    private int tradeId;          // 거래 ID
    private String imageUrl;      // 상품 이미지 URL
    private String productName;   // 상품명
    private int salePrice;        // 판매 가격
}