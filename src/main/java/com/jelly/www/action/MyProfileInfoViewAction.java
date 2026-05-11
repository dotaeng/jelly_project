package com.jelly.www.action;

import com.jelly.www.dao.MyProfileInfoDAO;
import com.jelly.www.vo.UserProfileInfoVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class MyProfileInfoViewAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        
        // 세션 userI
        Object userIdObj = session.getAttribute("user_id");
        if (userIdObj == null) {
            return "redirect:/jelly?page=login";
        }

        int userId = Integer.parseInt(userIdObj.toString());
        MyProfileInfoDAO dao = new MyProfileInfoDAO();
        UserProfileInfoVO userProfile = null;

        try {
            userProfile = dao.getUserProfile(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return "/views/error/serverError.jsp";
        }

        if (userProfile == null) {
            return "/views/error/userNotFound.jsp";
        }

        request.setAttribute("userProfile", userProfile);

        return "/views/mypage/profileInfo.jsp";
    }
}