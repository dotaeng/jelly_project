package com.jelly.www.vo;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SizeVO {
    private int sizeId;          // 사이즈 ID
    private int productId;       // 상품 ID (PRODUCT 테이블 참조)
    private String size;         // 사이즈
    private int price;           // 사이즈별 가격
    private Timestamp createdAt; // 생성 	날짜
    private Timestamp updatedAt; // 수정 날짜

    // 사이즈 ID 조회용
    public SizeVO(int sizeId) {
    	this.sizeId = sizeId;
    }
    
    // 사이즈별 가격 조회용
   public SizeVO (int sizeId, int price) {
	   this.sizeId = sizeId;
	   this.price = price;
   }
}
