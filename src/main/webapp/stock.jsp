<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="project.StockDTO" %>
<%@ page import="project.MemberDTO" %>

<%
    List<StockDTO> stocks = (List<StockDTO>) request.getAttribute("stocks");
    MemberDTO user = (MemberDTO) session.getAttribute("user");

    // 기본 선택 종목 (첫 번째 정상 종목)
    String defaultCode = null;
    String defaultName = null;
    String defaultPrice = null;

    if (stocks != null && !stocks.isEmpty()) {
        for (StockDTO s : stocks) {
            if (s.getName() != null) {
                defaultCode = s.getCode();
                defaultName = s.getName();
                defaultPrice = s.getNow();
                break;
            }
        }
    }
%>

<html>
<head>
    <title>주식 차트</title>

    <!-- 공통 스타일 -->
    <link rel="stylesheet" href="css/style.css">
    <!-- 이 페이지 전용 스타일 -->
    <link rel="stylesheet" href="css/stock_restyle.css">

    <!-- Chart.js -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body>

<!-- 상단 메뉴 -->
<div class="navbar">
    <a href="stockList">주식</a>
    <a href="news.do">뉴스</a>
    <a href="mypage.do">마이페이지</a>

    <% if (user == null) { %>
        <a href="login.jsp">회원가입 / 로그인</a>
    <% } else { %>
        <a href="logout.do">로그아웃</a>
    <% } %>
</div>

<!-- 메인 레이아웃 -->
<div class="main-container">

    <!-- 왼쪽: 차트 영역 -->
    <div class="chart-box" id="chart-box">
    	<div class="option-bar">
    		<button onclick="changeOption('1d', '1m')">1일</button>
    		<button onclick="changeOption('5d', '60m')">1주일</button>
    		<button onclick="changeOption('3mo', '1d')">3개월</button>
		</div>
    	
        <canvas id="stockChart"></canvas>
    </div>

    <!-- 오른쪽: 리스트 + 매수/매도 -->
    <div class="right-panel">

        <!-- 종목 리스트 -->
        <div class="stock-list">
            <h3>종목 리스트</h3>
            <ul>
            <% if (stocks != null) {
                   for (StockDTO s : stocks) {
                       if (s.getName() == null) continue;
            %>
                <!-- ❗ onclick 대신 data-* 로 값 전달 -->
                <li class="stock-item"
                    data-code="<%= s.getCode() %>"
                    data-name="<%= s.getName() %>"
                    data-now="<%= s.getNow() %>">
                    <div class="list-name"><%= s.getName() %></div>
                    <div class="list-price"><%= s.getNow() %>원</div>
                </li>
            <%     }
               } %>
            </ul>
        </div>

        <!-- 리스트 아래 매수/매도 패널 -->
        <div class="trade-panel" id="tradePanel">
            <div class="trade-info">
                <h2 id="selectedStock">
                    <% if (defaultCode != null) { %>
                        <%= defaultName %> (<%= defaultCode %>)
                    <% } else { %>
                        종목을 선택하세요
                    <% } %>
                </h2>
                <p id="selectedPrice">
                    <% if (defaultPrice != null) { %>
                        현재가: <%= defaultPrice %>원
                    <% } %>
                </p>
            </div>

            <% if (user != null) { %>
                <!-- 매수 -->
                <form id="buyForm" method="post" action="buy" class="trade-form">
                    <input type="hidden" name="symbol" id="buySymbol" value="<%= defaultCode != null ? defaultCode : "" %>">
                    <input type="hidden" name="name" id="buyName" value="<%= defaultName != null ? defaultName : "" %>">
                    <input type="hidden" name="price" id="buyPrice" value="<%= defaultPrice != null ? defaultPrice : "" %>">

                    <label>수량</label>
                    <input type="number" name="qty" min="1" value="1">
                    <button type="submit">매수</button>
                </form>

                <!-- 매도 -->
                <form id="sellForm" method="post" action="sell" class="trade-form">
                    <input type="hidden" name="symbol" id="sellSymbol" value="<%= defaultCode != null ? defaultCode : "" %>">
                    <input type="hidden" name="price" id="sellPrice" value="<%= defaultPrice != null ? defaultPrice : "" %>">

                    <label>수량</label>
                    <input type="number" name="qty" min="1" value="1">
                    <button type="submit">매도</button>
                </form>
            <% } else { %>
                <div class="need-login">
                    매수/매도를 하려면 <a href="login.jsp">로그인</a>이 필요합니다.
                </div>
            <% } %>
        </div>

    </div><!-- /right-panel -->

