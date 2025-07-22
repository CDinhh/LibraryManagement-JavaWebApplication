

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Request Status</title>
        <link rel="stylesheet" href="css/viewrequeststatus.css"/>
    </head>
    <body>
        <a style="font-size:30px" href="MainController?action=home">Back</a>
        <h1>Request history</h1>
        <c:if test="${empty sessionScope.User or sessionScope.User.role eq 'admin'}">
            <jsp:forward page="Login.jsp"/>
        </c:if>
        <c:choose>
            <c:when test="${not empty requestScope.RequestStatus}">
                <table>
                    <tr>
                        <th>Id</th>
                        <th>User_id</th>
                        <th>User_name</th>
                        <th>Book_id</th>
                        <th>Book_name</th>
                        <th>Request_day</th>
                        <th>Status</th>
                        <th>Remove</th>
                    </tr>
                    <c:forEach var="Request" items="${requestScope.RequestStatus}">
                        <tr>
                            <td>${Request.id}</td>
                            <td>${Request.userId}</td>
                            <td>${Request.userName}</td>
                            <td>${Request.bookId}</td>
                            <td>${Request.bookName}</td>
                            <td>${Request.requestDate}</td>
                            <td>${Request.status}</td>
                            <c:if test="${Request.status!='rejected' and Request.status!='approved'}">
                                <td>
                                    <a class="btn-cancel" href="MainController?action=RemoveRequest&id=${Request.id}">Cancel</a>
                                </td>
                            </c:if>

                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <p>You havent request anything!</p>
            </c:otherwise>
        </c:choose>
        <h2>${requestScope.Msg}</h2>
        <h1>Borrow history</h1>
        <c:choose>
            <c:when test="${not empty requestScope.BorrowedHistory}">
                <table>
                    <tr>
                        <th>Id</th>
                        <th>User_id</th>
                        <th>User_name</th>
                        <th>Book_id</th>
                        <th>Book_name</th>
                        <th>Borrow date</th>
                        <th>Due date</th>
                        <th>Return date</th>
                        <th>Status</th>
                    </tr>
                    <c:forEach var="Borrowed" items="${requestScope.BorrowedHistory}">
                        <tr>
                            <td>${Borrowed.id}</td>
                            <td>${Borrowed.user_id}</td>
                            <td>${Borrowed.user_name}</td>
                            <td>${Borrowed.book_id}</td>
                            <td>${Borrowed.book_name}</td>
                            <td>${Borrowed.borrow_date}</td>
                            <td>${Borrowed.due_date}</td>
                            <td>${Borrowed.return_date==null? "Havent returned":Borrowed.return_date}</td>
                            <td>${Borrowed.status}</td>
                        </tr>
                    </c:forEach>
                </table>

            </c:when>
            <c:otherwise>
                <p>You dont have borrowed history!</p>
            </c:otherwise>
        </c:choose>
    </body>
</html>
