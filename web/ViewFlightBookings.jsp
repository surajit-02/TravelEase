<%@ page import="java.sql.*, java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Flight Bookings</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f7fb;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 1200px;
            margin: 30px auto;
            background: #fff;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }
        h2 {
            text-align: center;
            margin-bottom: 25px;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 12px 10px;
            border-bottom: 1px solid #ddd;
            text-align: center;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .btn-reject {
            background-color: #dc3545;
            color: #fff;
            border: none;
            padding: 6px 12px;
            border-radius: 5px;
            cursor: pointer;
            transition: 0.3s;
        }
        .btn-reject:hover {
            background-color: #b02a37;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>All Flight Bookings</h2>
    <table>
        <tr>
            <th>Booking ID</th>
            <th>Flight ID</th>
            <th>Passenger Name</th>
            <th>Seat Type</th>
            <th>Contact Number</th>
            <th>Payment Method</th>
            <th>Booking Date</th>
            <th>Number of Persons</th>
            <th>Total Price</th>
            <th>Action</th>
        </tr>

        <%
            // Database connection
            String url = "jdbc:oracle:thin:@localhost:1521:xe"; // change if needed
            String user = "system";
            String password = "manager";
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection(url, user, password);
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM bookings ORDER BY BOOKING_DATE DESC");

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                while(rs.next()){
        %>
        <tr>
            <td><%= rs.getInt("BOOKING_ID") %></td>
            <td><%= rs.getInt("FLIGHT_ID") %></td>
            <td><%= rs.getString("PASSENGER_NAME") %></td>
            <td><%= rs.getString("SEAT_TYPE") %></td>
            <td><%= rs.getString("CONTACT_NUMBER") %></td>
            <td><%= rs.getString("PAYMENT_METHOD") %></td>
            <td><%= sdf.format(rs.getDate("BOOKING_DATE")) %></td>
            <td><%= rs.getInt("NUM_PERSONS") %></td>
            <td><%= rs.getDouble("TOTAL_PRICE") %></td>
            <td>
                <form action="RejectBookingServlet" method="post" 
      onsubmit="return confirm('Are you sure you want to reject this booking?');">
    <input type="hidden" name="bookingId" value="<%= rs.getInt("BOOKING_ID") %>">
    <input type="hidden" name="type" value="flight"> <!-- IMPORTANT: tells servlet the type -->
    <button type="submit" class="btn-reject">Reject</button>
</form>

            </td>
        </tr>
        <%
                }
            } catch(Exception e){
                out.println("<tr><td colspan='10' style='color:red;'>Error: "+e.getMessage()+"</td></tr>");
            } finally {
                try { if(rs!=null) rs.close(); } catch(Exception e){}
                try { if(stmt!=null) stmt.close(); } catch(Exception e){}
                try { if(conn!=null) conn.close(); } catch(Exception e){}
            }
        %>

    </table>
</div>
</body>
</html>
