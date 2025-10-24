import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class UpdateBusForm extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String busId = req.getParameter("bus_id");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");

            PreparedStatement ps = con.prepareStatement("SELECT * FROM buses WHERE bus_id=?");
            ps.setString(1, busId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                out.println("<!DOCTYPE html><html><head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<title>Update Bus Price</title>");
                out.println("<style>");
                out.println("* { margin: 0; padding: 0; box-sizing: border-box; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }");
                out.println("body { background-color: #f5f7fa; color: #333; line-height: 1.6; display: flex; justify-content: center; align-items: center; min-height: 100vh; padding: 20px; }");
                out.println(".update-container { background: #fff; border-radius: 14px; box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); width: 100%; max-width: 600px; padding: 30px; }");
                out.println(".update-header { text-align: center; margin-bottom: 30px; padding-bottom: 20px; border-bottom: 1px solid #eef0f5; }");
                out.println(".update-header h3 { color: #2c3e50; font-size: 1.8rem; font-weight: 600; margin-bottom: 10px; }");
                out.println(".update-form { display: grid; gap: 20px; }");
                out.println(".bus-info { background-color: #f8f9fa; padding: 20px; border-radius: 8px; margin-bottom: 10px; }");
                out.println(".info-row { display: flex; margin-bottom: 12px; padding: 8px 0; border-bottom: 1px dashed #e5e7eb; }");
                out.println(".info-row:last-child { border-bottom: none; margin-bottom: 0; }");
                out.println(".info-label { font-weight: 600; color: #2c3e50; min-width: 120px; }");
                out.println(".info-value { color: #4b5563; }");
                out.println(".form-group { display: flex; flex-direction: column; }");
                out.println(".form-group label { font-weight: 500; color: #2c3e50; margin-bottom: 8px; }");
                out.println(".form-group input { padding: 14px 16px; border: 1px solid #d1d9e6; border-radius: 6px; font-size: 16px; transition: all 0.3s ease; }");
                out.println(".form-group input:focus { outline: none; border-color: #4a6cf7; box-shadow: 0 0 0 3px rgba(74, 108, 247, 0.15); }");
                out.println(".button-group { display: flex; gap: 15px; margin-top: 20px; }");
                out.println(".btn-update { background-color: #4a6cf7; color: white; border: none; padding: 14px 20px; border-radius: 6px; font-size: 16px; font-weight: 500; cursor: pointer; transition: background-color 0.3s ease; flex: 1; }");
                out.println(".btn-update:hover { background-color: #3a5cd8; }");
                out.println(".btn-cancel { background-color: #f3f4f6; color: #4b5563; border: 1px solid #d1d5db; padding: 14px 20px; border-radius: 6px; font-size: 16px; font-weight: 500; cursor: pointer; transition: all 0.3s ease; text-decoration: none; text-align: center; flex: 1; }");
                out.println(".btn-cancel:hover { background-color: #e5e7eb; }");
                out.println("@media (max-width: 600px) { .update-container { padding: 20px; } .button-group { flex-direction: column; } .info-row { flex-direction: column; } .info-label { margin-bottom: 5px; } }");
                out.println("</style>");
                out.println("</head><body>");
                
                out.println("<div class='update-container'>");
                out.println("<div class='update-header'>");
                out.println("<h3>Update Bus Price</h3>");
                out.println("</div>");
                
                out.println("<form action='UpdateBus' method='post' class='update-form'>");
                out.println("<input type='hidden' name='bus_id' value='" + rs.getString("bus_id") + "'>");
                
                out.println("<div class='bus-info'>");
                out.println("<div class='info-row'><span class='info-label'>Bus ID:</span><span class='info-value'>" + rs.getString("bus_id") + "</span></div>");
                out.println("<div class='info-row'><span class='info-label'>Company:</span><span class='info-value'>" + rs.getString("company") + "</span></div>");
                out.println("<div class='info-row'><span class='info-label'>From:</span><span class='info-value'>" + rs.getString("source") + "</span></div>");
                out.println("<div class='info-row'><span class='info-label'>To:</span><span class='info-value'>" + rs.getString("destination") + "</span></div>");
                out.println("<div class='info-row'><span class='info-label'>Current Price:</span><span class='info-value'>" + rs.getInt("price") + "</span></div>");
                out.println("</div>");
                
                out.println("<div class='form-group'>");
                out.println("<label for='price'>New Price:</label>");
                out.println("<input type='number' name='price' id='price' required>");
                out.println("</div>");
                
                out.println("<div class='button-group'>");
                out.println("<button type='submit' class='btn-update'>Update Price</button>");
                out.println("<a href='BusAdmin' class='btn-cancel'>Cancel</a>");
                out.println("</div>");
                out.println("</form>");
                
                out.println("</div>");
                
                out.println("<script>");
                out.println("document.addEventListener('DOMContentLoaded', function() {");
                out.println("const priceInput = document.getElementById('price');");
                out.println("if (priceInput) { priceInput.focus(); }");
                out.println("const form = document.querySelector('.update-form');");
                out.println("if (form) {");
                out.println("form.addEventListener('submit', function(e) {");
                out.println("if (!priceInput.value.trim() || isNaN(priceInput.value) || parseInt(priceInput.value) <= 0) {");
                out.println("e.preventDefault();");
                out.println("priceInput.style.borderColor = 'red';");
                out.println("alert('Please enter a valid price.');");
                out.println("}");
                out.println("});");
                out.println("}");
                out.println("});");
                out.println("</script>");
                
                out.println("</body></html>");
            } else {
                out.println("<p style='color:red;'>No bus found with ID: " + busId + "</p>");
            }

            con.close();
        } catch (Exception e) {
            out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }
    }
}