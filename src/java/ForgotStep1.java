import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ForgotStep1 extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        res.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = res.getWriter();
        String email = req.getParameter("email");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");

            PreparedStatement ps = con.prepareStatement(
                "SELECT security_question FROM reg2 WHERE email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String question = rs.getString("security_question");

                pw.println("<!DOCTYPE html><html><head><meta charset='UTF-8'>");
                pw.println("<title>Security Question</title>");
                pw.println("<style>");
                pw.println("body{font-family:'Segoe UI';background:linear-gradient(135deg,#00c6ff,#0072ff);display:flex;align-items:center;justify-content:center;min-height:100vh;}");
                pw.println(".box{background:rgba(255,255,255,0.15);backdrop-filter:blur(20px);border-radius:20px;padding:40px 30px;text-align:center;color:white;width:90%;max-width:420px;}");
                pw.println("input{width:100%;padding:12px;border:none;border-radius:30px;margin-bottom:20px;font-size:16px;}");
                pw.println("button{padding:12px 30px;border:none;border-radius:50px;background:#fff;color:#0072ff;font-weight:600;cursor:pointer;}");
                pw.println("</style></head><body>");
                pw.println("<form action='ForgotStep2' method='post' class='box'>");
                pw.println("<h2>Answer Security Question</h2>");
                pw.println("<p><b>Q:</b> " + question + "</p>");
                pw.println("<input type='hidden' name='email' value='" + email + "'>");
                pw.println("<input type='text' name='answer' placeholder='Enter your answer' required>");
                pw.println("<button type='submit'>Verify</button>");
                pw.println("</form></body></html>");
            } else {
                pw.println("<div style='color:white;text-align:center;margin-top:50px;'>Email not found!<br><a href='forgot.html' style='color:white;text-decoration:underline;'>Try Again</a></div>");
            }

            rs.close(); ps.close(); con.close();
        } catch (Exception e) {
            pw.println("<h3 style='color:white;'>Error: "+ e.getMessage() +"</h3>");
        }
    }
}
