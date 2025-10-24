import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Homepage extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false); // get existing session
        if (session == null || session.getAttribute("username") == null) {
            res.sendRedirect("login.html");
            return;
        }

        String username = (String) session.getAttribute("username");

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Home - TravelEase</title>");
        out.println("<style>");
        out.println(".welcome { position: absolute; top: 10px; right: 20px; font-family: 'Segoe UI'; font-size:16px; color:#333; }");
        out.println(".logout-btn { display:inline-block; margin-left:10px; background:#dc3545; color:white; padding:5px 12px; text-decoration:none; border-radius:5px; }");
        out.println(".logout-btn:hover { background:#b02a37; }");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<div class='welcome'>");
        out.println("Welcome, <b>" + username + "</b>");
        out.println(" <a href='LogoutServlet' class='logout-btn'>Logout</a>");
        out.println("</div>");

        out.println("<h1 style='text-align:center; margin-top:80px;'>Welcome to TravelEase!</h1>");
        out.println("<p style='text-align:center;'>Use menu to book bus, flight, or view bookings.</p>");

        out.println("</body></html>");
    }
}
