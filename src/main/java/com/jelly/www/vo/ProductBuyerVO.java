package com.jelly.www.vo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductBuyerVO {

	int productBuyerId;
	int productId;
	int buyerId;
	String size;
	int bidMoney;
	String bidStatus;
	Timestamp createdAt;
	Timestamp updatedAt;

	// 구매자 조회용	
	public ProductBuyerVO(int productBuyerId) {
		this.productBuyerId = productBuyerId;
	}
		
	
	// 구매정보 추가
	public ProductBuyerVO(int productId, int buyerId, String size, int bidMoney) {
		this.productId = productId;
		this.buyerId = buyerId;
		this.size= size;
		this.bidMoney = bidMoney;
	}

}

