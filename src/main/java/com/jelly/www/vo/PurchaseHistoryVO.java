package com.jelly.www.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistoryVO {
    private int tradeId;         // 거래 ID 추가
    private String imageUrl;     // 상품이미지
    private String productName;  // 상품명
    private int purchasePrice;   // 구매가격
}