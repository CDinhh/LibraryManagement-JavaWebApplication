<%-- 
    Document   : ManageRequest
    Created on : Jun 23, 2025, 1:35:50 PM
    Author     : Dan Huy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Manage Request</title>
        <link rel="stylesheet" type="text/css" href="css/userrequest.css">
    </head>
    <body>
        <c:if test="${empty sessionScope.User or sessionScope.User.role ne 'admin'}">
            <jsp:forward page="Login.jsp"/>
        </c:if>
        <h1>Admin Manage Request</h1>
        <h2>Welcome ${sessionScope.User.name}</h2>    
        <a href="MainController?action=admin">Back</a <br>
        <c:choose>
            <c:when test="${not empty requestScope.RequestList}">
                <table>
                    <tr>
                        <th >Id</th>
                        <th >User_id</th>
                        <th >User_Name</th>
                        <th >Book_id</th>
                        <th >Book_name</th>
                        <th >Request_day</th>
                        <th >Status</th>
                        <th>Manage</th>
                    </tr>
                    <c:forEach var="Request" items="${requestScope.RequestList}">
                        <tr>
                            <td >${Request.id}</td>
                            <td >${Request.userId}</td>
                            <td >${Request.userName}</td>
                            <td >${Request.bookId}</td>
                            <td >${Request.bookName}</td>
                            <td >${Request.requestDate}</td>
                            <td >${Request.status}</td>
                            <c:if test="${Request.status != 'rejected'}">
                                <td><form action="MainController">
                                        <input type="hidden" name="requestID" value="${Request.id}">
                                        <input type="hidden" name="bookID" value="${Request.bookId}">
                                        <input type="hidden" name="userID" value="${Request.userId}">
                                        <c:if test="${Request.status != 'approved'}">
                                            <input type="submit" name="action" value="Approve request">
                                            <input type="submit" name="action" value="Reject request">
                                        </c:if>
                                        <c:if test="${Request.status == 'approved'}">
                                            <input type="submit" name="action" value="Borrowed">
                                        </c:if>                                       
                                    </form>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <p>There are no request</p>
            </c:otherwise>
        </c:choose>
        <p>${requestScope.Msg}</p>
    </body>
</html>
