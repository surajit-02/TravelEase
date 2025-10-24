import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class UpdateBus extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String busId = req.getParameter("bus_id");
        int newPrice = Integer.parseInt(req.getParameter("price"));

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");

            PreparedStatement ps = con.prepareStatement("UPDATE buses SET price=? WHERE bus_id=?");
            ps.setInt(1, newPrice);
            ps.setString(2, busId);

            int rows = ps.executeUpdate();
            con.close();

            if (rows > 0) {
                res.sendRedirect("BusAdmin");
            } else {
                out.println("<p style='color:red;'>No bus found with ID: " + busId + "</p>");
            }
        } catch (Exception e) {
            out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }
    }
}
