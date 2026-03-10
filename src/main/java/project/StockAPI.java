package project;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class StockAPI {

    private static final String APP_KEY = 
    private static final String APP_SECRET = 
    private static final String PRICE_URL =
            


    public static StockDTO getStockInfo(String token, String code) {

        try {
            HttpClient client = HttpClient.newHttpClient();

            String query = "?FID_COND_MRKT_DIV_CODE=J&FID_INPUT_ISCD=" +
                    URLEncoder.encode(code, "UTF-8");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(PRICE_URL + query))
                    .header("authorization", "Bearer " + token)
                    .header("appkey", APP_KEY)
                    .header("appsecret", APP_SECRET)
                    .header("tr_id", "FHKST01010100")
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject json = new JSONObject(response.body());

            if (!json.getString("rt_cd").equals("0")) return null;

            JSONObject o = json.getJSONObject("output");

            return new StockDTO(
                    o.getString("stck_shrn_iscd"),
                    StockName.getName(code),   //  여기서 이름 넣기
                    o.getString("stck_prpr"),
                    o.getString("stck_oprc"),
                    o.getString("stck_hgpr"),
                    o.getString("stck_lwpr"),
                    o.getString("prdy_ctrt")
            ); //종목명을 자동으로 받아오는 기능이 api내부에 없음.
            //시장명 또는 하나하나 다 매핑해야함

        } catch (Exception e) {
            System.out.println("StockAPI 오류: " + e.getMessage());
            return null;
        }
    }
}
