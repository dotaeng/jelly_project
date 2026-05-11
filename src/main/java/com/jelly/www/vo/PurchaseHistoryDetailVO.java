package com.jelly.www.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistoryDetailVO {
    private int tradeId;            // 거래 ID
    private int productId;          // 상품 ID
    private String imageUrl;        // 상품 이미지
    private String productName;     // 상품명
    private String productDescription; // 상품 설명
    private String productSize;     // 상품 사이즈
    private int productPrice;       // 상품 가격(거래 금액)
    private String paymentMethod;   // 결제수단 (null 가능)
    private String addressLine1;    // 배송지 주소 1 (null 가능)
    private String addressLine2;    // 배송지 주소 2 (null 가능)
    private String postalCode;      // 배송지 우편번호 (null 가능)
    private String tradeStatusName; // 거래 상태명 (1~5 숫자를 문자열로)
    private String userName;        // 구매자 이름
    private String phoneNumber;     // 구매자 핸드폰 번호
}