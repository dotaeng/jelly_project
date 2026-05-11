package com.jelly.www.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.jelly.www.dao.ProductDAO;
import com.jelly.www.vo.ProductVO;

import java.util.List;

public class ProductPopularAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // DAO 인스턴스 생성
        ProductDAO productDAO = new ProductDAO();

        // 인기 상품 조회 (판매량 기준)
        List<ProductVO> popularProducts = productDAO.getPopularProducts();

        // 인기 상품 데이터를 request에 저장
        request.setAttribute("popularProducts", popularProducts);

        // popular.jsp로 포워딩
        return "/views/popular/popular.jsp";
    }
}