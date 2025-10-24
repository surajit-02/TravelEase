import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RejectBookingServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USER = "system";
    private static final String DB_PASSWORD = "manager";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        String type = request.getParameter("type");
        int result = 0;

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            String tableName;
            switch (type.toLowerCase()) {
                case "flight": tableName = "bookings"; break;
                case "bus": tableName = "bus_bookings"; break;
                case "hotel": tableName = "hotel_bookings"; break;
                case "package": tableName = "package_bookings"; break;
                default: throw new IllegalArgumentException("Invalid booking type");
            }

            Class.forName("oracle.jdbc.driver.OracleDriver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE BOOKING_ID = ?")) {
                pstmt.setInt(1, bookingId);
                result = pstmt.executeUpdate();
            }

            if(result > 0) {
                out.println("<h2>Booking rejected successfully.</h2>");
            } else {
                out.println("<h2>No booking found with specified ID.</h2>");
            }
        } catch(Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        } finally {
            out.close();
        }
    }
}
