<%@ page import="java.sql.*" %>
<%
  String busId = request.getParameter("busId");
  String company = "";
  String source = "";
  String destination = "";
  double price = 0;
  String duration = "";
  String departureTime = "";

  try {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    Connection con = DriverManager.getConnection(
        "jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");

    PreparedStatement ps = con.prepareStatement("SELECT * FROM buses WHERE BUS_ID = ?");
    ps.setString(1, busId);
    ResultSet rs = ps.executeQuery();

    if (rs.next()) {
      company = rs.getString("COMPANY");
      source = rs.getString("SOURCE");
      destination = rs.getString("DESTINATION");
      price = rs.getDouble("PRICE");
      duration = rs.getString("DURATION");
      departureTime = rs.getString("DEPARTURE_TIME");
    }

    con.close();
  } catch (Exception e) {
    out.println("<p style='color:red'>Error: " + e.getMessage() + "</p>");
  }
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Book Bus</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f5f7fa;
      margin: 0;
      padding: 0;
    }

    .container {
      width: 90%;
      max-width: 600px;
      margin: 50px auto;
      background: #fff;
      padding: 25px 30px;
      border-radius: 10px;
      box-shadow: 0 0 10px rgba(0,0,0,0.2);
    }

    h2 {
      text-align: center;
      color: #004d99;
      margin-bottom: 20px;
    }

    label {
      font-weight: bold;
      display: block;
      margin-top: 10px;
    }

    input {
      width: 100%;
      padding: 10px;
      margin-top: 5px;
      border-radius: 5px;
      border: 1px solid #ccc;
    }

    button {
      margin-top: 20px;
      width: 100%;
      padding: 12px;
      background-color: #004d99;
      color: white;
      border: none;
      border-radius: 6px;
      cursor: pointer;
      font-weight: bold;
    }

    button:hover {
      background-color: #003366;
    }
  </style>
</head>
<body>

  <div class="container">
    <h2>Bus Booking</h2>

    <form action="ConfirmBusBooking" method="post">
      <input type="hidden" name="busId" value="<%= busId %>">

      <label>Company</label>
      <input type="text" value="<%= company %>" readonly>

      <label>From</label>
      <input type="text" value="<%= source %>" readonly>

      <label>To</label>
      <input type="text" value="<%= destination %>" readonly>

      <label>Departure Time</label>
      <input type="text" value="<%= departureTime %>" readonly>

      <label>Duration</label>
      <input type="text" value="<%= duration %>" readonly>

      <label>Fare per Seat</label>
      <input type="text" id="fare" value="<%= price %>" readonly>

      <label>Your Name</label>
      <input type="text" name="customer_name" required>

      <label>Email</label>
      <input type="email" name="email" required>

      <label>Number of Seats</label>
      <input type="number" name="seats" min="1" required>

      <button type="submit">Confirm Booking</button>
    </form>
  </div>

</body>
</html>
