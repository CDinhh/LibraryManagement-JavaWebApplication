<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Library Home</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/index.css">
    </head>
    <body>

        <div class="header">
            <div class="header-left">
                <a href="index.jsp"><img src="pics/logo.jpg" alt="logo"></a>
            </div>
            <div>
                <p>Em Yeu Co Van Library</p>
            </div>
            <div class="header-buttons">
                <form action="MainController" method="post">
                    <input type="hidden" name="action"  value="loginpage">
                    <input type="submit" value="Log In">
                </form>
                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="signuppage">
                    <input type="submit" value="Sign Up">
                </form>
            </div>
        </div>

        <div class="search-bar">
            <form action="MainController" method="get">
                <span>Search book</span>
                <input type="text" name="txtsearch" value="${param.txtsearch}" placeholder="Name, category and author">
                <input type="hidden" name="action" value="searchbook">
                <input type="submit" value="Find">
            </form>
        </div>

        <div class="main-content">

            <h2>${requestScope.SEARCHERROR}</h2>
            <c:if test="${empty param.txtsearch}">
                <h2 class="newbook-title">Top New Books</h2>
            </c:if>              
            <c:if test="${not empty requestScope.SEARCHRESULT}">
                <div class="book-list">
                    <c:forEach var="Book" items="${requestScope.SEARCHRESULT}" varStatus="status">
                        <div class="book-item">
                            <form action="ViewDetailBook" method="post">
                                <h3>Book ${status.index + 1}</h3>
                                <img class="book-img" src="${Book.url}" alt="Book image">
                                <p>ID : ${Book.id}</p>
                                <p>Title: ${Book.title}</p>
                                <p>Category: ${Book.category}</p>
                                <a href="MainController?id=${Book.id}&action=viewdetail">View detail</a>
                            </form>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
            <c:if test="${empty requestScope.SEARCHERROR and empty requestScope.SEARCHRESULT}">
                <jsp:forward page="MainController">
                    <jsp:param name="action" value="newbook"/>
                </jsp:forward>
            </c:if>


        </div>

    </body>
</html>
