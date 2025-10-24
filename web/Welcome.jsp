<%@ page import="javax.servlet.http.HttpSession" %>
<%
    HttpSession session = request.getSession(false); // get session
    String username = null;
    if(session != null){
        username = (String) session.getAttribute("username");
    }
%>

<!DOCTYPE html>
<html>
<head>
<style>
    .welcome {
        position: absolute;
        top: 10px;
        right: 20px; /* or left: 20px; if you want top-left */
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        font-size: 16px;
        color: #333;
    }
</style>
</head>
<body>
<% if(username != null){ %>
    <div class="welcome">
        Welcome, <b><%= username %></b>!
        <a href="LogoutServlet">Logout</a>
    </div>
<% } %>
