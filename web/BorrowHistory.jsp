
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>All Borrow History</title>
        <link rel="stylesheet" type="text/css" href="css/borrowhistory.css">
    </head>
    <body>
        <h1>All Borrowed History</h1>
        <c:if test="${empty sessionScope.User or sessionScope.User.role ne 'admin'}">
            <jsp:forward page="Login.jsp"/>
        </c:if>
        <h2>Welcome ${sessionScope.User.name}</h2>
        <a href="MainController?action=admin">Back</a>
        <c:choose>
            <c:when test="${not empty requestScope.RecordList}">
                <table>
                    <tr>
                        <th>Id</th>
                        <th>User_id</th>
                        <th>User_Name</th>
                        <th>Book_id</th>
                        <th>Book_name</th>
                        <th>Borrow_date</th>
                        <th>Due_date</th>
                        <th>Return_date</th>
                        <th>Status</th>
                        <th>Check Return</th>
                    </tr>
                    <c:forEach var="Record" items="${requestScope.RecordList}">
                        <tr class="${Record.status eq 'overdue' ? 'highlightrow' : '' }">
                            <td >${Record.id}</td>
                            <td >${Record.user_id}</td>
                            <td >${Record.user_name}</td>
                            <td >${Record.book_id}</td>
                            <td >${Record.book_name}</td>
                            <td >${Record.borrow_date}</td>
                            <td >${Record.due_date}</td>
                            <td >${empty Record.return_date ? 'Havent returned yet' : Record.return_date}</td>    
                            <td >${Record.status}</td>
                            <td>
                                <c:if test="${Record.status ne 'returned' }">
                                    <form action="MainController">
                                        <input type="hidden" name="borrowId" value="${Record.id}">
                                        <input type="hidden" name="bookId" value="${Record.book_id}">
                                        <input type="submit" name="action" value="Returned book">
                                    </form> 
                                </c:if>
                                
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <p>There are no borrow record</p>
            </c:otherwise>    
        </c:choose>
                <p>${requestScope.RETURNSTATUS}</p>
    </body>
</html>
