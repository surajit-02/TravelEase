import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class UpdateFlight extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        String flightId = req.getParameter("flight_id");
        String newPrice = req.getParameter("price");

        if (flightId == null || newPrice == null) {
            out.println("<p style='color:red;'>Invalid request.</p>");
            return;
        }
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE","system","manager");
            PreparedStatement ps = con.prepareStatement("UPDATE flights SET price = ? WHERE flight_id = ?");
            ps.setInt(1, Integer.parseInt(newPrice));
            ps.setString(2, flightId);
            int updated = ps.executeUpdate();
            if (updated > 0) {
                // redirect to admin or status page
                res.sendRedirect("FlightAdmin");
            } else {
                out.println("<p style='color:red;'>No flight found with ID: " + flightId + "</p>");
            }
            con.close();
        } catch (Exception e) {
            out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }
    }
}
