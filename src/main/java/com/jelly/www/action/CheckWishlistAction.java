package com.jelly.www.action;

import com.google.gson.JsonObject;
import com.jelly.www.dao.WishlistDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CheckWishlistAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // 요청 파라미터에서 userId와 productId 가져오기
        int userId = Integer.parseInt(req.getParameter("userId"));
        int productId = Integer.parseInt(req.getParameter("productId"));
        
        // WishlistDAO 객체 생성
        WishlistDAO wishlistDAO = new WishlistDAO();
        
        // 관심상품이 존재하는지 확인
        boolean isWishlist = wishlistDAO.isWishlistExists(userId, productId);
        
        // JSON 응답 생성
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("isWishlist", isWishlist);  // 관심상품 여부
        
        try {
            // 응답 설정
            resp.setContentType("application/json");
            resp.getWriter().write(jsonResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
}