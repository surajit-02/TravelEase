<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.sql.*, java.util.*" %>

<%
    String flightId = request.getParameter("flightId");
    String numPersonsStr = request.getParameter("numPersons");
    String seatSelection = request.getParameter("seatSelection");
    String contactNumber = request.getParameter("contactNumber");
    String paymentMethod = request.getParameter("paymentMethod");

    int numPersons = Integer.parseInt(numPersonsStr);
    List<String> personNames = new ArrayList<>();
    for(int i = 1; i <= numPersons; i++) {
        personNames.add(request.getParameter("personName" + i));
    }

    int totalPrice = 0;

    try {
        // Load driver
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");

        // Get flight price
        PreparedStatement psPrice = con.prepareStatement("SELECT PRICE FROM FLIGHTS WHERE FLIGHT_ID=?");
        psPrice.setString(1, flightId);
        ResultSet rs = psPrice.executeQuery();
        int pricePerSeat = 0;
        if(rs.next()) {
            pricePerSeat = rs.getInt("PRICE");
        }
        rs.close();
        psPrice.close();

        totalPrice = pricePerSeat * numPersons;

        // Insert booking for each passenger
        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO BOOKINGS (FLIGHT_ID, PASSENGER_NAME, SEAT_TYPE, CONTACT_NUMBER, PAYMENT_METHOD, NUM_PERSONS, TOTAL_PRICE) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)");

        for(String name : personNames) {
            ps.setString(1, flightId);
            ps.setString(2, name);
            ps.setString(3, seatSelection);
            ps.setString(4, contactNumber);
            ps.setString(5, paymentMethod);
            ps.setInt(6, numPersons);
            ps.setInt(7, totalPrice);
            ps.executeUpdate();
        }

        con.close();

        // Redirect to Home after success
        response.sendRedirect("homepage.html?status=success");

    } catch(Exception e) {
        out.println("<h3>Error: " + e.getMessage() + "</h3>");
    }
%>
