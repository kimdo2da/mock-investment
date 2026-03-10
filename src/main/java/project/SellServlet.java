package project;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/sell")
public class SellServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String symbol = req.getParameter("symbol");
        int qty = Integer.parseInt(req.getParameter("qty"));

        long price = Long.parseLong(req.getParameter("price").replace(",", ""));

        MemberDTO user = (MemberDTO) req.getSession().getAttribute("user");
        if (user == null) {
            resp.getWriter().println("<script>alert('로그인이 필요합니다.'); location.href='login.jsp';</script>");
            return;
        }

        AccountDAO aDao = new AccountDAO();
        PortfolioDAO pDao = new PortfolioDAO();
        TransactionDAO tDao = new TransactionDAO();

        AccountDTO acc = aDao.getAccountByUser(user.getId());
        int accountId = acc.getAccountId();

        int have = pDao.getQuantity(accountId, symbol);

        if (have < qty) {
            resp.getWriter().println("<script>alert('보유 수량이 부족합니다!'); history.back();</script>");
            return;
        }

        long total = qty * price;

        // 매도 전 평균단가 미리 조회 (삭제되기 전에)
        double avg = pDao.getAveragePrice(accountId, symbol);

        //  포트폴리오 감소
        pDao.sell(accountId, symbol, qty, price);

        //  주문 가능 금액 증가
        aDao.increaseAvailable(accountId, total);

        //  판매 수익 계산
        double profit = (price - avg) * qty;

        //  수익 반영
        aDao.addProfit(user.getId(), profit);

        //  거래 기록
        tDao.insertHistory(accountId, symbol, "SELL", qty, price);

        resp.getWriter().println("<script>alert('매도 완료!'); location.href='mypage.do';</script>");
    }
}
