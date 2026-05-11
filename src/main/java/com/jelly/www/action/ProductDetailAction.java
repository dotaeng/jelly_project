package com.jelly.www.action;

import com.jelly.www.dao.ProductDAO;
import com.jelly.www.dao.ProductSellerDAO;
import com.jelly.www.vo.ProductVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.*;

public class ProductDetailAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // productId 가져오기
        String productIdParam = request.getParameter("productId");
        int productId;

        try {
            productId = Integer.parseInt(productIdParam);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "유효하지 않은 상품 ID입니다.");
            return "/views/error/404.jsp";
        }

        // DAO 객체 생성
        ProductDAO productDAO = new ProductDAO();
        ProductSellerDAO productSellerDAO = new ProductSellerDAO();

        // 상품 상세 정보 조회
        ProductVO product = productDAO.selectOne(productId);

        if (product == null) {
            request.setAttribute("error", "해당 상품을 찾을 수 없습니다.");
            return "/views/error/404.jsp";
        }

        // 모델 번호 포맷팅
        String formattedModelNumber = productDAO.getFormattedModelNumber(productId);
        request.setAttribute("formattedModelNumber", formattedModelNumber);

        // 발매가 그대로 전달
        int initialPrice = product.getInitialPrice();

        // 구매가와 판매가 평균 가격 조회
        int averagePurchasePrice = productSellerDAO.getAveragePurchasePrice(productId);
        int averageSellPrice = productSellerDAO.getAverageSellPrice(productId);
        int lowestPrice = productSellerDAO.getLowestPrice(productId);

        // 1의 자리 버림 처리
        averagePurchasePrice = (averagePurchasePrice / 10) * 10;
        averageSellPrice = (averageSellPrice / 10) * 10;

        // 구매가 평균 가격이 없으면 발매가로 대체
        int averagePurchasePriceToUse = averagePurchasePrice > 0 ? averagePurchasePrice : initialPrice;
        // 판매가 평균 가격이 없으면 발매가로 대체
        int averageSellPriceToUse = averageSellPrice > 0 ? averageSellPrice : initialPrice;
        // 최저가가 없으면 발매가로 대체
        int lowestPriceToUse = lowestPrice > 0 ? lowestPrice : initialPrice;

        // 고정 사이즈 목록 (210~290)
        List<String> sizeList = Arrays.asList("210", "220", "230", "240", "250", "260", "270", "280", "290");

        // 사이즈별 가격 조회
        Map<String, Integer> sizePriceMap = new HashMap<>();
        for (ProductVO sizePrice : productDAO.selectSizesAndPricesByProductId(productId)) {
            Integer price = sizePrice.getPrice();
            if (price == null || price == 0) {
                sizePriceMap.put(sizePrice.getSize(), -1); // 가격이 없으면 -1로 설정
            } else {
                sizePriceMap.put(sizePrice.getSize(), price); // 실제 가격
            }
        }

        // 구매 입찰과 판매 입찰 버튼 텍스트를 따로 생성
        Map<String, String> buySizeButtons = new HashMap<>();
        Map<String, String> sellSizeButtons = new HashMap<>();

        for (String size : sizeList) {
            int price = sizePriceMap.getOrDefault(size, -1);

            // 구매 입찰 버튼 텍스트 설정
            if (price == -1) {
                buySizeButtons.put(size, "구매 입찰"); // 문자열로 설정
            } else {
                buySizeButtons.put(size, String.valueOf(price)); // 숫자를 문자열로 변환
            }

            // 판매 입찰 버튼 텍스트 설정
            if (price == -1) {
                sellSizeButtons.put(size, "판매 입찰"); // 문자열로 설정
            } else {
                sellSizeButtons.put(size, String.valueOf(price)); // 숫자를 문자열로 변환
            }
        }

        productDAO.close();

        // JSP로 전달할 데이터 설정
        request.setAttribute("product", product); // 상품 정보
        request.setAttribute("initialPrice", initialPrice); // 발매가
        request.setAttribute("averagePurchasePrice", averagePurchasePriceToUse); // 구매가 평균 
        request.setAttribute("averageSellPrice", averageSellPriceToUse); // 판매가 평균
        request.setAttribute("lowestPrice", lowestPriceToUse); // 최저가
        request.setAttribute("sizeList", sizeList); // 고정 사이즈 리스트
        request.setAttribute("buySizeButtons", buySizeButtons); // 구매 입찰 버튼 텍스트
        request.setAttribute("sellSizeButtons", sellSizeButtons); // 판매 입찰 버튼 텍스트

        return "/views/product/productDetail.jsp";
    }
}