import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ShowFlights extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String src = req.getParameter("source");
        String dest = req.getParameter("destination");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");
            
            String sql = "SELECT * FROM flights";
            PreparedStatement ps;
            if (src != null && dest != null && !src.isEmpty() && !dest.isEmpty()) {
                sql += " WHERE LOWER(SOURCE) = ? AND LOWER(DESTINATION) = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, src.toLowerCase());
                ps.setString(2, dest.toLowerCase());
            } else {
                ps = con.prepareStatement(sql);
            }
            ResultSet rs = ps.executeQuery();

            out.println("<style>");
            out.println("table { width: 90%; margin: 30px auto; border-collapse: collapse; box-shadow: 0 2px 8px rgba(0,0,0,0.1); font-family: Arial, sans-serif; }");
            out.println("th, td { padding: 12px 18px; border-bottom: 1px solid #eee; text-align: left; font-size: 16px; }");
            out.println("th { background: #f8f8f8; color: #222; }");
            out.println("tr:hover { background: #f3f8fa; }");
            out.println("</style>");
            
            out.println("<table>");
            out.println("<tr><th>Flight ID</th><th>Flight Name</th><th>Company</th><th>From</th><th>To</th><th>Price</th><th>Departure</th><th>Duration</th></tr>");

            boolean found = false;
            while (rs.next()) {
                found = true;
                out.println("<tr>");
                out.println("<td>" + rs.getString("FLIGHT_ID") + "</td>");
                out.println("<td>" + rs.getString("FLIGHT_NAME") + "</td>");
                out.println("<td>" + rs.getString("COMPANY") + "</td>");
                out.println("<td>" + rs.getString("SOURCE") + "</td>");
                out.println("<td>" + rs.getString("DESTINATION") + "</td>");
                out.println("<td>₹" + rs.getInt("PRICE") + "</td>");
                out.println("<td>" + rs.getString("DEPARTURE_TIME") + "</td>");
                out.println("<td>" + rs.getString("DURATION") + "</td>");
                out.println("<td><a href='BookFlight.jsp?flightId=" + rs.getString("FLIGHT_ID") + "' style='text-decoration:none; color:#007bff;'>Book</a></td>");
                out.println("</tr>");
            }

            if (!found) {
                out.println("<tr><td colspan='8' style='text-align:center; color:#c00; font-size:17px;'>No flights found for your search.</td></tr>");
            }
            out.println("</table>");

            con.close();
        } catch(Exception e) {
            out.println("<p style='color:red'>Error: " + e.getMessage() + "</p>");
        }
    }
}
