<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="project.MemberDTO" %>
<%@ page import="project.AccountDTO" %>

<%
    MemberDTO user = (MemberDTO) session.getAttribute("user");
    AccountDTO account = (AccountDTO) request.getAttribute("account");
%>

<html>
<head>
    <title>회원가입</title>
    <link rel="stylesheet" href="css/style.css">   <!-- 뉴스/로그인 공통 CSS -->
    <link rel="stylesheet" href="css/signup.css">  <!-- 회원가입 전용 CSS -->
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

<!-- 햄버거 버튼 -->
<div class="news-hamburger-btn">☰</div>

<!-- 상단 로고 -->
<div class="news-top-logo" onclick="location.href='news.do'" style="cursor:pointer;">
    ACHIEVEMENT
</div>

<!--  중앙 회원가입 박스 -->
<div class="signup-container">

    <div class="signup-box">
        <h2>회원 가입</h2>

        <form action="signup.do" method="post">

            <div class="signup-input">
                <input type="text" name="userid" placeholder="아이디 입력" required>
            </div>

            <div class="signup-input">
                <input type="password" name="password" placeholder="비밀번호 입력" required>
            </div>

            <div class="signup-input">
                <input type="password" name="password2" placeholder="비밀번호 확인" required>
            </div>

            <div class="signup-input">
                <input type="text" name="account_name" placeholder="계좌 별명" required>
            </div>

            <button type="submit" class="submit-btn">동의하고 가입하기</button>
        </form>
    </div>

</div>

<script>
document.querySelector('.news-hamburger-btn').onclick = function(){
    document.querySelector('.news-sidebar').classList.add('open');
};
document.querySelector('.news-sidebar-close').onclick = function(){
    document.querySelector('.news-sidebar').classList.remove('open');
};
</script>

</body>
</html>
