package com.jelly.www.action;

import com.jelly.www.dao.UserAccountDAO;
import com.jelly.www.vo.UserAccountVO;
import com.jelly.www.vo.UserVO;
import com.mysql.cj.Session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class UserAccountAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
    	
        String bankname = req.getParameter("bankname");
        String accountnum = req.getParameter("accountnum");
        String accountname = req.getParameter("accountname");
        
        HttpSession session = req.getSession(true);
        
        UserVO user = (UserVO) session.getAttribute("user");
        int sessionuserid = user.getUserId();
        UserAccountDAO dao = new UserAccountDAO();
        
        // 은행명 유효성 검사
        if (bankname == null || "default".equals(bankname)) {
            req.setAttribute("bankNameError", "은행을 선택해주세요.");
            return "views/mypage/userAccount.jsp";
        }

        // 계좌번호 유효성 검사 
        if (accountnum == null || accountnum.length() <= 10) {
            req.setAttribute("errorMessage", "계좌번호를 정확히 입력하세요");
            return "views/mypage/userAccount.jsp";
        }
        
        // 세션 이름과 동일한지 검사
        if (!accountname.equals(user.getUserName())) {
            req.setAttribute("userNameError", "회원 이름이 일치하지 않습니다.");
            return "views/mypage/userAccount.jsp";
        }
        
        UserAccountVO account = new UserAccountVO();
        account.setBankName(bankname);
        account.setAccountNumber(accountnum);
        account.setAccountHolder(accountname);
        account.setUserId(sessionuserid);

        // DB에 있으면 update, 없으면 insert
        UserAccountVO existing = dao.selectAccount(sessionuserid);
        if (existing != null) {
            dao.updateAccount(account);
            req.setAttribute("successMessage", "계좌 정보가 수정되었습니다.");
        } else {
            dao.insertAccount(account);
            req.setAttribute("successMessage", "판매 정산 계좌가 저장되었습니다.");
        }

        session.setAttribute("bankname", account.getBankName());
        session.setAttribute("accountnum", account.getAccountNumber());
        session.setAttribute("accountname", account.getAccountHolder());

        return "views/mypage/userAccount.jsp";
    }
}
