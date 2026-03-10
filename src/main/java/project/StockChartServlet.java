package project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@WebServlet("/stockChart")
public class StockChartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String code = req.getParameter("symbol"); // 예: 005930
        if (code == null || code.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "symbol required");
            return;
        }

        //  추가된 옵션 (없으면 기본값 사용)
        String range = req.getParameter("range");
        String interval = req.getParameter("interval");

        if (range == null || range.isBlank())   range = "3mo"; // 기본 3개월
        if (interval == null || interval.isBlank()) interval = "1d";  // 기본 일봉

        String yahooSymbol = toYahooSymbol(code); // 예: 005930.KS

        String url = "https://query1.finance.yahoo.com/v8/finance/chart/"
                + yahooSymbol
                + "?range=" + range
                + "&interval=" + interval;

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "Mozilla/5.0")
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().write(response.body());

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Chart API error");
        }
    }

    private String toYahooSymbol(String code) {
        // TODO: 코스닥 종목 있으면 KQ로 예외 처리
        return code + ".KS";
    }
}
