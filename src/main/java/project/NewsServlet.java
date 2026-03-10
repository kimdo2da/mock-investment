package project;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/news.do")
public class NewsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1) 로그인한 사용자 정보 가져오기
        MemberDTO user = (MemberDTO) request.getSession().getAttribute("user");

        if (user != null) {
            // 2) 계좌 정보 조회
            AccountDAO accountDAO = new AccountDAO();
            AccountDTO account = accountDAO.getAccountByUser(user.getId());

            // 3) JSP에 전달
            request.setAttribute("account", account);
        }

        // 4) 뉴스 리스트 불러오기
        ArrayList<NewsDTO> newsList = NewsAPI.getNews();
        request.setAttribute("news", newsList);

        // 5) mainIndex 처리
        String indexStr = request.getParameter("index");
        int index = 0;
        if (indexStr != null) {
            try { index = Integer.parseInt(indexStr); } 
            catch (Exception ignore) {}
        }
        request.setAttribute("mainIndex", index);

        // 6) index.jsp로 이동
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }
}
