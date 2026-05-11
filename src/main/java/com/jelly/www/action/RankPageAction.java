package com.jelly.www.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class RankPageAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");
        
        // calculate rank
        
		// 로그인 여부 확인
        if (userId == null) {
            return "redirect:/jelly?page=login";
        }
		return "/views/rank/rank.jsp";
	}
}
