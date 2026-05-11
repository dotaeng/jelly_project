package com.jelly.www.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.jelly.www.dao.ProductDAO;
import com.jelly.www.vo.ProductVO;

import java.util.List;

public class OuterAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // 아우터 카테고리 상품 조회
        ProductDAO productDAO = new ProductDAO();
        List<ProductVO> productList = productDAO.selectByCategory("아우터");

        // 상품 리스트를 request 속성에 저장
        request.setAttribute("productList", productList);
        request.setAttribute("currentCategory", "아우터");
        productDAO.close();
        // 이동할 JSP 경로 반환
        return "/views/product/productOuter.jsp";
    }
}