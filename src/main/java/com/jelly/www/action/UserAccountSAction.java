package com.jelly.www.action;

import com.jelly.www.dao.UserAccountDAO;
import com.jelly.www.vo.UserAccountVO;
import com.jelly.www.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserAccountSAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        int userId = user.getUserId();

        UserAccountDAO dao = new UserAccountDAO();

        UserAccountVO user2 = dao.selectAccount(userId);

        if (user2 != null) {

        	session.setAttribute("bankname", user2.getBankName());
            session.setAttribute("accountnum", user2.getAccountNumber());
            session.setAttribute("accountname", user2.getAccountHolder());

            System.out.println("조회 완료");
        } else {
        	System.out.println("조회 실패");
        }

        return "views/mypage/userAccount.jsp";
    }
}