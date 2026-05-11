package com.jelly.www.action;

import com.jelly.www.dao.MyProfileInfoDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyProfileInfoUpdateAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // 세션에서 user_id
        int userId = Integer.parseInt(request.getSession().getAttribute("user_id").toString());
        
        // 닉네임과 프로필 이미지
        String nickname = request.getParameter("nickname");
        String profileImage = request.getParameter("profileImage");

        // DAO 객체 생성
        MyProfileInfoDAO dao = new MyProfileInfoDAO();
        
        // 닉네임 업데이트
        boolean isUpdated = dao.updateNickname(userId, nickname);

        // 프로필 이미지가 있으면 업데이트
        if (profileImage != null && !profileImage.isEmpty()) {
            isUpdated = dao.updateProfileImage(userId, profileImage);
        }

        // 결과 메시지 설정
        if (isUpdated) {
            request.setAttribute("message", "프로필이 성공적으로 수정되었습니다.");
        } else {
            request.setAttribute("message", "프로필 수정에 실패했습니다.");
        }

        // 프로필 관리 페이지로 이동
        return "/views/mypage/profileInfo.jsp";
    }
}