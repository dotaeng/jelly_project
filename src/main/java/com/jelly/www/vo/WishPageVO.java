package com.jelly.www.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishPageVO {
    private int productId; // 상품 ID
    private String productImage; // 상품 이미지 URL
    private String productName; // 상품명
    private String productDescription; // 상품 상세 설명
    private Integer lowestPrice; // 최저가 
    private Integer releasePrice; // 발매가 
}