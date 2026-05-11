package com.jelly.www.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistVO {
    private int wishlistId;     // 찜 고유 ID
    private int userId;         // 사용자 ID
    private int productId;      // 상품 ID
    private String createdAt;   // 생성 날짜
    private String updatedAt;   // 수정 날짜
}