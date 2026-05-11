package com.jelly.www.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.jelly.www.dao.CommentDAO;
import com.jelly.www.dao.PostDAO;
import com.jelly.www.dao.UserDAO;
import com.jelly.www.vo.CommentVO;
import com.jelly.www.vo.PostVO;
import com.jelly.www.vo.StyleCommentVO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/comment")
public class CommentController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JSONObject jsonResponse = new JSONObject();
        String url = "";

        PostDAO postDAO = new PostDAO();
        CommentDAO commentDAO = new CommentDAO();
        UserDAO userDAO = new UserDAO();

        try {
            HttpSession session = req.getSession();
            UserVO user = (UserVO) session.getAttribute("user");

            if (user == null) { // 로그인이 되어 있지 않을 경우
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // HTTP 401 상태
                return;
            }

            // 파라미터 가져오기
            int userId = user.getUserId();
            String postIdParam = req.getParameter("postId");
            String deleteCommentId = req.getParameter("deleteCommentId");
            String comment = req.getParameter("comment");

            // postId 파싱 및 검증
            int postId = 0;
            try {
                postId = Integer.parseInt(postIdParam);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 400 상태
                return;
            }


            // 댓글 삭제 처리
            if (deleteCommentId != null) {
                try {
                    int commentId = Integer.parseInt(deleteCommentId);
                    commentDAO.deleteOne(commentId);
                    postDAO.minusComment(postId);
                } catch (NumberFormatException e) {

                }
            }

            // 댓글 추가
            if (comment != null && !comment.trim().isEmpty() && deleteCommentId == null) {
                commentDAO.insertOne(new CommentVO(postId, userId, comment.trim()));
                postDAO.plusComment(postId);
            }

            // 댓글 목록 생성
            ArrayList<CommentVO> commentList = commentDAO.getCommentOfPost(postId);
            ArrayList<StyleCommentVO> styleCommentInfo = new ArrayList<>();
            PostVO postVO = postDAO.selectOne(postId);

            for (CommentVO vo : commentList) {
                UserVO userVO = userDAO.selectOne(vo.getUserId());
                StyleCommentVO styleComment = new StyleCommentVO(
                        vo.getCommentId(),
                        vo.getPostId(),
                        vo.getUserId(),
                        vo.getContent(),
                        vo.getLikesCount(),
                        vo.getCreatedAt(),
                        vo.getUpdatedAt(),
                        userVO.getProfileImage(),
                        userVO.getNickname(),
                        postVO.getCommentCount()
                );
                styleCommentInfo.add(styleComment);
            }

            req.setAttribute("commentList", styleCommentInfo);
            req.setAttribute("user", user);

            url = "/views/style/singleComment.jsp";

            if (url.startsWith("redirect:")) {
                resp.sendRedirect(url.substring("redirect:".length()));
            } else if (url != null && !resp.isCommitted()) {
                RequestDispatcher rd = req.getRequestDispatcher(url);
                rd.forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.put("error", "댓글 처리중 오류 발생");
            resp.setContentType("application/json");
            resp.getWriter().write(jsonResponse.toJSONString());
        } finally {
            try {
                postDAO.close();
                commentDAO.close();
                userDAO.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}