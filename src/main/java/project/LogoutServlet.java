package project;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/logout.do")
public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // 세션 제거
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();

        // 로그아웃 후 뉴스(index.jsp)로 이동
        resp.sendRedirect("news.do");
    }
}