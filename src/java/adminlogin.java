import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class adminlogin extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");

        String user = req.getParameter("username");
        String pass = req.getParameter("password");

        // ✅ Static login check (replace later with DB table)
        if ("admin".equals(user) && "admin123".equals(pass)) {
            HttpSession session = req.getSession();
            session.setAttribute("admin", user);
            res.sendRedirect("Admin.html");
        } else {
            PrintWriter out = res.getWriter();
            out.println("<script>alert('Invalid Login'); window.location='admin_login.html';</script>");
        }
    }
}
