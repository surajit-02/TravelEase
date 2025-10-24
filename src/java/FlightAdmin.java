import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class FlightAdmin extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE","system","manager");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM flights");

            out.println("<!DOCTYPE html><html><head>");
            out.println("<link rel='stylesheet' href='Admin.css'>");
            out.println("</head><body>");
            out.println("<div class='main-content'>");

            // Flight Section Card
            out.println("<div class='flight-section'>");

            // Add Flight Form
            out.println("<h3 style='margin-bottom:18px;'>Add New Flight</h3>");
            out.println("<form action='AddFlight' method='post' class='flight-form'>");
            out.println("<label>Name: <input type='text' name='flight_name' required></label>");

            // ✅ Company dropdown added
            out.println("<label>Company: ");
            out.println("<select name='company' required>");
            out.println("<option value='Air India'>Air India</option>");
            out.println("<option value='IndiGo'>IndiGo</option>");
            out.println("<option value='Vistara'>Vistara</option>");
            out.println("<option value='SpiceJet'>SpiceJet</option>");
            out.println("<option value='Go First'>Go First</option>");
            out.println("<option value='AirAsia India'>AirAsia India</option>");
            out.println("<option value='Alliance Air'>Alliance Air</option>");
            out.println("</select>");
            out.println("</label>");

            out.println("<label>From: <input type='text' name='source' required></label>");
            out.println("<label>To: <input type='text' name='destination' required></label>");
            out.println("<label>Price: <input type='number' name='price' required></label>");
            out.println("<label>Departure: <input type='text' name='departure_time' placeholder='10:30 AM' required></label>");
            out.println("<label>Duration: <input type='text' name='duration' placeholder='2h 30m' required></label>");
            out.println("<button type='submit'>Add Flight</button>");
            out.println("</form>");

            // Flight Table
            out.println("<div class='flight-table-container'>");
            out.println("<table class='flight-table'>");
            out.println("<thead><tr>");
            out.println("<th>ID</th><th>Name</th><th>Company</th><th>From</th><th>To</th><th>Price</th><th>Departure</th><th>Duration</th><th>Actions</th>");
            out.println("</tr></thead><tbody>");
            while(rs.next()) {
                int id = rs.getInt("flight_id");
                out.println("<tr>");
                out.println("<td>"+id+"</td>");
                out.println("<td>"+rs.getString("flight_name")+"</td>");
                out.println("<td>"+rs.getString("company")+"</td>");
                out.println("<td>"+rs.getString("source")+"</td>");
                out.println("<td>"+rs.getString("destination")+"</td>");
                out.println("<td>"+rs.getInt("price")+"</td>");
                out.println("<td>"+rs.getString("departure_time")+"</td>");
                out.println("<td>"+rs.getString("duration")+"</td>");
                out.println("<td>"
                    + "<a class='action-btn' style='background:#f87171;' href='DeleteFlight?flight_id="+id+"' onclick=\"return confirm('Delete flight?');\">Delete</a> "
                    + "<a class='action-btn' style='background:#6366f1;' href='UpdateFlightForm?flight_id="+id+"'>Update Price</a>"
                    + "</td>");
                out.println("</tr>");
            }
            out.println("</tbody></table>");
            out.println("</div>"); // flight-table-container

            out.println("</div>"); // flight-section
            out.println("</div>"); // main-content
            out.println("</body></html>");

            con.close();
        } catch(Exception e) {
            out.println("<p style='color:red;'>Error: "+e.getMessage()+"</p>");
        }
    }
}
