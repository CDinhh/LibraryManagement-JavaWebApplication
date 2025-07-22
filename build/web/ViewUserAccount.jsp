
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="css/viewuseraccount.css">
    </head>
    <body>
        <c:if test="${empty sessionScope.User or sessionScope.User.role ne 'admin'}">
            <jsp:forward page="Login.jsp"/>
        </c:if>
        <a href="MainController?action=admin">Back</a <br>
        <c:choose>
            <c:when test="${not empty requestScope.ALLUSERACCOUNT}">            
                <table>
                    <tr>
                        <th >Id</th>
                        <th >User_name</th>
                        <th >Email</th>
                        <th >Status</th>
                        <th></th>
                    </tr>
                    <c:forEach var="User" items="${requestScope.ALLUSERACCOUNT}">
                        <tr>
                            <td >${User.id}</td>
                            <td >${User.name}</td>
                            <td >${User.email}</td>
                            <td >${User.status}</td>
                            <td>
                                <form action="MainController">
                                    <input type="hidden" name="userId" value="${User.id}">
                                    <c:choose>
                                        <c:when test="${User.status == 'active'}">
                                            <input type="submit" name="action" value="Disable_user">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="submit" name="action" value="Enable_user">
                                        </c:otherwise>
                                    </c:choose>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <p>There are no user</p>
            </c:otherwise>    
        </c:choose>
    </body>
</html>
