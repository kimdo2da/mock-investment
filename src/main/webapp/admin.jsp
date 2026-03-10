<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="project.AccountDTO" %>

<html>
<head>
<link rel="stylesheet" href="css/admin.css">
    <title>관리자 페이지</title>

    
</head>

<body>

<h1>회원관리</h1>

<div class="content-wrapper">

    <!-- 리스트 테이블 -->
    <div class="table-wrapper">
        <table>
            <thead>
                <tr>
                    <th>회원 ID</th>
                    <th>계좌 별명</th>
                    <th>비밀번호</th>
                    <th>수익금</th>
                </tr>
            </thead>
        </table>

        <div class="table-body">
            <table>
                <tbody>
                <%
                    List<AccountDTO> accounts = (List<AccountDTO>) request.getAttribute("accounts");
                    int realCount = 0;

                    if (accounts != null) {
                        for (AccountDTO a : accounts) {
                            realCount++;
                %>
                    <tr>
                        <td><%= a.getUserId() %></td>
                        <td><%= a.getAccountName() %></td>
                        <td><%= a.getPassword() %></td>
                        <td><%= a.getProfit() %>원</td>
                    </tr>
                <% 
                        }
                    }

                    // 화면 채우기 위해 최소 줄 확보
                    for (int i = realCount; i < 12; i++) {
                %>
                    <tr class="empty-row">
                        <td>빈칸</td>
                        <td>빈칸</td>
                        <td>빈칸</td>
                        <td>빈칸</td>
                    </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>

    <!-- 관리자 로그아웃 버튼 -->
    <div class="logout-wrapper">
        <button class="logout-btn" onclick="location.href='news.do'">관리자 로그아웃</button>
    </div>

</div>

</body>
</html>
