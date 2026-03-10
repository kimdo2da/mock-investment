<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="project.MemberDTO" %>
<%@ page import="project.AccountDTO" %>

<%
    MemberDTO user = (MemberDTO) session.getAttribute("user");
    AccountDTO account = (AccountDTO) request.getAttribute("account");
%>

<html>
<head>
    <title>로그인</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/login.css">
</head>

<body>

<!-- 뉴스페이지와 동일한 배경을 유지하려면 news-wrapper가 반드시 필요함 -->
<div class="news-wrapper">

    <!-- 햄버거 -->
    <div class="news-hamburger-btn">☰</div>

    <!-- 상단 로고 -->
    <div class="news-top-logo">ACHIEVEMENT</div>

</div>

<!-- 🔥 로그인 박스는 뉴스 레이아웃 위에 '독립적으로' 고정 -->
<div class="login-center-wrapper">

    <div class="login-box">
        

        <h2>로그인</h2>

        <form action="login.do" method="post">

            <div class="login-input-group">
                <label>ID</label>
                <input type="text" name="userid" placeholder="아이디를 입력하세요" required>
            </div>

            <div class="login-input-group">
                <label>Password</label>
                <input type="password" name="password" placeholder="비밀번호를 입력하세요" required>
            </div>

            <div class="login-extra">
                <a href="signup.jsp">회원가입</a>
            </div>

            <button type="submit" class="login-btn">LOGIN</button>

        </form>
    </div>

</div>

<!-- 사이드바 -->
<div class="news-sidebar">
    <div class="news-sidebar-header">
        <span class="news-sidebar-logo">KR</span>
        <span class="news-sidebar-close">×</span>
    </div>

    <div class="news-sidebar-menu">
        <a href="stockList" class="news-menu-item">시장</a>
        <a href="mypage.do" class="news-menu-item">내 포트폴리오</a>
        <a href="news.do" class="news-menu-item">뉴스</a>

        <% if (user == null) { %>
            <a href="login.jsp" class="news-menu-item active">로그인 / 회원가입</a>
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

<script>
document.querySelector('.news-hamburger-btn').onclick = () =>
    document.querySelector('.news-sidebar').classList.add('open');

document.querySelector('.news-sidebar-close').onclick = () =>
    document.querySelector('.news-sidebar').classList.remove('open');
</script>

</body>
</html>
