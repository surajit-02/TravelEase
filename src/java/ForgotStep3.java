import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ForgotStep3 extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        res.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = res.getWriter();

        String email = req.getParameter("email");
        String newpass = req.getParameter("newpass");
        String confpass = req.getParameter("confpass");

        try {
            // Step 1: Validation
            if (newpass == null || confpass == null || !newpass.equals(confpass)) {
                pw.println("<!DOCTYPE html><html><head><meta charset='UTF-8'>");
                pw.println("<title>Password Mismatch</title>");
                pw.println("<style>");
                pw.println("body{font-family:'Segoe UI';background:linear-gradient(135deg,#ff416c,#ff4b2b);display:flex;align-items:center;justify-content:center;min-height:100vh;color:white;margin:0;}");
                pw.println(".box{background:rgba(255,255,255,0.15);padding:40px;border-radius:20px;text-align:center;}");
                pw.println("a{color:white;text-decoration:underline;}");
                pw.println("</style></head><body>");
                pw.println("<div class='box'><h2>Passwords Do Not Match!</h2>");
                pw.println("<p>Please try again.</p>");
                pw.println("<a href='forgot.html'>Back</a>");
                pw.println("</div></body></html>");
                return;
            }

            // Step 2: DB Connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");

            // Step 3: Update password for this email
            PreparedStatement ps = con.prepareStatement(
                "UPDATE reg2 SET password=? WHERE email=?");
            ps.setString(1, newpass);
            ps.setString(2, email);

            int updated = ps.executeUpdate();

            // Step 4: Response
            if (updated > 0) {
                pw.println("<!DOCTYPE html><html><head><meta charset='UTF-8'>");
                pw.println("<title>Password Updated</title>");
                pw.println("<style>");
                pw.println("body{font-family:'Segoe UI';background:linear-gradient(135deg,#00b09b,#96c93d);display:flex;align-items:center;justify-content:center;min-height:100vh;color:white;margin:0;}");
                pw.println(".box{background:rgba(255,255,255,0.15);padding:40px;border-radius:20px;text-align:center;}");
                pw.println("a{color:white;text-decoration:underline;}");
                pw.println("</style></head><body>");
                pw.println("<div class='box'><h2>Password Updated Successfully ✅</h2>");
                pw.println("<p>You can now <a href='login.html'>login</a> with your new password.</p>");
                pw.println("</div></body></html>");
            } else {
                pw.println("<!DOCTYPE html><html><head><meta charset='UTF-8'>");
                pw.println("<title>Error</title>");
                pw.println("<style>");
                pw.println("body{font-family:'Segoe UI';background:linear-gradient(135deg,#ff416c,#ff4b2b);display:flex;align-items:center;justify-content:center;min-height:100vh;color:white;margin:0;}");
                pw.println(".box{background:rgba(255,255,255,0.15);padding:40px;border-radius:20px;text-align:center;}");
                pw.println("a{color:white;text-decoration:underline;}");
                pw.println("</style></head><body>");
                pw.println("<div class='box'><h2>Failed to Update Password!</h2>");
                pw.println("<p>Email not found or some error occurred.</p>");
                pw.println("<a href='forgot.html'>Try Again</a>");
                pw.println("</div></body></html>");
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            pw.println("<h3 style='color:white;'>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        }
    }
}
