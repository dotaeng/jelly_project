package com.jelly.www.action;

import java.util.ArrayList;

import com.jelly.www.dao.FollowDAO;
import com.jelly.www.dao.UserDAO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FollowingAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String uId = request.getParameter("userId");

		if (uId != null) {
			int userId = Integer.parseInt(uId);
			
			// 팔로잉 리스트 가져오기
			FollowDAO followDao = new FollowDAO();
			ArrayList<Integer> followingList = followDao.getFollowingIdList(userId);
			ArrayList<UserVO> followingUserList = new ArrayList<UserVO>();
			UserDAO userDao = new UserDAO();
			for(Integer followingId : followingList) {
				followingUserList.add(userDao.selectOne(followingId));
			}
			
			request.setAttribute("followingUserList", followingUserList);
			userDao.close();
			followDao.close();
		}

		return "/views/style/following.jsp";
	}

}
