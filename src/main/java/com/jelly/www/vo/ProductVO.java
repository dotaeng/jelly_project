package com.jelly.www.vo;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO {
    private int productId;           // 상품 ID
    private String productName;      // 상품 이름
    private String description;      // 상품 설명
    private String brand;            // 브랜드
    private Date releaseDate;        // 출시일
    private int initialPrice;        // 발매가
    private String modelNumber;      // 모델 번호
    private int categoryId;          // 카테고리 ID
    private String imageUrl;         // 이미지 URL
    private boolean isActive;        // 활성 상태
    private Date createdAt;          // 생성일
    private Date updatedAt;          // 수정일

    private List<String> sizes;      // 사이즈 목록
    private String size;             // 단일 사이즈 조회용
    private int price;               // 가격 조회용
    private int totalSales;          // 총 판매량 (인기 상품 조회용)
    
    private String formattedPrice;      // 포맷팅 처리된 가격



    // 전체
    public ProductVO(int productId, String productName, String description, String brand, Date releaseDate, int initialPrice,
                     String modelNumber, int categoryId, String imageUrl, boolean isActive, Date createdAt, Date updatedAt) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.brand = brand;
        this.releaseDate = releaseDate;
        this.initialPrice = initialPrice;
        this.modelNumber = modelNumber;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // 필터링 메서드에서 쓰는거임
    public ProductVO(int productId, String productName, String description, String brand, Date releaseDate, int initialPrice,
                     String imageUrl) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.brand = brand;
        this.releaseDate = releaseDate;
        this.initialPrice = initialPrice;
        this.imageUrl = imageUrl;
    }

    // 사이즈, 가격 조회용
    public ProductVO(String size, int price) {
        this.size = size;
        this.price = price;
    }

    // 인기 상품 조회용
    public ProductVO(int productId, String productName, String brand, int initialPrice, String imageUrl, int totalSales) {
        this.productId = productId;
        this.productName = productName;
        this.brand = brand;
        this.initialPrice = initialPrice;
        this.imageUrl = imageUrl;
        this.totalSales = totalSales;
    }
}