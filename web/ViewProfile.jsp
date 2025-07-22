

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Base64" %>
<%@page  import="dto.User"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Profile</title>
        <link rel="stylesheet" href="css/viewprofile.css"/>
    </head>
    <body>
        <c:if test="${empty sessionScope.User or sessionScope.User.role eq 'admin'}">
            <jsp:forward page="Login.jsp"/>
        </c:if>
        
        <%
            User user = (User) session.getAttribute("User");
            String decoded = new String( Base64.getDecoder().decode(user.getPassword()) );
        %>
        <a href="MainController?action=home">Back</a>
        <form action="MainController" method="post">
            <p>Name: <input type="text" name="txtname" value="${sessionScope.User.name}" required=""></p>
            <p>Email: <input type="text" value="${sessionScope.User.email}" readonly=""></p>
            <p>Password: <input type="text" name="txtpassword" value="<%=decoded%>" required=""></p>
            <p>Role: <input type="text" value="${sessionScope.User.role}" readonly=""></p>
            <p>Status: <input type="text" value="${sessionScope.User.status}" readonly=""></p>
            <input type="hidden" name="action" value="changeprofile">        
            <input type="submit" value="save">       
        </form>
            <h3>${requestScope.UPDATESTATUS}</h3>
            
    </body>
</html>
