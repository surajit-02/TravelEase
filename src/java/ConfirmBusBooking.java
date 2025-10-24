import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.text.DecimalFormat; // Import for better fare formatting

public class ConfirmBusBooking extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // Use DecimalFormat for currency formatting
        DecimalFormat df = new DecimalFormat("0.00");

        // Input data sanitization and validation (highly recommended in a real app)
        // For this example, we'll stick to the original logic
        try {
            int busId = Integer.parseInt(req.getParameter("busId"));
            String name = req.getParameter("customer_name");
            String email = req.getParameter("email");
            int seats = Integer.parseInt(req.getParameter("seats"));
            
            // Basic input validation: check if seats is a positive number
            if (seats <= 0) {
                 out.println("<h3 style='color:red;text-align:center;'>Error: Number of seats must be at least 1.</h3>");
                 return;
            }


            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");

            // Get price for this bus
            PreparedStatement ps1 = con.prepareStatement("SELECT price FROM buses WHERE bus_id=?");
            ps1.setInt(1, busId);
            ResultSet rs = ps1.executeQuery();

            double price = 0;
            if (rs.next()) {
                price = rs.getDouble("price");
            } else {
                out.println("<h3 style='color:red;text-align:center;'>Error: Bus not found!</h3>");
                con.close(); // Close connection even on error
                return;
            }

            double totalFare = price * seats;

            // Insert booking
            PreparedStatement ps2 = con.prepareStatement(
                "INSERT INTO bus_bookings (bus_id, customer_name, email, seats, total_fare) VALUES (?, ?, ?, ?, ?)"
            );
            ps2.setInt(1, busId);
            ps2.setString(2, name);
            ps2.setString(3, email);
            ps2.setInt(4, seats);
            ps2.setDouble(5, totalFare);
            ps2.executeUpdate();

            // --- HTML Output with Embedded CSS for a better look ---
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Booking Confirmed</title>");
            out.println("<style>");
            out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f7f6; display: flex; justify-content: center; align-items: center; min-height: 100vh; margin: 0; }");
            out.println(".container { background-color: #ffffff; padding: 40px; border-radius: 12px; box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); width: 100%; max-width: 450px; text-align: center; }");
            out.println("h2 { color: #28a745; margin-bottom: 25px; font-size: 1.8em; border-bottom: 2px solid #28a745; padding-bottom: 10px; }");
            out.println("p { font-size: 1.1em; color: #555; margin: 15px 0; display: flex; justify-content: space-between; padding: 0 20px; }");
            out.println("p b { color: #333; font-weight: 600; }");
            out.println(".fare { font-size: 1.4em; color: #007bff; font-weight: bold; margin: 30px 0; padding-top: 15px; border-top: 1px dashed #ccc; }");
            out.println("a { display: inline-block; background-color: #007bff; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; margin-top: 25px; transition: background-color 0.3s; }");
            out.println("a:hover { background-color: #0056b3; }");
            out.println("</style>");
            out.println("</head><body>");

            out.println("<div class='container'>");
            out.println("<h2>&#10003; Booking Confirmed!</h2>");
            
            out.println("<p><b>Customer Name:</b> <span>" + name + "</span></p>");
            out.println("<p><b>Email:</b> <span>" + email + "</span></p>");
            out.println("<p><b>Bus ID:</b> <span>" + busId + "</span></p>");
            out.println("<p><b>Seats Booked:</b> <span>" + seats + "</span></p>");
            
            // Format totalFare to ensure two decimal places
            out.println("<p class='fare'><b>Total Fare:</b> <span>&#8377;" + df.format(totalFare) + "</span></p>");

            
            out.println("<a href='bus.html'>Book Another Bus</a>");
            out.println("</div>");
            out.println("</body></html>");
            // --- End of HTML Output ---

            con.close();
        } catch (NumberFormatException e) {
             out.println("<h3 style='color:red;text-align:center;'>Error: Invalid input for Bus ID or Seats. Please check your submission.</h3>");
        }
        catch (Exception e) {
            // General exception handler
            out.println("<h3 style='color:red;text-align:center;'>A database or connection error occurred.</h3>");
            // For development/debugging, you can print the full error
            // out.println("<p style='text-align:center;'>" + e.getMessage() + "</p>"); 
        }
    }
}