import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class BusAdmin extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM buses");

            out.println("<!DOCTYPE html><html><head>");
            out.println("<link rel='stylesheet' href='Admin.css'>");
            out.println("</head><body>");
            out.println("<div class='main-content'>");

            out.println("<div class='bus-section'>");
            out.println("<h3 style='margin-bottom:18px;'>Add New Bus</h3>");
            out.println("<form action='AddBus' method='post' class='bus-form'>");
            out.println("<label>Company: ");
            out.println("<select name='company' required>");
            out.println("<option value='GreenLine'>GreenLine</option>");
            out.println("<option value='ExpressLine'>ExpressLine</option>");
            out.println("<option value='Shyamoli'>Shyamoli</option>");
            out.println("<option value='Eagle'>Eagle</option>");
            out.println("<option value='Volvo'>Volvo</option>");
            out.println("<option value='SRS'>SRS</option>");
            out.println("</select></label>");
            out.println("<label>From: <input type='text' name='source' required></label>");
            out.println("<label>To: <input type='text' name='destination' required></label>");
            out.println("<label>Price: <input type='number' name='price' required></label>");
            out.println("<label>Departure: <input type='text' name='departure_time' required></label>");
            out.println("<label>Duration: <input type='text' name='duration' required></label>");
            out.println("<button type='submit'>Add Bus</button>");
            out.println("</form>");

            out.println("<div class='bus-table-container'>");
            out.println("<table class='bus-table'>");
            out.println("<thead><tr>");
            out.println("<th>ID</th><th>Company</th><th>From</th><th>To</th><th>Price</th><th>Departure</th><th>Duration</th><th>Actions</th>");
            out.println("</tr></thead><tbody>");

            while (rs.next()) {
                String id = rs.getString("bus_id");
                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + rs.getString("company") + "</td>");
                out.println("<td>" + rs.getString("source") + "</td>");
                out.println("<td>" + rs.getString("destination") + "</td>");
                out.println("<td>" + rs.getInt("price") + "</td>");
                out.println("<td>" + rs.getString("departure_time") + "</td>");
                out.println("<td>" + rs.getString("duration") + "</td>");
                out.println("<td>"
                    + "<a class='action-btn' style='background:#f87171;' href='DeleteBus?bus_id=" + id + "' onclick=\"return confirm('Delete bus?');\">Delete</a> "
                    + "<a class='action-btn' style='background:#6366f1;' href='UpdateBusForm?bus_id=" + id + "'>Update Price</a>"
                    + "</td>");
                out.println("</tr>");
            }

            out.println("</tbody></table>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            out.println("</body></html>");
            con.close();
        } catch (Exception e) {
            out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }
    }
}
