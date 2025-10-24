package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;

public final class BookFlight_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");

    String flightId = request.getParameter("flightId");
    if (flightId == null || flightId.trim().isEmpty()) {
        out.println("<h3>Invalid flight selection. Please go back and choose a flight.</h3>");
        return;
    }

    String flightName = "", company = "", source = "", destination = "", departureTime = "", duration = "";
    int price = 0;

    try {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");
        PreparedStatement ps = con.prepareStatement("SELECT * FROM flights WHERE FLIGHT_ID = ?");
        ps.setString(1, flightId);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            flightName = rs.getString("FLIGHT_NAME");
            company = rs.getString("COMPANY");
            source = rs.getString("SOURCE");
            destination = rs.getString("DESTINATION");
            departureTime = rs.getString("DEPARTURE_TIME");
            duration = rs.getString("DURATION");
            price = rs.getInt("PRICE");
        } else {
            out.println("<h3>Flight not found.</h3>");
            return;
        }
        con.close();
    } catch(Exception e) {
        out.println("<h3>Error: " + e.getMessage() + "</h3>");
        return;
    }

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    <title>Book Flight - ");
      out.print( flightName );
      out.write("</title>\n");
      out.write("    <style>\n");
      out.write("        body { font-family: Arial, sans-serif; margin: 20px; background: #f4f7f8; color: #333; }\n");
      out.write("        .container { width: 600px; margin: auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px #ccc; }\n");
      out.write("        h2 { text-align: center; }\n");
      out.write("        label { display: block; margin-top: 15px; font-weight: bold; }\n");
      out.write("        input[type=text], input[type=tel], select { width: 100%; padding: 10px; margin-top: 5px; border: 1px solid #ccc; border-radius: 4px; }\n");
      out.write("        button { margin-top: 20px; width: 100%; padding: 12px; background: #007bff; color: white; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; }\n");
      out.write("        button:hover { background: #0056b3; }\n");
      out.write("        .flight-details { padding: 15px; background-color: #e9ecef; border-radius: 5px; margin-bottom: 20px; }\n");
      out.write("        .seat-selection { margin-top: 10px; }\n");
      out.write("        .person-input { margin-top: 10px; }\n");
      out.write("    </style>\n");
      out.write("    <script>\n");
      out.write("        // Dynamically show input fields for each passenger name based on number of persons selected\n");
      out.write("        function updatePersonInputs() {\n");
      out.write("            let count = document.getElementById('numPersons').value;\n");
      out.write("            let container = document.getElementById('personNamesContainer');\n");
      out.write("            container.innerHTML = \"\";\n");
      out.write("            for(let i=1; i <= count; i++) {\n");
      out.write("                let label = document.createElement('label');\n");
      out.write("                label.textContent = \"Name of Person \" + i;\n");
      out.write("                let input = document.createElement('input');\n");
      out.write("                input.type = 'text';\n");
      out.write("                input.name = 'personName' + i;\n");
      out.write("                input.required = true;\n");
      out.write("                input.placeholder = \"Person \" + i + \" full name\";\n");
      out.write("                input.className = 'person-input';\n");
      out.write("                container.appendChild(label);\n");
      out.write("                container.appendChild(input);\n");
      out.write("            }\n");
      out.write("        }\n");
      out.write("    </script>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("    <div class=\"container\">\n");
      out.write("        <h2>Book Your Flight</h2>\n");
      out.write("        \n");
      out.write("        <div class=\"flight-details\">\n");
      out.write("            <p><strong>Flight:</strong> ");
      out.print( flightName );
      out.write(' ');
      out.write('(');
      out.print( company );
      out.write(")</p>\n");
      out.write("            <p><strong>From:</strong> ");
      out.print( source );
      out.write(" <strong>To:</strong> ");
      out.print( destination );
      out.write("</p>\n");
      out.write("            <p><strong>Departure:</strong> ");
      out.print( departureTime );
      out.write("</p>\n");
      out.write("            <p><strong>Duration:</strong> ");
      out.print( duration );
      out.write("</p>\n");
      out.write("            <p><strong>Price (Per Seat):</strong> ₹");
      out.print( price );
      out.write("</p>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        <form action=\"ConfirmBooking.jsp\" method=\"post\">\n");
      out.write("            <input type=\"hidden\" name=\"flightId\" value=\"");
      out.print( flightId );
      out.write("\">\n");
      out.write("\n");
      out.write("            <label for=\"numPersons\">Number of Persons</label>\n");
      out.write("            <select id=\"numPersons\" name=\"numPersons\" onchange=\"updatePersonInputs()\" required>\n");
      out.write("                <option value=\"\">Select</option>\n");
      out.write("                <option value=\"1\">1</option>\n");
      out.write("                <option value=\"2\">2</option>\n");
      out.write("                <option value=\"3\">3</option>\n");
      out.write("                <option value=\"4\">4</option>\n");
      out.write("                <!-- add more if desired -->\n");
      out.write("            </select>\n");
      out.write("\n");
      out.write("            <div id=\"personNamesContainer\"></div>\n");
      out.write("\n");
      out.write("            <label for=\"seatSelection\">Seat Selection</label>\n");
      out.write("            <select id=\"seatSelection\" name=\"seatSelection\" required>\n");
      out.write("                <option value=\"\">Select Seat</option>\n");
      out.write("                <option value=\"Window\">Window</option>\n");
      out.write("                <option value=\"Middle\">Middle</option>\n");
      out.write("                <option value=\"Aisle\">Aisle</option>\n");
      out.write("            </select>\n");
      out.write("\n");
      out.write("            <label for=\"contactNumber\">Contact Number</label>\n");
      out.write("            <input type=\"tel\" id=\"contactNumber\" name=\"contactNumber\" placeholder=\"Enter your contact number\" required pattern=\"[0-9]{10}\" title=\"Enter 10 digit phone number\">\n");
      out.write("\n");
      out.write("            <label for=\"paymentMethod\">Payment Method</label>\n");
      out.write("            <select id=\"paymentMethod\" name=\"paymentMethod\" required>\n");
      out.write("                <option value=\"\">Select Payment Method</option>\n");
      out.write("                <option value=\"Credit Card\">Credit Card</option>\n");
      out.write("                <option value=\"Debit Card\">Debit Card</option>\n");
      out.write("                <option value=\"UPI\">UPI</option>\n");
      out.write("                <option value=\"Net Banking\">Net Banking</option>\n");
      out.write("                <option value=\"Cash\">Cash on Arrival</option>\n");
      out.write("            </select>\n");
      out.write("\n");
      out.write("            <button type=\"submit\">Confirm Booking</button>\n");
      out.write("        </form>\n");
      out.write("    </div>\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
