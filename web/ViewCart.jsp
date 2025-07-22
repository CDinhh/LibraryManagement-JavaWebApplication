<%-- 
    Document   : ViewCart
    Created on : Jun 19, 2025, 10:47:20 PM
    Author     : Dan Huy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>
        <link rel="stylesheet" href="css/viewcart.css"/>
    </head>
    <body>
        <c:if test="${empty sessionScope.User or sessionScope.User.role eq 'admin'}">
            <jsp:forward page="Login.jsp"/>
        </c:if>
        <a style="font-size:30px" href="MainController?action=home">Back</a>
        <c:choose>
            <c:when test="${not empty sessionScope.BorrowList}">
                 <table>
                            <tr>
                                <th>No</th>
                                <th>Id</th>
                                <th>Title</th>
                                <th>Author</th>
                                <th>Tool</th>
                            </tr>
                <c:forEach var="Book" items="${sessionScope.BorrowList}" varStatus="status">
                                      
                        <tr>
                            <td>${status.index +1}</td>
                            <td>${Book.id}</td>
                            <td>${Book.title}</td>
                            <td>${Book.author}</td>
                            <td>
                                <form action="MainController" method="post">
                                    <input type="hidden" name="txtid" value="${Book.id}">
                                    <input type="hidden" name="action" value="delete borrow">
                                    <input type="submit" value="delete book">
                                </form>
                            </td>

                        </tr>
                    
                </c:forEach>
                        </table>
                <p>${requestScope.DELETESTATUS}</p>
                <form action="MainController">
                    <input type="hidden" name="action" value="Send borrow">
                    <input type="submit" value="Submit borrow request"/>
                </form> 
            </c:when>
            <c:otherwise>
                <p>Your cart is empty</p>
            </c:otherwise>
        </c:choose>
    </body>
</html>
