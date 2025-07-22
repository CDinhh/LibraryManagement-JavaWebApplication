<%-- 
    Document   : ViewFine
    Created on : Jun 24, 2025, 1:09:29 PM
    Author     : Dan Huy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin view fine</title>
        <link rel="stylesheet" type="text/css" href="css/viewfine.css">
    </head>
    <body>
        <c:if test="${empty sessionScope.User or sessionScope.User.role ne 'admin'}">
            <jsp:forward page="Login.jsp"/>
        </c:if>
        <h1>Admin Manage Fine</h1>
        <h2>Welcome ${sessionScope.User.name}</h2>    
        <a href="MainController?action=admin">Back</a <br>
        <c:choose>
            <c:when test="${not empty requestScope.FineList}">
                <table>
                    <tr>
                        <th >Id</th>
                        <th >Borrow_id</th>
                        <th >User_name</th>
                        <th >Fine_amount</th>
                        <th >Status</th>
                        <th>Check paid</th>
                    </tr>
                    <c:forEach var="Fine" items="${requestScope.FineList}">
                        <tr>
                            <td >${Fine.id}</td>
                            <td >${Fine.borrow_id}</td>
                            <td >${Fine.user_name}</td>
                            <td >${Fine.fine_amount}</td>
                            <td >${Fine.paid_status}</td>
                            <td>
                                <form action="MainController">
                                    <input type="hidden" name="FineId" value="${Fine.id}">
                                    <input type="hidden" name="BorrowId" value="${Fine.borrow_id}">
                                    <c:if test="${Fine.paid_status=='unpaid'}">
                                        <input type="submit" name="action" value="Paid check">
                                    </c:if>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <p>There are no fine</p>
            </c:otherwise>    
        </c:choose>
                <p>${requestScope.Msg}</p>
    </body>
</html>
