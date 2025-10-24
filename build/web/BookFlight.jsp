<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page import="java.sql.*, javax.naming.*, javax.sql.*" %>
<%
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
%>
<!DOCTYPE html>
<html>
<head>
    <title>Book Flight - <%= flightName %></title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background: #f4f7f8; color: #333; }
        .container { width: 600px; margin: auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px #ccc; }
        h2 { text-align: center; }
        label { display: block; margin-top: 15px; font-weight: bold; }
        input[type=text], input[type=tel], select { width: 100%; padding: 10px; margin-top: 5px; border: 1px solid #ccc; border-radius: 4px; }
        button { margin-top: 20px; width: 100%; padding: 12px; background: #007bff; color: white; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; }
        button:hover { background: #0056b3; }
        .flight-details { padding: 15px; background-color: #e9ecef; border-radius: 5px; margin-bottom: 20px; }
        .seat-selection { margin-top: 10px; }
        .person-input { margin-top: 10px; }
    </style>
    <script>
        // Dynamically show input fields for each passenger name based on number of persons selected
        function updatePersonInputs() {
            let count = document.getElementById('numPersons').value;
            let container = document.getElementById('personNamesContainer');
            container.innerHTML = "";
            for(let i=1; i <= count; i++) {
                let label = document.createElement('label');
                label.textContent = "Name of Person " + i;
                let input = document.createElement('input');
                input.type = 'text';
                input.name = 'personName' + i;
                input.required = true;
                input.placeholder = "Person " + i + " full name";
                input.className = 'person-input';
                container.appendChild(label);
                container.appendChild(input);
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>Book Your Flight</h2>
        
        <div class="flight-details">
            <p><strong>Flight:</strong> <%= flightName %> (<%= company %>)</p>
            <p><strong>From:</strong> <%= source %> <strong>To:</strong> <%= destination %></p>
            <p><strong>Departure:</strong> <%= departureTime %></p>
            <p><strong>Duration:</strong> <%= duration %></p>
            <p><strong>Price (Per Seat):</strong> ₹<%= price %></p>
        </div>

        <form action="ConfirmBooking.jsp" method="post">
            <input type="hidden" name="flightId" value="<%= flightId %>">

            <label for="numPersons">Number of Persons</label>
            <select id="numPersons" name="numPersons" onchange="updatePersonInputs()" required>
                <option value="">Select</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <!-- add more if desired -->
            </select>

            <div id="personNamesContainer"></div>

            <label for="seatSelection">Seat Selection</label>
            <select id="seatSelection" name="seatSelection" required>
                <option value="">Select Seat</option>
                <option value="Window">Window</option>
                <option value="Middle">Middle</option>
                <option value="Aisle">Aisle</option>
            </select>

            <label for="contactNumber">Contact Number</label>
            <input type="tel" id="contactNumber" name="contactNumber" placeholder="Enter your contact number" required pattern="[0-9]{10}" title="Enter 10 digit phone number">

            <label for="paymentMethod">Payment Method</label>
            <select id="paymentMethod" name="paymentMethod" required>
                <option value="">Select Payment Method</option>
                <option value="Credit Card">Credit Card</option>
                <option value="Debit Card">Debit Card</option>
                <option value="UPI">UPI</option>
                <option value="Net Banking">Net Banking</option>
                <option value="Cash">Cash on Arrival</option>
            </select>

            <button type="submit">Confirm Booking</button>
        </form>
    </div>
</body>
</html>
