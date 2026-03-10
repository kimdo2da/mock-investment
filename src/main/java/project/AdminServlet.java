package project;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //관리자 인증 확인
        Boolean isAdmin = (Boolean) req.getSession().getAttribute("admin");
        if (isAdmin == null || !isAdmin) {
            resp.sendRedirect("login.jsp");
            return;
        }

        AdminDAO dao = new AdminDAO();
        List<AccountDTO> accounts = dao.getAllAccounts();

        req.setAttribute("accounts", accounts);
        req.getRequestDispatcher("admin.jsp").forward(req, resp);
    }
}
