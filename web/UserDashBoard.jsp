

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Library Home</title>
        <link rel="stylesheet" href="css/userdashboard.css"/>
    </head>
    <body>
        <c:if test="${empty sessionScope.User or sessionScope.User.role eq 'admin' }">
            <jsp:forward page="Login.jsp"/>
        </c:if>

        <div class="header">
            <div class="header-left">
                <a href="index.jsp"><img src="pics/logo.jpg" alt="logo"></a>
            </div>
            <div>
                <p>Em Yeu Co Van Library</p>
            </div>
            <div class="header-guest">
                <img src="pics/guest.jpg" alt="guest"/>
            </div>
        </div>

        <div class="search-bar">
            <div>
                <form action="MainController" method="post">
                    <span>Search book  </span><input type="text" name="txtsearch" value="${param.txtsearch}" placeholder="Name, category and author"/>
                    <input type="hidden" name="action" value="searchbook">
                    <input type="submit" value="Find"/>
                </form>
            </div>       
            <div>
                <a href="MainController?action=logout">Logout</a>|<a href="MainController?action=viewcart">View cart</a>
                |<a href="MainController?action=viewprofile">Change profile</a>|<a href="MainController?action=ViewRequestStatus">Request&Borrow status</a>

            </div>
        </div>

        <div>
            <h1 style="text-align: center">Welcome ${sessionScope.User.name}</h1>
        </div>

        <div>${BorrowComplete}</div>

        <div class="main-content">
            <h2 style="color: red">${requestScope.SEARCHERROR}</h2>                   
            <h2 style="color: red">${requestScope.BORROWERROR}</h2> 
            <c:if test="${empty param.txtsearch}">
                <h2 class="newbook-title">Top new book</h2>
            </c:if>    
            <c:if test="${not empty requestScope.SEARCHRESULT}">
                <div class="book-list">
                    <c:forEach var="Book" items="${requestScope.SEARCHRESULT}" varStatus="status">
                        <div class="book-item">
                            <form action="MainController" method="post">
                                <h3>Book ${status.index + 1}</h3>
                                <img src="${Book.url}">
                                <p>Id: ${Book.id}</p>
                                <p>Title: ${Book.title}</p>
                                <p>Category: ${Book.category}</p>
                                <c:choose>
                                    <c:when test="${Book.available_copies > 0}">
                                        <p>Available copy: ${Book.available_copies}</p>
                                    </c:when>
                                    <c:otherwise>
                                        <p style="color:red;">Book out of stock</p>
                                    </c:otherwise>
                                </c:choose>                                  
                                <input type="hidden" name="txtid" value="${Book.id}">
                                <input type="hidden" name="txtsearch" value="${param.txtsearch}">
                                <a href="MainController?id=${Book.id}&action=viewdetail">View detail</a><br><br>
                                <c:if test="${Book.available_copies > 0}">
                                    <input type="submit" name="action" value="Borrow book">
                                </c:if>
                            </form>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
            <c:if test="${empty requestScope.SEARCHERROR and empty requestScope.SEARCHRESULT and empty requestScope.BORROWERROR}">
                <jsp:forward page="MainController"> 
                    <jsp:param name="action" value="newbook"/>
                </jsp:forward>    
            </c:if>

        </div>
    </body>
</html>
