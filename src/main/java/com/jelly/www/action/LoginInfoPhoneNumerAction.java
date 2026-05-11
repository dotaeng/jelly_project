package com.jelly.www.action;

import com.jelly.www.dao.UserDAO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginInfoPhoneNumerAction implements Action{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String userphonenum = req.getParameter("userphonenum");
		HttpSession session = req.getSession(true);
        Object obj = session.getAttribute("user");
        UserVO user = (UserVO) obj;
        int sessionuserid = user.getUserId();
        
        UserDAO dao = new UserDAO();
        if(userphonenum != null) {
        	UserVO user1 = new UserVO();
        	user1.setPhoneNumber(userphonenum);
        	
        	// 이메일 변경 메서드
        	dao.updateInfoUserPhoneNumber(userphonenum, sessionuserid);
        	session.setAttribute("userphonenum", user1.getPhoneNumber());
        	
        	req.setAttribute("successMessage", "휴대폰 번호가 수정되었습니다");
            System.out.println("휴대폰 번호 수정 완료");
            return "views/mypage/loginInfo.jsp";
        }
        req.setAttribute("errorMessage", "휴대폰 번호를 정확히 입력하세요");
		return "/views/mypage/loginInfo.jsp";
	}

}
