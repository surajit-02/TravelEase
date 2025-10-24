import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AddFlight extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String name = req.getParameter("flight_name");
        String company = req.getParameter("company");
        String source = req.getParameter("source");
        String destination = req.getParameter("destination");
        int price = Integer.parseInt(req.getParameter("price"));
        String departure = req.getParameter("departure_time");
        String duration = req.getParameter("duration");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","manager");

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO flights(flight_name,company,source,destination,price,departure_time,duration) VALUES (?,?,?,?,?,?,?)"
            );
            ps.setString(1, name);
            ps.setString(2, company);
            ps.setString(3, source);
            ps.setString(4, destination);
            ps.setInt(5, price);
            ps.setString(6, departure);
            ps.setString(7, duration);
            ps.executeUpdate();

            con.close();
            res.sendRedirect("FlightAdmin");
        } catch(Exception e) {
            res.getWriter().println("Error: "+e.getMessage());
        }
    }
}
