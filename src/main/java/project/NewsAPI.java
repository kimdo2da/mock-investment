package project;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class NewsAPI {

    
    private static final String API_KEY = 

    public static ArrayList<NewsDTO> getNews() {

        ArrayList<NewsDTO> list = new ArrayList<>();
        HttpURLConnection conn = null;
        BufferedReader br = null;

        try {
            // 주식 뉴스로 변경
            String keyword = URLEncoder.encode("주식", StandardCharsets.UTF_8.toString());

            //  기사 100개 요청
            String urlStr =
                    "https://api-v2.deepsearch.com/v1/articles"
                    + "?keyword=" + keyword
                    + "&date_from=2024-01-01"
                    + "&date_to=2025-01-01"
                    + "&page_size=100"
                    + "&page=1"
                    + "&api_key=" + API_KEY;

            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            int status = conn.getResponseCode();

            InputStream is = (status >= 200 && status < 300)
                    ? conn.getInputStream()
                    : conn.getErrorStream();

            if (is == null) return list;

            br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            StringBuilder result = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            if (status != 200) return list;

            // JSON 파싱
            JsonObject root = JsonParser.parseString(result.toString()).getAsJsonObject();
            JsonArray data = root.getAsJsonArray("data");
            if (data == null) return list;

            for (int i = 0; i < data.size(); i++) {
                JsonObject obj = data.get(i).getAsJsonObject();

                NewsDTO dto = new NewsDTO();
                dto.setTitle(getAsString(obj, "title"));
                dto.setSummary(getAsString(obj, "summary"));
                dto.setUrl(getAsString(obj, "content_url"));

                String thumb = getAsString(obj, "thumbnail_url");
                String img = getAsString(obj, "image_url");
                dto.setImage(!thumb.isEmpty() ? thumb : img);

                dto.setDate(getAsString(obj, "published_at"));

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (br != null) br.close(); } catch (IOException ignore) {}
            if (conn != null) conn.disconnect();
        }

        return list;
    }

    private static String getAsString(JsonObject obj, String key) {
        if (obj.has(key) && !obj.get(key).isJsonNull()) {
            return obj.get(key).getAsString();
        }
        return "";
    }
}
