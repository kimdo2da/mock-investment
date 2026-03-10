package project;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/buy")
public class BuyServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String symbol = req.getParameter("symbol");
        String name = req.getParameter("name");
        int qty = Integer.parseInt(req.getParameter("qty"));

        // 쉼표 제거
        long price = Long.parseLong(req.getParameter("price").replace(",", ""));

        MemberDTO user = (MemberDTO) req.getSession().getAttribute("user");
        if (user == null) { 
            resp.sendRedirect("login.jsp"); 
            return; 
        }

        AccountDAO accDao = new AccountDAO();
        PortfolioDAO portDao = new PortfolioDAO();
        TransactionDAO tDao = new TransactionDAO();

        AccountDTO acc = accDao.getAccountByUser(user.getId());
        long total = qty * price;

        // 돈 부족
        if (acc.getAvailable() < total) {
            resp.getWriter().println("<script>alert('보유 금액이 부족합니다!'); history.back();</script>");
            return;
        }

        // available 감소만
        accDao.decreaseAvailable(acc.getAccountId(), total);

        // 포트폴리오 업데이트
        portDao.buy(acc.getAccountId(), symbol, name, qty, price);

        // 거래 기록
        tDao.insertHistory(acc.getAccountId(), symbol, "BUY", qty, price);

        resp.sendRedirect("mypage.do");
    }
}
