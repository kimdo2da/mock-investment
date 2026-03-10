package project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/stockList")
public class StockListServlet extends HttpServlet {

	private static final String[] STOCK_CODES = {

		    // 🔹 대표 기술/반도체
		    "005930", // 삼성전자
		    "000660", // SK하이닉스
		    "006400", // 삼성SDI
		    "035420", // NAVER
		    "035720", // 카카오

		    // 🔹 자동차
		    "005380", // 현대차
		    "000270", // 기아
		    "012330", // 현대모비스

		    // 🔹 배터리/화학
		    "051910", // LG화학
		    "373220", // LG에너지솔루션
		    "096770", // SK이노베이션

		    // 🔹 금융/은행
		    "105560", // KB금융
		    "055550", // 신한지주
		    "003550", // LG
		    "024110", // 기업은행
		    "000810", // 삼성화재
		    "086790", // 하나금융지주

		    // 🔹 운송/항공
		    "003490", // 대한항공

		    // 🔹 철강/소재
		    "003670", // 포스코홀딩스
		    "047050", // POSCO인터내셔널

		    // 🔹 바이오
		    "068270", // 셀트리온
		    "207940", // 삼성바이오로직스

		    // 🔹 에너지/인프라
		    "011170", // 롯데케미칼
		    "010950", // S-Oil

		    // 🔹 유통/소비재
		    "028260", // 삼성물산
		    "090430", // 아모레퍼시픽

		    // 🔹 게임/엔터
		    "036570", // 엔씨소프트
		    "251270", // 넷마블

		    // 🔹 IT/통신
		    "030200", // KT
		    "017670"  // SK텔레콤
		};

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String accessToken = (String) req.getSession().getAttribute("accessToken");
        if (accessToken == null) {
            resp.sendRedirect("getToken");
            return;
            
        }

        List<StockDTO> list = new ArrayList<>();

        for (String code : STOCK_CODES) {
            StockDTO dto = StockAPI.getStockInfo(accessToken, code);
            if (dto != null) list.add(dto);
        }

        req.setAttribute("stocks", list);
        req.getRequestDispatcher("stock.jsp").forward(req, resp);
        
    }
    
}