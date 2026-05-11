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
public class CouponVO {
	int couponId;
	String couponCode;
	String description;
	String couponType;
	int discountAmount;
	int discountPercentage;
	int minimumOrderAmount;
	String expiryDate;
	Timestamp createdAt;
	Timestamp updatedAt;

	// 쿠폰 정보 조회 (쿠폰 아이디, 쿠폰 코드, 쿠폰 상세, 할인금액, 할인율, 최소금액, 유효기간)
	public CouponVO(int couponId, String couponCode, String description, int discountAmount, int discountPercentage, int minimumOrderAmount, String expiryDate ) {
		this.couponId = couponId;
		this.couponCode = couponCode;
		this.description = description;
		this.discountAmount = discountAmount;
		this.discountPercentage = discountPercentage;
		this.minimumOrderAmount = minimumOrderAmount;
		this.expiryDate = expiryDate;
	}
	
	// 사용자가 선택한 쿠폰 조회용
	public CouponVO(int discountAmount, int discountPercentage) {
		this.discountPercentage = discountPercentage;
		this.discountPercentage = discountPercentage;
	}
}
