package project;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/mypage.do")
public class MyPageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        MemberDTO user = (MemberDTO) req.getSession().getAttribute("user");

        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        AccountDAO accountDAO = new AccountDAO();
        PortfolioDAO portfolioDAO = new PortfolioDAO();
        TransactionDAO transactionDAO = new TransactionDAO();

        // 계좌 조회
        AccountDTO account = accountDAO.getAccountByUser(user.getId());
        if (account == null) {
            resp.getWriter().println("계좌 정보가 없습니다.");
            return;
        }

        //  포트폴리오/거래내역 조회
        List<PortfolioDTO> portfolio = portfolioDAO.getPortfolioByAccount(account.getAccountId());
        List<TransactionDTO> history = transactionDAO.getHistoryByAccount(account.getAccountId());

        // ==========================================================
        //  투자 중 금액 (실시간 계산): 수량 × 현재가
        // ==========================================================
        long investingNow = 0;

        if (portfolio != null) {
            for (PortfolioDTO p : portfolio) {
                investingNow += (long) p.getQuantity() * p.getCurrentPrice();
            }
        }

        // =====================================================================
        // JSP로 전달할 데이터
        // =====================================================================
        req.setAttribute("account", account);
        req.setAttribute("portfolio", portfolio);
        req.setAttribute("history", history);

        // 새로 계산한 투자금 전달
        req.setAttribute("investingNow", investingNow);

        // JSP로 이동
        req.getRequestDispatcher("mypage.jsp").forward(req, resp);
    }
}
