<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - View Flight Bookings</title>
    <style>
        body {
            font-family: "Segoe UI", sans-serif;
            background-color: #f4f7fb;
            padding: 20px;
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 25px;
        }
        table {
            width: 95%;
            margin: auto;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 0 8px rgba(0,0,0,0.1);
        }
        th, td {
            padding: 12px 14px;
            border: 1px solid #ccc;
            text-align: center;
        }
        th {
            background-color: #007bff;
            color: white;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .no-data {
            text-align: center;
            color: red;
            font-weight: bold;
            margin-top: 20px;
        }
        .back-btn {
            display: inline-block;
            background: #007bff;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 6px;
            margin: 25px auto;
            display: block;
            width: fit-content;
        }
        .back-btn:hover {
            background: #0056b3;
        }
    </style>
</head>
<body>
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
            <th>No. of Persons</th>
            <th>Total Price (?)</th>
        </tr>

        <%
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");

                String sql = "SELECT * FROM BOOKINGS ORDER BY BOOKING_ID DESC";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();

                boolean hasData = false;
                while(rs.next()) {
                    hasData = true;
        %>
        <tr>
            <td><%= rs.getInt("BOOKING_ID") %></td>
            <td><%= rs.getString("FLIGHT_ID") %></td>
            <td><%= rs.getString("PASSENGER_NAME") %></td>
            <td><%= rs.getString("SEAT_TYPE") %></td>
            <td><%= rs.getString("CONTACT_NUMBER") %></td>
            <td><%= rs.getString("PAYMENT_METHOD") %></td>
            <td><%= rs.getDate("BOOKING_DATE") %></td>
            <td><%= rs.getInt("NUM_PERSONS") %></td>
            <td>?<%= rs.getInt("TOTAL_PRICE") %></td>
        </tr>
        <%
                }
                if(!hasData) {
        %>
        </table>
        <p class="no-data">No flight bookings found.</p>
        <%
                }
            } catch(Exception e) {
                out.println("<p class='no-data'>Error: " + e.getMessage() + "</p>");
            } finally {
                try { if(rs != null) rs.close(); } catch(Exception e) {}
                try { if(ps != null) ps.close(); } catch(Exception e) {}
                try { if(con != null) con.close(); } catch(Exception e) {}
            }
        %>
    </table>

    <a href="admin_dashboard.html" class="back-btn">Back to Dashboard</a>
</body>
</html>
