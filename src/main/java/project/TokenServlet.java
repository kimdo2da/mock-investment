package project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import org.json.JSONObject;

@WebServlet("/getToken")
public class TokenServlet extends HttpServlet {

    private static final String APP_KEY = "PSlfLDqjGgbXdqXq6VSOAVq9bOsvWv0aUDvb";
    private static final String APP_SECRET = "QqcWNsDq27m6N7YtbGRDAKdWpWhNOOuAxnm/rcMCkDR4e+g+Xvh5XsDE1UpNCryg9wsQutSKxTer4Uc4IblRQpsoLalT3+2D2e2AnQRnKUWx8RA24s3J1r9C4Jp8/plOnsn7UnMn1GpFBIpeAABJ7JvpLvrDkXtDmReu6UeK9X6Kuma1Bk0=";

    private static final String TOKEN_URL =
            "https://openapi.koreainvestment.com:9443/oauth2/tokenP";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            HttpClient client = HttpClient.newHttpClient();

            JSONObject body = new JSONObject();
            body.put("grant_type", "client_credentials");
            body.put("appkey", APP_KEY);
            body.put("appsecret", APP_SECRET);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(TOKEN_URL))
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject json = new JSONObject(response.body());
            String token = json.getString("access_token");

            // 세션에 토큰 저장
            req.getSession().setAttribute("accessToken", token);

            // 토큰 발급 후 자동으로 stock.jsp로 이동
            resp.sendRedirect("stock.jsp");

        } catch (Exception e) {
            resp.setContentType("text/plain;charset=UTF-8");
            resp.getWriter().write("예외 발생: " + e.getMessage());
        }
    }
}
