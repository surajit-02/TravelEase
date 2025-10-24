import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AdminLogout extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();
        res.sendRedirect("homepage.html");
    }
}
