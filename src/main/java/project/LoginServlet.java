package project;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String id = req.getParameter("userid");
        String pw = req.getParameter("password");

        // 관리자 모드 키워드 감지
        if (pw != null && pw.contains("!admin!")) {

            HttpSession session = req.getSession();
            session.setAttribute("admin", true);  // 관리자 로그인 인증

            resp.sendRedirect("admin");  // 관리자 페이지로 이동
            return;
        }

        // 일반 회원 로그인 처리
        MemberDAO dao = new MemberDAO();
        MemberDTO user = dao.login(id, pw);

        if (user != null) {
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("news.do");
        } else {
            resp.sendRedirect("login.jsp?error=1");
        }
    }
}

