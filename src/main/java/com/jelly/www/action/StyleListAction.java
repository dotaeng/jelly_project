package com.jelly.www.action;

import java.io.IOException;
import java.util.ArrayList;

import com.jelly.www.dao.FollowDAO;
import com.jelly.www.dao.PostDAO;
import com.jelly.www.dao.PostImageDAO;
import com.jelly.www.dao.PostLikeDAO;
import com.jelly.www.dao.PostSaveDAO;
import com.jelly.www.dao.UserDAO;
import com.jelly.www.vo.PostImageVO;
import com.jelly.www.vo.PostVO;
import com.jelly.www.vo.StylePostInfoVO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class StyleListAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// set attribute of all posts
		String styleCode = request.getParameter("styleCode");
		String currentPage = request.getParameter("pageNum");
		String orderBy = request.getParameter("orderBy");
		
		
		System.out.println("orderBy: " + orderBy);
		if (styleCode == null) {
			// 껍데기를 출력하는 요청이
			return "/views/style/styleList.jsp";
		}
		int code = Integer.parseInt(styleCode);
		int pageNum = Integer.parseInt(currentPage);
		ArrayList<StylePostInfoVO> styleListInfo = new ArrayList<>();
		PostDAO dao = new PostDAO();
		UserDAO userDAO = new UserDAO();
		PostLikeDAO postLikeDao = new PostLikeDAO();
		
		HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        int orderingVal = 0;
        
        // Explicitly check for null, empty, or "0"
        if (orderBy == null || orderBy.trim().isEmpty() || orderBy.equals("0")) {
            System.out.println("not changing val");
        } else if (orderBy.equals("1")) {
            System.out.println("changing orderingVal to 1");
            // Add your logic for sorting by most liked posts
            orderingVal = 1;
        }
        
        
        
		if(code == 99 && user == null) {
			// 리다이렉션 처리
			session.setAttribute("returnPage", "/views/style/styleList.jsp");
            return "/views/login/login.jsp";
		} else {
			ArrayList<PostVO> list = null;
			if (code == 99) {
				list = dao.getFollowersPosts(user.getUserId(), orderingVal);
			} else {
				list = dao.selectByStyleCategory(code, pageNum, orderingVal);
			}
			for(PostVO vo: list) {
				// get first image associated with post id
				boolean isLike = false;
				if(user != null) {
					isLike = postLikeDao.checkLike(vo.getPostId(), user.getUserId());
				}
				
				String postImageUrl = vo.getThumbnailImageUrl();
				if(postImageUrl == null) {
					postImageUrl = "https://static.vecteezy.com/system/resources/previews/004/141/669/non_2x/no-photo-or-blank-image-icon-loading-images-or-missing-image-mark-image-not-available-or-image-coming-soon-sign-simple-nature-silhouette-in-frame-isolated-illustration-vector.jpg";
				}
				
				// get user information
				UserVO userVO = userDAO.selectOne(vo.getUserId());
				String nickname = userVO.getNickname();
				String profileImageUrl = userVO.getProfileImage();
				
				StylePostInfoVO obj = new StylePostInfoVO(vo.getPostId(), vo.getUserId(), nickname, vo.getTitle(), postImageUrl, profileImageUrl, vo.getLikeCount(), isLike);
				styleListInfo.add(obj);
			}
		}
		userDAO.close();
		dao.close();
		postLikeDao.close();
		request.setAttribute("postList", styleListInfo);
		return "/views/style/stylePostList.jsp";
	}
}
