import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class DeleteFlight extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("flight_id"));
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","manager");

            PreparedStatement ps = con.prepareStatement("DELETE FROM flights WHERE flight_id=?");
            ps.setInt(1, id);
            ps.executeUpdate();

            con.close();
            res.sendRedirect("FlightAdmin");
        } catch(Exception e) {
            res.getWriter().println("Error: "+e.getMessage());
        }
    }
}
