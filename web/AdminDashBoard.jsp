

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin DashBoard</title>
        <link rel="stylesheet" href="css/admindashboard.css"/>
    </head>
    <body>
        <c:if test="${empty sessionScope.User or sessionScope.User.role ne 'admin'}">
            <jsp:forward page="Login.jsp"/>
        </c:if>
        <div>
            <h1>Welcome Admin ${fn:trim(sessionScope.User.name)}</h1>
        </div>
        <div class="nav-menu">
            <a href="MainController?action=logout">Logout</a>
            <a href="MainController?action=View system config">View system config</a>
            <a href="MainController?action=Manage request">User request</a>
            <a href="MainController?action=Borrow record">Borrow history</a>
            <a href="MainController?action=View fine admin">View fine</a>
            <a href="MainController?action=view manage book">Manage Books</a>
            <a href="MainController?action=manage inventory">Manage Inventory</a>
            <a href="MainController?action=View_all_user">Enable/Disable user</a>
        </div>

    </body>
</html>
