package project;   //  꼭 있어야 함

public class StockDTO {
    private String code;   // 종목코드
    private String name;   // 종목명
    private String now;    // 현재가
    private String open;   // 시가
    private String high;   // 고가
    private String low;    // 저가
    private String rate;   // 등락률

    public StockDTO(String code, String name,
                    String now, String open, String high, String low, String rate) {
        this.code = code;
        this.name = name;
        this.now = now;
        this.open = open;
        this.high = high;
        this.low = low;
        this.rate = rate;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public String getNow() { return now; }
    public String getOpen() { return open; }
    public String getHigh() { return high; }
    public String getLow() { return low; }
    public String getRate() { return rate; }
}