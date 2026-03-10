<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="project.MemberDTO" %>
<%@ page import="project.AccountDTO" %>
<%@ page import="project.PortfolioDTO" %>
<%@ page import="project.TransactionDTO" %>
<%@ page import="java.util.List" %>

<%
    MemberDTO user = (MemberDTO) session.getAttribute("user");
    AccountDTO account = (AccountDTO) request.getAttribute("account");
    List<PortfolioDTO> portfolioList = (List<PortfolioDTO>) request.getAttribute("portfolio");
    List<TransactionDTO> history = (List<TransactionDTO>) request.getAttribute("history");

    long investingNow = 0;
    if (request.getAttribute("investingNow") != null) {
        investingNow = (long) request.getAttribute("investingNow");
    }
%>

<html>
<head>
    <title>마이페이지</title>
    <link rel="stylesheet" href="css/style.css">  <!-- 공통 UI -->
    <link rel="stylesheet" href="css/mypage.css"> <!-- 마이페이지 전용 -->
</head>

<body>

<!--  사이드바 -->
<div class="news-sidebar">
    <div class="news-sidebar-header">
        <span class="news-sidebar-logo">KR</span>
        <span class="news-sidebar-close">×</span>
    </div>

    <div class="news-sidebar-menu">
        <a href="stockList" class="news-menu-item">시장</a>
        <a href="mypage.do" class="news-menu-item active">내 포트폴리오</a>
        <a href="news.do" class="news-menu-item">뉴스</a>

        <% if (user == null) { %>
            <a href="login.jsp" class="news-menu-item">로그인 / 회원가입</a>
        <% } else { %>
            <a href="logout.do" class="news-menu-item">로그아웃</a>
        <% } %>
    </div>

    <% if (user == null) { %>
        <div class="news-sidebar-bottom">
            <p class="news-asset-title">로그인이 필요합니다</p>
            <a href="login.jsp" class="login-link">로그인 하기 →</a>
        </div>
    <% } else if (account != null) { %>
        <div class="news-sidebar-bottom">
            <p class="news-asset-title">총 자산</p>
            <p class="news-asset-value"><%= account.getAvailable() %>원</p>
        </div>
    <% } %>
</div>

<!-- 햄버거 버튼 -->
<div class="news-hamburger-btn">☰</div>

<!-- 상단 로고 -->
<div class="news-top-logo" onclick="location.href='news.do'" style="cursor:pointer;">
    ACHIEVEMENT
</div>

<!--  메인 컨테이너 -->
<div class="mypage-wrapper">

    <!--  자산 요약 카드 -->
    <div class="mypage-card">
        <h2>자산 요약</h2>

        <div class="asset-grid">

            <div class="asset-box">
                <p>주문 가능 금액</p>
                <h3><%= account.getAvailable() %>원</h3>
            </div>

            <div class="asset-box">
                <p>초기 투자 금액</p>
                <h3><%= investingNow %>원</h3>
            </div>

            <div class="asset-box">
                <p>판매 수익</p>
                <h3 class="profit"><%= account.getProfit() %>원</h3>
            </div>

        </div>
    </div>

    <!--  투자 중인 종목 -->
    <div class="mypage-card">
        <h2>투자 중인 종목</h2>

        <% if (portfolioList == null || portfolioList.size() == 0) { %>

            <p class="empty-text">투자 중인 종목이 없습니다.</p>

        <% } else { %>

        <table class="mp-table">
            <tr>
                <th>종목명</th>
                <th>수량</th>
                <th>1주당 구매가격</th>
            </tr>

            <% for (PortfolioDTO p : portfolioList) { %>
            <tr>
                <td><%= p.getName() %> (<%= p.getSymbol() %>)</td>
                <td><%= p.getQuantity() %>주</td>
                <td><%= String.format("%,d원", (long)p.getAveragePrice()) %></td>
            </tr>
            <% } %>

        </table>

        <% } %>
    </div>

    <!-- 거래내역 -->
    <div class="mypage-card">
        <h2>거래내역</h2>

        <% if (history == null || history.size() == 0) { %>

            <p class="empty-text">거래내역이 없습니다.</p>

        <% } else { %>

        <table class="mp-table">
            <tr>
                <th>종목명</th>
                <th>타입</th>
                <th>수량</th>
                <th>거래금액</th>
                <th>거래일</th>
            </tr>

            <% for (TransactionDTO t : history) { %>
            <tr>
                <td><%= t.getSymbol() %></td>

                <td class="<%= t.getType().equals("BUY") ? "buy" : "sell" %>">
                    <%= t.getType().equals("BUY") ? "매수" : "매도" %>
                </td>

                <td><%= t.getQuantity() %>주</td>

                <td><%= String.format("%,d원", (long)(t.getPrice() * t.getQuantity())) %></td>

                <td><%= t.getCreatedAt() %></td>
            </tr>
            <% } %>

        </table>

        <% } %>
    </div>

</div>

<script>
document.querySelector('.news-hamburger-btn').onclick = () =>
    document.querySelector('.news-sidebar').classList.add('open');
document.querySelector('.news-sidebar-close').onclick = () =>
    document.querySelector('.news-sidebar').classList.remove('open');
</script>

</body>
</html>
