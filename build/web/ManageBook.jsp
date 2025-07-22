<%-- 
    Document   : ManageBook
    Created on : Jul 3, 2025, 11:46:53 AM
    Author     : CDinh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar" %>
<%
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin manage book</title>
        <link rel="stylesheet" href="css/managebook.css"/>
    </head>
    <body>
        <c:if test="${empty sessionScope.User or sessionScope.User.role ne 'admin'}">
            <jsp:forward page="Login.jsp"/>
        </c:if>
        <h1>Admin Manage books</h1>
        <h2>Welcome ${sessionScope.User.name}</h2>
        <a href="MainController?action=admin">Back</a>
            <c:if test="${not empty requestScope.ALLBOOKLIST}">
                <table>
                    <tr>
                       <th>Id</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th>ISBN</th>
                        <th>Category</th>
                        <th>Published Year</th>
                        <th>Total copies</th>
                        <th>Available copies</th>
                        <th>Status</th>
                        <th>URL</th> 
                        <th>Edit</th>
                    </tr>
                    <c:forEach var="b" items="${ALLBOOKLIST}">
                        <form action="MainController" method="post">
                            <tr>
                                <td><input type="text" name="id" value="${b.id}" readonly></td>
                                <td><input type="text" name="title" value="${b.title}" required=""></td>
                                <td><input type="text" name="author" value="${b.author}" required=""></td>
                                <td><input type="text" name="isbn" value="${b.isbn}" required=""></td>
                                <td><input type="text" name="category" value="${b.category}" required=""></td>
                                <td><input type="number" min="1980" max="<%= Calendar.getInstance().get(Calendar.YEAR) %>" name="published_year" value="${b.published_year}" required=""></td>
                                <td><input type="number" min="0" name="total_copies" value="${b.total_copies}" required=""></td>
                                <td><input type="number" min="0" max="${b.total_copies}" name="available_copies" value="${b.available_copies}" required=""></td>
                                <td>
                                    <select name="status">
                                        <option value="active" ${b.status == 'active' ? 'selected' : ''}>active</option>
                                        <option value="inactive" ${b.status == 'inactive' ? 'selected' : ''}>inactive</option>
                                    </select>
                                </td>
                                <td><input type="text" name="url" value="${b.url}"></td>
                                <td>
                                    <input type="hidden" name="action" value="edit book">
                                    <input type="submit" value="Update">
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
                    
                     <form action="MainController" method="post">
                        <tr>
                            <td><input type="text" name="addid" readonly="" placeholder="Add a book->"></td>
                            <td><input type="text" name="addtitle" required="" placeholder="Input title"></td>
                            <td><input type="text" name="addauthor" required="" placeholder="Input author"></td>
                            <td><input type="text" name="addisbn" required="" placeholder="Input isbn"></td>
                            <td><input type="text" name="addcategory" required="" placeholder="Input category"></td>
                            <td><input type="number" name="addpublished_year" min="1980" max="<%= java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) %>" required placeholder="Input year"></td>
                            <td><input type="number" name="addtotal_copies" min="0" required="" placeholder="Input total"></td>
                            <td><input type="number" name="addavailable_copies" min="0" max="${RequestScope.addtotal_copies}" required="" placeholder="Input available"></td>
                            <td>
                                <select name="addstatus" required="">
                                    <option value="" disabled selected hidden>Choose 1</option>
                                    <option value="active">active</option>
                                    <option value="inactive">inactive</option>
                                </select>
                            </td>
                            <td><input type="text" name="addurl" required="" placeholder="Input url"></td>
                            <td>
                                <input type="hidden" name="action" value="add book">
                                <input type="submit" value="Add">
                            </td>
                        </tr>
                    </form>
                    
                </table>
                <p style="color:red;">${requestScope.SETBOOKSTATUS}</p>
            </c:if>
        <c:if test="${empty ALLBOOKLIST}">
            <p style="color:red;">Book list is empty.</p>
        </c:if>
    </body>
</html>
