package com.jelly.www.action;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.jelly.www.dao.WishlistDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class WishlistToggleAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
	    String action = request.getParameter("page");
	    try {
	        int userId = Integer.parseInt(request.getParameter("userId"));
	        int productId = Integer.parseInt(request.getParameter("productId"));

	        WishlistDAO dao = new WishlistDAO();
	        JsonObject jsonResponse = new JsonObject();

	        if ("checkWishlist".equals(action)) {
	            // 관심상품 상태 확인
	            boolean isWishlist = dao.isWishlistExists(userId, productId);
	            jsonResponse.addProperty("isWishlist", isWishlist);
	        } else if ("wishlistToggle".equals(action)) {
	            // 관심상품 추가/삭제 토글
	            boolean isExists = dao.isWishlistExists(userId, productId);
	            if (isExists) {
	                dao.removeWishlist(userId, productId);
	                jsonResponse.addProperty("status", "removed");
	            } else {
	                dao.addWishlist(userId, productId);
	                jsonResponse.addProperty("status", "added");
	            }
	        }

	        response.setContentType("application/json");
	        response.getWriter().write(jsonResponse.toString());

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}