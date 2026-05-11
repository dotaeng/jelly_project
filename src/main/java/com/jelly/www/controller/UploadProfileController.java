package com.jelly.www.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import com.jelly.www.dao.MyProfileInfoDAO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/uploadProfile")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 5,       // 5MB
    maxRequestSize = 1024 * 1024 * 10    // 10MB
)
public class UploadProfileController extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "profileImages";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 세션에서 사용자 정보 가져오기
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/views/login/login.jsp");
            return;
        }

        // 업로드 디렉토리 설정
        String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String fileURL = null;

        try {
            for (Part part : request.getParts()) {
                String fileName = part.getSubmittedFileName();
                if (fileName != null && !fileName.isEmpty()) {
                    // 고유한 파일 이름 생성
                    String uniqueFileName = UUID.randomUUID().toString() + "_" + cleanFileName(fileName);

                    // 파일 저장 경로
                    String filePath = uploadPath + File.separator + uniqueFileName;

                    // 파일 URL 생성
                    String baseUrl = "http://localhost:8080";
                    fileURL = baseUrl + request.getContextPath() + "/" + UPLOAD_DIRECTORY + "/" + uniqueFileName;

                    // 파일 저장
                    part.write(filePath);
                    System.out.println("File uploaded to: " + filePath);
                }
            }

            // DB에 저장
            if (fileURL != null) {
                MyProfileInfoDAO dao = new MyProfileInfoDAO();
                boolean isUpdated = dao.updateProfileImage(user.getUserId(), fileURL);
                if (isUpdated) {
                    response.getWriter().println("프로필 이미지가 성공적으로 변경");
                } else {
                    response.getWriter().println("프로필 이미지 변경에 실패");
                }
            } else {
                response.getWriter().println("업로드된 파일이 없음");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("프로필 이미지 업로드 중 오류 발생");
        }

        // 프로필 관리 페이지로 리다이렉트
        response.sendRedirect(request.getContextPath() + "/jelly?page=viewProfileInfo");
    }

    private String cleanFileName(String fileName) {
        return new File(fileName).getName().replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}