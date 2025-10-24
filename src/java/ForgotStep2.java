import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ForgotStep2 extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        res.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = res.getWriter();

        String email = req.getParameter("email");
        String answer = req.getParameter("answer");

        try {
            // Load driver & connect
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");

            // ✅ Corrected column name
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM reg2 WHERE email=? AND LOWER(answer)=LOWER(?)");
            ps.setString(1, email);
            ps.setString(2, answer);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // ✅ User matched: show password reset form
                pw.println("<!DOCTYPE html><html><head><meta charset='UTF-8'>");
                pw.println("<title>Reset Password</title>");
                pw.println("<style>");
                pw.println("body{font-family:'Segoe UI';background:linear-gradient(135deg,#00c6ff,#0072ff);display:flex;align-items:center;justify-content:center;min-height:100vh;margin:0;}");
                pw.println(".box{background:rgba(255,255,255,0.15);backdrop-filter:blur(20px);border-radius:20px;padding:40px 30px;text-align:center;color:white;width:90%;max-width:420px;box-shadow:0 0 20px rgba(0,0,0,0.2);}");
                pw.println("input{width:100%;padding:12px;border:none;border-radius:30px;margin-bottom:20px;font-size:16px;text-align:center;}");
                pw.println("button{padding:12px 30px;border:none;border-radius:50px;background:#fff;color:#0072ff;font-weight:600;cursor:pointer;transition:0.3s;}button:hover{background:#0072ff;color:white;}");
                pw.println("</style></head><body>");
                pw.println("<form action='ForgotStep3' method='post' class='box'>");
                pw.println("<h2>Reset Your Password</h2>");
                pw.println("<input type='hidden' name='email' value='" + email + "'>");
                pw.println("<input type='password' name='newpass' placeholder='Enter new password' required>");
                pw.println("<input type='password' name='confpass' placeholder='Confirm new password' required>");
                pw.println("<button type='submit'>Update Password</button>");
                pw.println("</form></body></html>");
            } else {
                // ❌ Wrong answer
                pw.println("<!DOCTYPE html><html><head><meta charset='UTF-8'>");
                pw.println("<title>Wrong Answer</title>");
                pw.println("<style>");
                pw.println("body{font-family:'Segoe UI';background:linear-gradient(135deg,#ff416c,#ff4b2b);display:flex;align-items:center;justify-content:center;min-height:100vh;margin:0;color:white;}");
                pw.println(".box{background:rgba(255,255,255,0.15);padding:40px;border-radius:20px;text-align:center;}");
                pw.println("a{color:white;text-decoration:underline;}");
                pw.println("</style></head><body>");
                pw.println("<div class='box'><h2>Wrong Answer!</h2>");
                pw.println("<p>Please try again.</p>");
                pw.println("<a href='forgot.html'>Back</a>");
                pw.println("</div></body></html>");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            pw.println("<h3 style='color:white;'>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        }
    }
}
