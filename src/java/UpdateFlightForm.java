import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class UpdateFlightForm extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        String flightId = req.getParameter("flight_id");

        if (flightId == null || flightId.trim().isEmpty()) {
            out.println("<p style='color:red;'>No Flight ID provided.</p>");
            return;
        }
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE","system","manager");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM flights WHERE flight_id = ?");
            ps.setString(1, flightId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                // Start HTML with CSS styling
                out.println("<!DOCTYPE html>");
                out.println("<html lang='en'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<title>Update Flight Price</title>");
                out.println("<style>");
                out.println("* { box-sizing: border-box; margin: 0; padding: 0; }");
                out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f5f7fa; color: #333; line-height: 1.6; }");
                out.println(".container { max-width: 600px; margin: 40px auto; padding: 20px; background: white; border-radius: 10px; box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1); }");
                out.println("h3 { color: #2c3e50; margin-bottom: 20px; text-align: center; font-size: 24px; }");
                out.println(".form-group { margin-bottom: 20px; }");
                out.println("label { display: block; margin-bottom: 8px; font-weight: 600; color: #34495e; }");
                out.println("input[type='number'] { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 4px; font-size: 16px; transition: border 0.3s; }");
                out.println("input[type='number']:focus { border-color: #3498db; outline: none; box-shadow: 0 0 5px rgba(52, 152, 219, 0.5); }");
                out.println(".current-price { background-color: #f8f9fa; padding: 12px; border-radius: 4px; border: 1px dashed #bdc3c7; margin-bottom: 15px; }");
                out.println(".update-price-btn { background-color: #3498db; color: white; border: none; padding: 12px 20px; border-radius: 4px; cursor: pointer; font-size: 16px; font-weight: 600; width: 100%; transition: background-color 0.3s; }");
                out.println(".update-price-btn:hover { background-color: #2980b9; }");
                out.println(".back-link { display: block; text-align: center; margin-top: 20px; color: #7f8c8d; text-decoration: none; }");
                out.println(".back-link:hover { color: #34495e; }");
                out.println(".flight-id { color: #e74c3c; font-weight: bold; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='container'>");
                out.println("<h3>Update Price for Flight ID: <span class='flight-id'>" + flightId + "</span></h3>");
                
                // Form
                out.println("<form action='UpdateFlight' method='post'>");
                out.println("<input type='hidden' name='flight_id' value='" + flightId + "'>");
                
                out.println("<div class='form-group'>");
                out.println("<label>Current Price:</label>");
                out.println("<div class='current-price'>" + rs.getInt("price") + "</div>");
                out.println("</div>");
                
                out.println("<div class='form-group'>");
                out.println("<label for='price'>New Price:</label>");
                out.println("<input type='number' id='price' name='price' required min='1'>");
                out.println("</div>");
                
                out.println("<button type='submit' class='update-price-btn'>Update Price</button>");
                out.println("</form>");
                
                out.println("<a href='javascript:history.back()' class='back-link'>← Back to previous page</a>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            } else {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Flight Not Found</title>");
                out.println("<style>");
                out.println("body { font-family: Arial, sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; background-color: #f5f7fa; }");
                out.println(".error-container { text-align: center; padding: 20px; background: white; border-radius: 10px; box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1); }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='error-container'>");
                out.println("<p style='color:red; font-size: 18px;'>Flight not found.</p>");
                out.println("<a href='javascript:history.back()' style='display: inline-block; margin-top: 15px; color: #3498db;'>Go back</a>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }
            con.close();
        } catch (Exception e) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Error</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; background-color: #f5f7fa; }");
            out.println(".error-container { text-align: center; padding: 20px; background: white; border-radius: 10px; box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1); max-width: 500px; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='error-container'>");
            out.println("<p style='color:red; font-size: 18px;'>Error: " + e.getMessage() + "</p>");
            out.println("<a href='javascript:history.back()' style='display: inline-block; margin-top: 15px; color: #3498db;'>Go back</a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}