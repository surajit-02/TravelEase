import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddBus extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String company = req.getParameter("company");
        String source = req.getParameter("source");
        String destination = req.getParameter("destination");
        int price = Integer.parseInt(req.getParameter("price"));
        String departure_time = req.getParameter("departure_time");
        String duration = req.getParameter("duration");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");

            // Generate unique Bus ID (yyyymmddXX)
            String datePrefix = new SimpleDateFormat("yyyyMMdd").format(new Date());
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT COUNT(*) FROM buses WHERE bus_id LIKE '" + datePrefix + "%'");
            rs.next();
            int count = rs.getInt(1) + 1;
            String busId = datePrefix + String.format("%02d", count);

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO buses(bus_id, company, source, destination, price, departure_time, duration) VALUES(?,?,?,?,?,?,?)");
            ps.setString(1, busId);
            ps.setString(2, company);
            ps.setString(3, source);
            ps.setString(4, destination);
            ps.setInt(5, price);
            ps.setString(6, departure_time);
            ps.setString(7, duration);

            ps.executeUpdate();
            con.close();

            res.sendRedirect("BusAdmin");
        } catch (Exception e) {
            out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }
    }
}
