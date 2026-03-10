<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="project.NewsDTO" %>
<%@ page import="project.MemberDTO" %>
<%@ page import="project.AccountDTO" %>

<%

	if (request.getAttribute("news") == null) {
    response.sendRedirect("news.do");
    return;
	}

    List<NewsDTO> articles = (List<NewsDTO>) request.getAttribute("news");
    if (articles == null) articles = project.NewsAPI.getNews();

    int mainIndex = (request.getAttribute("mainIndex") == null)
            ? 0 : (Integer) request.getAttribute("mainIndex");

    NewsDTO main = articles.get(mainIndex);

    MemberDTO user = (MemberDTO) session.getAttribute("user");
    AccountDTO account = (AccountDTO) request.getAttribute("account");
%>

<html>
<head>
    <title>뉴스</title>
    <!-- news_v3.css → style.css 로 변경 -->
    <link rel="stylesheet" href="css/style.css">
</head>

<body>
<div class="news-wrapper">
<!-- 뉴스 전용 사이드바 -->
<div class="news-sidebar">
    <div class="news-sidebar-header">
        <span class="news-sidebar-logo">KR</span>
        <span class="news-sidebar-close">×</span>
    </div>

    <div class="news-sidebar-menu">

        <a href="stockList" class="news-menu-item">
            <span class="news-icon"></span> 시장
        </a>

        <a href="mypage.do" class="news-menu-item">
            <span class="news-icon"></span> 내 포트폴리오
        </a>

        <a href="news.do" class="news-menu-item active">
            <span class="news-icon"></span> 뉴스
        </a>

        <% if (user == null) { %>
            <a href="login.jsp" class="news-menu-item">
                <span class="news-icon"></span> 로그인 / 회원가입
            </a>
        <% } else { %>
            <a href="logout.do" class="news-menu-item">
                <span class="news-icon"></span> 로그아웃
            </a>
        <% } %>

    </div>

    <% 
    if (user == null) { 
%>

    <!-- 🔹 로그인 안 한 상태 -->
    <div class="news-sidebar-bottom">
        <p class="news-asset-title">로그인이 필요합니다</p>
        <a href="login.jsp" class="login-link">로그인 하기 →</a>
    </div>

<%
    } else if (account != null) {
%>

    <!-- 🔹 로그인 + account 조회 성공 -->
    <div class="news-sidebar-bottom">
        <p class="news-asset-title">총 자산</p>
        <p class="news-asset-value"><%= account.getAvailable() %>원</p>
    </div>

<%
    }
%>

</div>

<!-- 햄버거 버튼 -->
<div class="news-hamburger-btn">☰</div>

<!-- 상단 로고 -->

<div class="news-top-logo" onclick="location.href='news.do'" style="cursor:pointer;">
ACHIEVEMENT</div>


<!-- 메인 뉴스 -->
<div class="news-container">

    <div class="news-main-card">
        <img class="news-main-img" src="<%= main.getImage() %>">

        <div class="news-main-content">
            <h1 class="news-main-title"><%= main.getTitle() %></h1>
            <p class="news-main-summary"><%= main.getSummary() %></p>
            <a class="news-view-btn" href="<%= main.getUrl() %>" target="_blank">
                기사 전체 보기 →
            </a>
        </div>
    </div>

    <!-- 주요 뉴스 -->
<!-- 주요 뉴스 -->
<div class="news-side-area">
    <h3>주요 뉴스</h3>

    <div class="news-side-list">

        <%
            // 주요 뉴스 랜덤 생성을 위한 articles 복사본
            List<NewsDTO> randomSide = new java.util.ArrayList<>(articles);
            java.util.Collections.shuffle(randomSide);

            int sideLimit = 7; // 7개만 보여주기
            int count = 0;

            for (NewsDTO n : randomSide) {

                // 메인 뉴스와 동일 제목이면 제외
                if (n.getTitle().equals(main.getTitle())) continue;

                // 이미지 없는 기사 제외
                if (n.getImage() == null || n.getImage().trim().equals("")) continue;

                // 5개 채우면 종료
                if (count >= sideLimit) break;
        %>

        <div class="news-side-item">
            <img class="news-side-img" src="<%= n.getImage() %>">
            <a class="news-side-title" 
               href="news.do?index=<%= articles.indexOf(n) %>">
                <%= n.getTitle() %>
            </a>
        </div>

        <%
                count++;
            }
        %>

    </div>
</div>

</div>


<!-- 추천 뉴스 (랜덤) -->
<div class="news-recommend-section">
    <h2>추천 뉴스</h2>

    <%
        java.util.Collections.shuffle(articles);
        int reclimit = Math.min(6, articles.size());
    %>

    <div class="news-recommend-grid">
        <% for(int i=0; i<reclimit; i++){
            NewsDTO rec = articles.get(i);
            if (rec.getImage() == null || rec.getImage().trim().equals("")) continue;
        %>

        <div class="news-rec-card">
            <img src="<%= rec.getImage() %>" class="news-rec-img">

            <div class="news-rec-content">
                <h3><%= rec.getTitle() %></h3>
                <p><%= rec.getSummary() %></p>
                <a href="<%= rec.getUrl() %>" target="_blank">기사 보기 →</a>
            </div>
        </div>

        <% } %>
    </div>
</div>

<script>
// 사이드바 열기
document.querySelector('.news-hamburger-btn').onclick = function(){
    document.querySelector('.news-sidebar').classList.add('open');
};

// 사이드바 닫기
document.querySelector('.news-sidebar-close').onclick = function(){
    document.querySelector('.news-sidebar').classList.remove('open');
};
</script>
</div>
</body>
</html>
