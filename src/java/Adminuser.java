import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Adminuser extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter pw1 = res.getWriter();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");
            Statement stmt = con.createStatement();
            String q1 = "select * from reg2";

            ResultSet rs = stmt.executeQuery(q1);

            // Full HTML with CSS included
            pw1.println("<!DOCTYPE html>");
pw1.println("<html lang='en'>");
pw1.println("<head>");
pw1.println("<meta charset='UTF-8'>");
pw1.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
pw1.println("<link rel='stylesheet' type='text/css' href='Admin.css'>");
pw1.println("<title>Registered Users</title>");
pw1.println("</head>");
pw1.println("<body>");

pw1.println("<div class='user-section'>");
pw1.println("<table class='table'>");
pw1.println("<thead><tr>");
pw1.println("<th>Username</th><th>Email</th><th>Password</th><th>Security</th><th>Answer</th><th>Action</th>");
pw1.println("</tr></thead>");
pw1.println("<tbody>");

while (rs.next()) {
    String username = rs.getString(1);

    pw1.println("<tr>");
    pw1.println("<td>" + username + "</td>");
    pw1.println("<td>" + rs.getString(2) + "</td>");
    pw1.println("<td>" + rs.getString(3) + "</td>");
    pw1.println("<td>" + rs.getString(4) + "</td>");
    pw1.println("<td>" + rs.getString(5) + "</td>");

    // Delete button → sends username to DeleteUser servlet
    pw1.println("<td><a href='DeleteUser?username=" + username + "' "
              + "onclick=\"return confirm('Are you sure you want to delete this user?');\" "
              + "style='color:red; font-weight:bold;'>Delete</a></td>");
    pw1.println("</tr>");
}

pw1.println("</tbody></table></div>");
pw1.println("</body></html>");


            con.close();
        } catch (Exception e) {
            pw1.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }
    }
}
