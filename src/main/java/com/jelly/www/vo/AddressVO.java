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
public class AddressVO {
	int addressId;
	int userId;
	String addressLine1;
	String addressLine2;
	String postalCode;
	Boolean isDefault;
	Timestamp createdAt;
	Timestamp updatedAt;

	// 주소 조회용
	public AddressVO(int addressId, String postalCode, String addressLine1, String addressLine2) {
		this.addressId = addressId;
		this.postalCode = postalCode;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
	}
	
	// 주소 

}
