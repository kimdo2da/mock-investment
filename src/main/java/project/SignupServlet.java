package project;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/signup.do")
public class SignupServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        //  signup.jsp의 name 값과 정확히 맞추기!
        String id = req.getParameter("userid");  
        String password = req.getParameter("password"); 
        String nickname = req.getParameter("account_name");

        // 회원 정보 담기
        MemberDTO dto = new MemberDTO();
        dto.setId(id);
        dto.setPassword(password);
        dto.setNickname(nickname);

        MemberDAO dao = new MemberDAO();
        int result = dao.join(dto); 

        if (result == 1) {

            //  회원가입 성공하면 자동 계좌 생성
            AccountDAO accDao = new AccountDAO();
            AccountDTO acc = new AccountDTO();

            acc.setUserId(id);              // FK
            acc.setAccountName(nickname);   // 계좌 별명
            acc.setBalance(1000000);        // 초기 잔고 100만원
            acc.setAvailable(1000000);
            acc.setInvesting(0);
            acc.setProfit(0);

            accDao.createAccount(acc);

            resp.sendRedirect("login.jsp");

        } else {
            resp.sendRedirect("signup.jsp?error=1");
        }
    }
}
