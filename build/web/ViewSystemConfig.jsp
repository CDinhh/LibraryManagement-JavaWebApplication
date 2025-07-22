<%-- 
    Document   : ViewSystemConfig
    Created on : Jun 21, 2025, 4:25:01 PM
    Author     : Dan Huy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/viewsystemconfig.css"/>
    </head>
    <body>
        <c:if test="${empty sessionScope.User or sessionScope.User.role ne 'admin'}">
            <jsp:forward page="Login.jsp"/>
        </c:if>
        <h1>Admin Config Manage</h1>
        <h2>Welcome ${sessionScope.User.name}</h2>
        <a href="MainController?action=admin">Back</a <br>
        <c:choose>
            <c:when test="${not empty requestScope.SystemList}">
                <table>
                    <tr>
                        <th>id</th>
                        <th>key</th>
                        <th>value</th>
                        <th>description</th>
                        <th></th>
                    </tr>
                    <c:forEach var="System" items="${requestScope.SystemList}">
                    <form action="MainController">
                        <tr>
                            <td><input type="text" name="txtid" value="${System.id}" readonly=""></td>
                            <td><input type="text" name="txtkey" value="${System.key}" required=""></td>
                            <td><input type="number" name="txtvalue" value="${System.value}" min="0" required="" step="any"></td>
                            <td><input type="text" name="txtdes" value="${System.description}" required=""></td>
                        <input type="hidden" name="action" value="EditSystemConfig">
                        <td><input type="submit" value="Save"></td> 
                    </tr>
                    </form>
                </c:forEach>     
            </table>              
            <form action="MainController">
                <input type="hidden" name="action" value="AddSystemConfig">
                <input type="submit" value="Add more"> 
            </form>
        </c:when>
        <c:otherwise>
            <p>Empty system config</p>
        </c:otherwise>
    </c:choose>
            <p>${requestScope.Msg}</p>
</body>
</html>
