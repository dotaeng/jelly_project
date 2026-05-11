package com.jelly.www.action;

import java.util.ArrayList;
import java.util.List;

import com.jelly.www.dao.PostDAO;
import com.jelly.www.dao.PostLikeDAO;
import com.jelly.www.dao.ProductDAO;
import com.jelly.www.dao.UserDAO;
import com.jelly.www.vo.PostVO;
import com.jelly.www.vo.ProductVO;
import com.jelly.www.vo.StylePostInfoVO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class HomeAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // currentPage 설정
        request.setAttribute("currentPage", "home");
        
        // 좋아요 상위 4개 포스트 가져오기
        PostDAO postDao = new PostDAO();
        ArrayList<PostVO> postVoList = postDao.getTop4ByLikes();
        
        ArrayList<StylePostInfoVO> postList = new ArrayList<StylePostInfoVO>();
        for(PostVO postVo : postVoList) {
        	UserDAO userDao = new UserDAO();
        	UserVO userVo = userDao.selectOne(postVo.getUserId());
        	StylePostInfoVO stylePostInfoVo = new StylePostInfoVO(
        			postVo.getPostId(),
        			userVo.getUserId(),
        			userVo.getNickname(),
        			postVo.getTitle(),
        			postVo.getThumbnailImageUrl(),
        			userVo.getProfileImage(),	
        			postVo.getLikeCount(),
        			false	
        	);
        	HttpSession session = request.getSession();
	        UserVO user = (UserVO) session.getAttribute("user");
			if(user != null) {
				PostLikeDAO postLikeDao = new PostLikeDAO();
				stylePostInfoVo.setLike(postLikeDao.checkLike(postVo.getPostId(), user.getUserId()));
			}
        	postList.add(stylePostInfoVo);
        }
        
        request.setAttribute("postList", postList);
        

        // 인기 상품 데이터 조회
        ProductDAO productDAO = new ProductDAO();
        List<ProductVO> popularProducts = productDAO.getPopularProducts(); // 인기 상품 데이터 조회
        request.setAttribute("popularProducts", popularProducts); // 인기 상품 데이터 저장


        return "/views/home/home.jsp"; // 홈 페이지로 이동
    }
}