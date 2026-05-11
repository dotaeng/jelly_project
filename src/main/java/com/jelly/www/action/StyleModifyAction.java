package com.jelly.www.action;

import com.jelly.www.dao.PostDAO;
import com.jelly.www.vo.PostVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StyleModifyAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String pId = request.getParameter("postId");

		if (pId != null) {
			int postId = Integer.parseInt(pId);
			
			// 게시물 가져오기
			PostDAO postDao = new PostDAO();
			PostVO postVo = postDao.selectOne(postId);
			
			postDao.close();
			// 요청 파라미터 설정
			request.setAttribute("postVo", postVo);
		}

		return "/views/style/styleModify.jsp";
	}
}
