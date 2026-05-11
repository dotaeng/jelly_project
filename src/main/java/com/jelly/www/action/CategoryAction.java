package com.jelly.www.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.jelly.www.dao.ProductDAO;
import com.jelly.www.vo.ProductVO;

import java.util.List;

public class CategoryAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // 카테고리 이름 가져오기
        String category = request.getParameter("category");
        if (category == null || category.isEmpty()) {
            category = "신발"; // 기본 카테고리
        }

        // DAO를 사용해 카테고리별 상품 조회
        ProductDAO productDAO = new ProductDAO();
        List<ProductVO> productList = productDAO.selectByCategory(category);
        productDAO.close();

        // 상품 리스트를 request 속성에 저장
        request.setAttribute("productList", productList);
        request.setAttribute("currentCategory", category);

        // 이동할 JSP 경로 반환 (카테고리에 따라 동적 JSP 결정 가능)
        return "/views/product/productList.jsp";
    }
}