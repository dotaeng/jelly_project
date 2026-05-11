package com.jelly.www.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.jelly.www.vo.ProductVO;

public class ProductDAO {
    // DB 연결 정보
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/jelly";
    String user = "scott";
    String password = "tiger";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    StringBuffer sb = new StringBuffer();

    // 생성자
    public ProductDAO() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL 드라이버 로드 실패");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("MySQL DB 연결 실패");
            e.printStackTrace();
        }
    }

    // 1. 전체 조회
    public List<ProductVO> selectAll() {
        List<ProductVO> list = new ArrayList<>();
        sb.setLength(0);
        sb.append("SELECT PRODUCT_ID, PRODUCT_NAME, DESCRIPTION, BRAND, RELEASE_DATE, INITIAL_PRICE, ");
        sb.append("MODEL_NUMBER, CATEGORY_ID, IMAGE_URL, IS_ACTIVE, CREATED_AT, UPDATED_AT FROM PRODUCT");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ProductVO vo = new ProductVO(
                    rs.getInt("PRODUCT_ID"),
                    rs.getString("PRODUCT_NAME"),
                    rs.getString("DESCRIPTION"),
                    rs.getString("BRAND"),
                    rs.getDate("RELEASE_DATE"),
                    rs.getInt("INITIAL_PRICE"),
                    rs.getString("MODEL_NUMBER"),
                    rs.getInt("CATEGORY_ID"),
                    rs.getString("IMAGE_URL"),
                    rs.getBoolean("IS_ACTIVE"),
                    rs.getDate("CREATED_AT"),
                    rs.getDate("UPDATED_AT")
                );
                list.add(vo);
                System.out.println("조회된 상품: " + vo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return list;
    }

    // 2. 상세 조회
    public ProductVO selectOne(int productId) {
        ProductVO vo = null;
        sb.setLength(0);
        sb.append("SELECT PRODUCT_ID, PRODUCT_NAME, DESCRIPTION, BRAND, RELEASE_DATE, INITIAL_PRICE, ");
        sb.append("MODEL_NUMBER, CATEGORY_ID, IMAGE_URL, IS_ACTIVE, CREATED_AT, UPDATED_AT ");
        sb.append("FROM PRODUCT WHERE PRODUCT_ID = ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, productId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                vo = new ProductVO(
                        rs.getInt("PRODUCT_ID"),
                        rs.getString("PRODUCT_NAME"),
                        rs.getString("DESCRIPTION"),
                        rs.getString("BRAND"),
                        rs.getDate("RELEASE_DATE"),
                        rs.getInt("INITIAL_PRICE"),
                        rs.getString("MODEL_NUMBER"),
                        rs.getInt("CATEGORY_ID"),
                        rs.getString("IMAGE_URL"),
                        rs.getBoolean("IS_ACTIVE"),
                        rs.getDate("CREATED_AT"),
                        rs.getDate("UPDATED_AT")
                );
            }

            // 상품이 존재하면 사이즈 목록을 추가로 조회
            if (vo != null) {
                // 사이즈 목록 조회
                List<String> sizes = selectSizesByProductId(productId);
                vo.setSizes(sizes); // 사이즈 추가
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return vo;
    }

    // 3. 데이터 추가
    public void insertOne(ProductVO vo) {
        sb.setLength(0);
        sb.append("INSERT INTO PRODUCT (PRODUCT_NAME, DESCRIPTION, BRAND, RELEASE_DATE, INITIAL_PRICE, ");
        sb.append("MODEL_NUMBER, CATEGORY_ID, IMAGE_URL, IS_ACTIVE, CREATED_AT, UPDATED_AT) ");
        sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, vo.getProductName());
            pstmt.setString(2, vo.getDescription());
            pstmt.setString(3, vo.getBrand());
            pstmt.setDate(4, new java.sql.Date(vo.getReleaseDate().getTime()));
            pstmt.setInt(5, vo.getInitialPrice());
            pstmt.setString(6, vo.getModelNumber());
            pstmt.setInt(7, vo.getCategoryId());
            pstmt.setString(8, vo.getImageUrl());
            pstmt.setBoolean(9, vo.isActive());
            pstmt.executeUpdate();
            System.out.println("상품 추가 완료: " + vo);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    // 4. 데이터 삭제
    public void deleteOne(int productId) {
        sb.setLength(0);
        sb.append("DELETE FROM PRODUCT WHERE PRODUCT_ID = ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, productId);
            pstmt.executeUpdate();
            System.out.println("상품 삭제 완료: ID = " + productId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    // 5. 데이터 수정
    public void updateOne(ProductVO vo) {
        sb.setLength(0);
        sb.append("UPDATE PRODUCT SET PRODUCT_NAME = ?, DESCRIPTION = ?, BRAND = ?, RELEASE_DATE = ?, ");
        sb.append("INITIAL_PRICE = ?, MODEL_NUMBER = ?, CATEGORY_ID = ?, IMAGE_URL = ?, IS_ACTIVE = ?, ");
        sb.append("UPDATED_AT = NOW() WHERE PRODUCT_ID = ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, vo.getProductName());
            pstmt.setString(2, vo.getDescription());
            pstmt.setString(3, vo.getBrand());
            pstmt.setDate(4, new java.sql.Date(vo.getReleaseDate().getTime()));
            pstmt.setInt(5, vo.getInitialPrice());
            pstmt.setString(6, vo.getModelNumber());
            pstmt.setInt(7, vo.getCategoryId());
            pstmt.setString(8, vo.getImageUrl());
            pstmt.setBoolean(9, vo.isActive());
            pstmt.setInt(10, vo.getProductId());
            pstmt.executeUpdate();
            System.out.println("상품 수정 완료: " + vo);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
    
    // 6. 필터
    public List<ProductVO> filterByBrandsCategoriesAndPrice(List<String> brands, List<Integer> categories, String priceRange) {
        List<ProductVO> list = new ArrayList<>();
        sb.setLength(0);

        // 기본 SQL 작성
        sb.append("SELECT p.PRODUCT_ID, p.PRODUCT_NAME, p.DESCRIPTION, p.BRAND, p.RELEASE_DATE, ");
        sb.append("p.INITIAL_PRICE, p.IMAGE_URL ");
        sb.append("FROM PRODUCT p ");
        sb.append("JOIN CATEGORY c ON p.CATEGORY_ID = c.CATEGORY_ID ");
        sb.append("WHERE 1=1 ");

        // 브랜드 필터 조건 추가
        if (brands != null && !brands.isEmpty()) {
            sb.append("AND p.BRAND IN (");
            sb.append(String.join(",", brands.stream().map(b -> "?").toArray(String[]::new)));
            sb.append(") ");
        }

        // 카테고리 필터 조건 추가
        if (categories != null && !categories.isEmpty()) {
            sb.append("AND (p.CATEGORY_ID IN (");
            sb.append(String.join(",", categories.stream().map(c -> "?").toArray(String[]::new)));
            sb.append(") OR c.PARENT_ID IN (");
            sb.append(String.join(",", categories.stream().map(c -> "?").toArray(String[]::new)));
            sb.append(")) ");
        }

        // 가격 필터 조건 추가
        if (priceRange != null) {
            switch (priceRange) {
                case "50000":
                    sb.append("AND p.INITIAL_PRICE <= 50000 ");
                    break;
                case "100000":
                    sb.append("AND p.INITIAL_PRICE > 50000 AND p.INITIAL_PRICE <= 100000 ");
                    break;
                case "200000":
                    sb.append("AND p.INITIAL_PRICE > 100000 AND p.INITIAL_PRICE <= 200000 ");
                    break;
                case "300000":
                    sb.append("AND p.INITIAL_PRICE > 200000 ");
                    break;
            }
        }

        try {
            PreparedStatement pstmt = conn.prepareStatement(sb.toString());
            int paramIndex = 1;

            // 브랜드 필터 값 설정
            if (brands != null && !brands.isEmpty()) {
                for (String brand : brands) {
                    pstmt.setString(paramIndex++, brand);
                }
            }

            // 카테고리 필터 값 설정
            if (categories != null && !categories.isEmpty()) {
                for (Integer category : categories) {
                    pstmt.setInt(paramIndex++, category); // CATEGORY_ID
                }
                for (Integer category : categories) { // PARENT_ID
                    pstmt.setInt(paramIndex++, category);
                }
            }

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ProductVO product = new ProductVO(
                    rs.getInt("PRODUCT_ID"),
                    rs.getString("PRODUCT_NAME"), 
                    rs.getString("DESCRIPTION"),
                    rs.getString("BRAND"),
                    rs.getDate("RELEASE_DATE"),
                    rs.getInt("INITIAL_PRICE"),
                    rs.getString("IMAGE_URL")
                );
                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return list;
    }
    

    // 카테고리별 상품 조회
    public List<ProductVO> selectByCategory(String categoryName) {
        List<ProductVO> list = new ArrayList<>();
        sb.setLength(0);  // StringBuffer 초기화

        // 해당 카테고리와 하위 카테고리 포함 조회
        sb.append("SELECT p.PRODUCT_ID, p.PRODUCT_NAME, p.DESCRIPTION, p.BRAND, p.RELEASE_DATE, ");
        sb.append("p.INITIAL_PRICE, p.MODEL_NUMBER, p.CATEGORY_ID, p.IMAGE_URL, p.IS_ACTIVE, ");
        sb.append("p.CREATED_AT, p.UPDATED_AT ");
        sb.append("FROM PRODUCT p ");
        sb.append("JOIN CATEGORY c ON p.CATEGORY_ID = c.CATEGORY_ID ");
        sb.append("WHERE c.NAME = ? OR c.PARENT_ID = (SELECT CATEGORY_ID FROM CATEGORY WHERE NAME = ?)");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, categoryName);
            pstmt.setString(2, categoryName); 
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ProductVO vo = new ProductVO(
                    rs.getInt("PRODUCT_ID"),
                    rs.getString("PRODUCT_NAME"),
                    rs.getString("DESCRIPTION"),
                    rs.getString("BRAND"),
                    rs.getDate("RELEASE_DATE"),
                    rs.getInt("INITIAL_PRICE"),
                    rs.getString("MODEL_NUMBER"),
                    rs.getInt("CATEGORY_ID"),
                    rs.getString("IMAGE_URL"),
                    rs.getBoolean("IS_ACTIVE"),
                    rs.getDate("CREATED_AT"),
                    rs.getDate("UPDATED_AT")
                );
                list.add(vo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return list;
    }
    
    // 무한 스크롤 관련
    public List<ProductVO> selectByCategoryAndPage(String category, int page, int limit) {
        List<ProductVO> list = new ArrayList<>();
        if (page <= 0) page = 1;  // 페이지 번호가 0 이하이면 1로 설정
        if (limit <= 0) limit = 10;  // 한 페이지에 출력할 상품 수 기본값 10

        int offset = (page - 1) * limit;  // 페이지 번호에 따라 데이터의 시작 위치

        sb.setLength(0);
        sb.append("SELECT PRODUCT_ID, PRODUCT_NAME, DESCRIPTION, BRAND, RELEASE_DATE, INITIAL_PRICE, ");
        sb.append("MODEL_NUMBER, CATEGORY_ID, IMAGE_URL, IS_ACTIVE, CREATED_AT, UPDATED_AT ");
        sb.append("FROM PRODUCT ");
        sb.append("WHERE CATEGORY_ID IN ( ");
        sb.append("   SELECT CATEGORY_ID FROM CATEGORY WHERE NAME = ? OR PARENT_ID = ");
        sb.append("   (SELECT CATEGORY_ID FROM CATEGORY WHERE NAME = ?) ");
        sb.append(") ");
        sb.append("ORDER BY RELEASE_DATE DESC, PRODUCT_ID DESC LIMIT ? OFFSET ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, category);  // 상위 카테고리 이름
            pstmt.setString(2, category);  // 하위 카테고리의 parent_id를 가져오기 위해 category 이름 사용
            pstmt.setInt(3, limit);  // 한 페이지에 출력할 상품 수 설정
            pstmt.setInt(4, offset);  // 페이지네이션에 따른 데이터 시작 위치 설정

            rs = pstmt.executeQuery();

            while (rs.next()) {
                ProductVO product = new ProductVO(
                    rs.getInt("PRODUCT_ID"),
                    rs.getString("PRODUCT_NAME"),
                    rs.getString("DESCRIPTION"),
                    rs.getString("BRAND"),
                    rs.getDate("RELEASE_DATE"),
                    rs.getInt("INITIAL_PRICE"),
                    rs.getString("MODEL_NUMBER"),
                    rs.getInt("CATEGORY_ID"),
                    rs.getString("IMAGE_URL"),
                    rs.getBoolean("IS_ACTIVE"),
                    rs.getDate("CREATED_AT"),
                    rs.getDate("UPDATED_AT")
                );
                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return list;
    }

    // 상품 사이즈 목록 조회
    public List<String> selectSizesByProductId(int productId) {
        List<String> sizes = new ArrayList<>();
        sb.setLength(0);

        sb.append("SELECT SIZE FROM SIZE WHERE PRODUCT_ID = ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());  
            pstmt.setInt(1, productId); 
            rs = pstmt.executeQuery();

            while (rs.next()) {
                sizes.add(rs.getString("SIZE")); // SIZE 값 추가
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return sizes;
    }

    // PreparedStatement에서 사용할 플레이스홀더를 생성하는 도우미 메서드
    private List<String> getPlaceholders(int count) {
        // ArrayList를 생성하여 플레이스홀더를 저장할 리스트를 준비
        List<String> placeholders = new ArrayList<>();

        // count 만큼 반복하면서 "?" 플레이스홀더를 추가
        for (int i = 0; i < count; i++) {
            placeholders.add("?"); // 플레이스홀더 "?" 추가
        }

        // 생성된 플레이스홀더 리스트를 반환
        return placeholders;
    }


    // 사이즈와 가격 조회 (상품 모든 사이즈버튼, 구매, 판매 버튼 누르면 나오는 모달창에 사용)
    public List<ProductVO> selectSizesAndPricesByProductId(int productId) {
        List<ProductVO> sizePriceList = new ArrayList<>();
        sb.setLength(0);
        sb.append("SELECT SIZE, PRICE FROM SIZE WHERE PRODUCT_ID = ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, productId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ProductVO vo = new ProductVO();
                vo.setSize(rs.getString("SIZE"));
                vo.setPrice(rs.getInt("PRICE"));
                sizePriceList.add(vo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return sizePriceList;
    }

    // 검색어 관련 메서드
    public List<ProductVO> searchProducts(String query) {
        List<ProductVO> list = new ArrayList<>();
        sb.setLength(0);
        sb.append("SELECT PRODUCT_ID, PRODUCT_NAME, DESCRIPTION, BRAND, RELEASE_DATE, INITIAL_PRICE, ");
        sb.append("MODEL_NUMBER, CATEGORY_ID, IMAGE_URL, IS_ACTIVE, CREATED_AT, UPDATED_AT ");
        sb.append("FROM PRODUCT WHERE PRODUCT_NAME LIKE ? OR MODEL_NUMBER LIKE ? OR BRAND LIKE ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            String searchKeyword = "%" + query + "%";
            pstmt.setString(1, searchKeyword);
            pstmt.setString(2, searchKeyword);
            pstmt.setString(3, searchKeyword);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                ProductVO vo = new ProductVO(
                    rs.getInt("PRODUCT_ID"),
                    rs.getString("PRODUCT_NAME"),
                    rs.getString("DESCRIPTION"),
                    rs.getString("BRAND"),
                    rs.getDate("RELEASE_DATE"),
                    rs.getInt("INITIAL_PRICE"),
                    rs.getString("MODEL_NUMBER"),
                    rs.getInt("CATEGORY_ID"),
                    rs.getString("IMAGE_URL"),
                    rs.getBoolean("IS_ACTIVE"),
                    rs.getDate("CREATED_AT"),
                    rs.getDate("UPDATED_AT")
                );
                list.add(vo);
                System.out.println("검색된 상품: " + vo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return list;
    }
    
    // 판매량이 높은 상품 조회 메서드 (Popular에서 사용)
    public List<ProductVO> getPopularProducts() {
        List<ProductVO> productList = new ArrayList<>();
        sb.setLength(0);
        sb.append("SELECT ");
        sb.append("p.product_id, ");
        sb.append("p.product_name, ");
        sb.append("p.brand, ");
        sb.append("p.initial_price, ");
        sb.append("p.image_url, ");
        sb.append("COUNT(t.trade_id) AS total_sales ");
        sb.append("FROM PRODUCT p ");
        sb.append("JOIN PRODUCT_SELLER ps ON p.product_id = ps.product_id ");
        sb.append("JOIN TRADE t ON t.product_seller_id = ps.product_seller_id ");
        sb.append("WHERE p.is_active = TRUE ");
        sb.append("GROUP BY p.product_id, p.product_name, p.brand, p.initial_price, p.image_url ");
        sb.append("ORDER BY total_sales DESC, p.created_at DESC ");
        sb.append("LIMIT 10");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ProductVO product = new ProductVO(
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getString("brand"),
                    rs.getInt("initial_price"),
                    rs.getString("image_url"),
                    rs.getInt("total_sales")
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return productList;
    }

    // 모델 번호 포맷팅 메서드
    public String getFormattedModelNumber(int productId) {
        String modelNumber = null;
        sb.setLength(0);
        sb.append("SELECT MODEL_NUMBER FROM PRODUCT WHERE PRODUCT_ID = ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, productId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                modelNumber = rs.getString("MODEL_NUMBER");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        // 모델 번호 포맷팅
        if (modelNumber != null) {
            modelNumber = formatModelNumber(modelNumber);
        }
        return modelNumber;
    }

    // 모델 번호를 8자리마다 줄바꿈 처리
    private String formatModelNumber(String modelNumber) {
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < modelNumber.length(); i++) {
            formatted.append(modelNumber.charAt(i));
            if ((i + 1) % 8 == 0 && i != modelNumber.length() - 1) {
                formatted.append("<br>");
            }
        }
        return formatted.toString();
    }
    
    // 자원 해제 메서드
    public void close() {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}