

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${requestScope.book.title}'s Details</title>
    </head>
    <body>
        <a style="font-size:30px" href="MainController?action=home">Back</a>
        <h1 style="text-align: center">Book detail</h1>
        <div style="text-align: center">
            <h2>Title: ${requestScope.book.title}</h2>                     
            <img style="width:60%" src="${book.url}">
            <p>Id : ${requestScope.book.id}</p>
            <p>Author: ${requestScope.book.author}</p>
            <p>ISBN: ${requestScope.book.isbn}</p>
            <p>Category: ${requestScope.book.category}</p>
            <p>Published year: ${requestScope.book.published_year}</p>
            <c:if test="${not empty sessionScope.User}">
                <p>Available copies: ${requestScope.book.available_copies}</p>
            </c:if>
            
        </div>
        
    </body>
</html>
