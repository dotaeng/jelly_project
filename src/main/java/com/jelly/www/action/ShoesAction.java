package com.jelly.www.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.jelly.www.dao.ProductDAO;
import com.jelly.www.dao.ProductSellerDAO;
import com.jelly.www.vo.ProductVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoesAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // 페이지 번호 파라미터 받기
        String pageParam = request.getParameter("pageNo");
        int pageNo = 1; // 기본 페이지
        if (pageParam != null && !pageParam.trim().isEmpty()) {
            try {
                pageNo = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                pageNo = 1;
            }
        }

        // 한 페이지에 불러올 상품 -> 10개씩
        int limit = 10;

        // DAO를 사용하여 상품 조회
        ProductDAO productDAO = new ProductDAO();
        List<ProductVO> productList = productDAO.selectByCategoryAndPage("신발", pageNo, limit);


        // 구매 평균가 조회
        ProductSellerDAO productSellerDAO = new ProductSellerDAO();
        Map<Integer, Integer> averagePurchasePriceMap = new HashMap<>();
        for (ProductVO product : productList) {
            int averagePurchasePrice = productSellerDAO.getAveragePurchasePrice(product.getProductId());

            // 구매 평균가가 0이라면 발매가를 사용
            if (averagePurchasePrice == 0) {
                averagePurchasePrice = product.getInitialPrice();  // 발매가 사용
            }

            averagePurchasePriceMap.put(product.getProductId(), averagePurchasePrice);
        }

        // 상품 리스트와 평균 구매 가격 정보를 request에 저장
        request.setAttribute("productList", productList);

        request.setAttribute("averagePurchasePriceMap", averagePurchasePriceMap);

        // AJAX 요청인지 일반 페이지 요청인지 확인
        if (request.getParameter("pageNo") != null) {
            // AJAX 요청 시 상품 리스트 HTML만 반환
            return "/views/product/productShoesList.jsp";
        }

        // 일반 페이지 요청 시 전체 상품 페이지 반환
        request.setAttribute("currentCategory", "신발");
        request.setAttribute("currentPage", "shoes");
        return "/views/product/productShoes.jsp";
    }
}