</div><!-- /main-container -->

<!-- ================= JS: 그래프 + 옵션 + 종목 선택 ================= -->
<script>
let chartInstance = null;

// 현재 선택된 종목 / 옵션
let currentSymbol   = "<%= defaultCode != null ? defaultCode : "" %>";
let currentRange    = "3mo";  // 기본: 3개월
let currentInterval = "1d";   // 기본: 일봉

// Yahoo 차트 데이터 불러와서 그리기
function drawChart(symbol) {
    if (!symbol) return;

    const url = "stockChart?symbol=" + symbol
              + "&range=" + encodeURIComponent(currentRange)
              + "&interval=" + encodeURIComponent(currentInterval);

    fetch(url)
        .then(res => res.json())
        .then(json => {
            if (!json || !json.chart || !json.chart.result || json.chart.result.length === 0) {
                console.error("차트 데이터 없음", json);
                return;
            }

            const result = json.chart.result[0];
            const timestamps = result.timestamp;
            const quote = result.indicators.quote[0];
            const closes = quote.close;

            if (!timestamps || !closes) {
                console.error("timestamp/close 데이터 없음");
                return;
            }

            // 라벨: 분봉이면 시간까지, 아니면 날짜만
            const labels = timestamps.map(t => {
                const d = new Date(t * 1000);
                const m = (d.getMonth() + 1).toString().padStart(2, '0');
                const day = d.getDate().toString().padStart(2, '0');
                const hh = d.getHours().toString().padStart(2, '0');
                const mm = d.getMinutes().toString().padStart(2, '0');

                if (currentInterval === "1m" || currentInterval === "60m") {
                    return `${m}/${day} ${hh}:${mm}`;
                } else {
                    return `${m}/${day}`;
                }
            });

            if (chartInstance) {
                chartInstance.destroy();
            }

            const ctx = document.getElementById("stockChart").getContext("2d");

            chartInstance = new Chart(ctx, {
                type: "line",
                data: {
                    labels: labels,
                    datasets: [{
                        label: symbol + " 종가",
                        data: closes,
                        borderWidth: 2,
                        borderColor: "#007bff",
                        pointRadius: 0,
                        fill: false,
                        tension: 0.25
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        x: {
                            ticks: { maxTicksLimit: 8 }
                        }
                    }
                }
            });
        })
        .catch(err => {
            console.error("차트 로딩 에러:", err);
        });
}

// 종목 선택 시 호출 (데이터만 바꿈)
function selectStock(code, name, nowPrice) {
    currentSymbol = code;

    // 1) 차트 갱신
    drawChart(code);

    // 2) 우측 정보 갱신
    document.getElementById("selectedStock").innerText = name + " (" + code + ")";
    document.getElementById("selectedPrice").innerText = "현재가: " + nowPrice + "원";

    // 3) 매수/매도 hidden 값 갱신
    const buySymbol  = document.getElementById("buySymbol");
    const buyName    = document.getElementById("buyName");
    const buyPrice   = document.getElementById("buyPrice");
    const sellSymbol = document.getElementById("sellSymbol");
    const sellPrice  = document.getElementById("sellPrice");

    if (buySymbol)  buySymbol.value  = code;
    if (buyName)    buyName.value    = name;
    if (buyPrice)   buyPrice.value   = nowPrice;
    if (sellSymbol) sellSymbol.value = code;
    if (sellPrice)  sellPrice.value  = nowPrice;
}

// 옵션 버튼 클릭
function changeOption(range, interval) {
    currentRange = range;
    currentInterval = interval;

    if (currentSymbol) {
        drawChart(currentSymbol);
    }
}

// 페이지 로드 시
document.addEventListener("DOMContentLoaded", function() {
    // 1) 기본 종목 차트
    if (currentSymbol) {
        drawChart(currentSymbol);
    }

    // 2) 각 리스트 아이템에 클릭 이벤트 연결
    document.querySelectorAll(".stock-item").forEach(function (li) {
        li.addEventListener("click", function () {
            const code = this.dataset.code;
            const name = this.dataset.name;
            const now  = this.dataset.now;

            selectStock(code, name, now);
        });
    });
});
</script>

</body>
</html>
