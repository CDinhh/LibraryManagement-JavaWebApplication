<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin Inventory Manage</title>
    <link rel="stylesheet" href="css/manageinventory.css"/>
</head>
<body>
    <c:if test="${empty sessionScope.User or sessionScope.User.role ne 'admin'}">
            <jsp:forward page="Login.jsp"/>
    </c:if>
    <h1>Admin Update Inventory</h1>
    <h2>Welcome ${sessionScope.User.name}</h2>    
    <a href="MainController?action=admin">Back</a <br>
    <h1>Import By :</h1>
<div class="container">
    <div class="form-box">
        <h2>1. Available Books</h2>
        <form action="MainController" method="post">
            <div class="form-group">
                <label>Choose a book</label>
                <select name="bookId" required>
                    <c:forEach var="book" items="${ALLBOOKLIST}">
                        <option value="${book.id}">${book.title}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label>Total (*)</label>
                <input type="number" name="quantity" min="1" step="1" required />
            </div>

            <div class="form-group">
                <label>Description (*)</label>
                <textarea name="description" rows="3" required=""></textarea>
            </div>

            <input type="hidden" name="action" value="updateinventory available book" />
            <button type="submit">Import</button>
        </form>
    </div>

  
    <div class="form-box">
        <h2>2. New Book</h2>
        <form action="MainController" method="post">
            <div class="form-group">
                <label>Book Name (*)</label> 
                <input type="text" name="newBookName" required />
            </div>
            
            <div class="form-group">
                <label>Author (*)</label>
                <input type="text" name="newBookAuthor" required />
            </div>
            
            <div class="form-group">
                <label>ISBN (*)</label>
                <input type="text" name="newBookISBN" required />
            </div>
            
            <div class="form-group">
                <label>Category</label>
                <input type="text" name="newBookCate" />
            </div>
            
            <div class="form-group">
                <label>Published Year</label>
                <input type="number"  id="yearInput" min="1980" step="1" name="newBookYear" />
            </div>


            <div class="form-group">
                <label>Total (*)</label>
                <input type="number" name="quantity" min="1" step="1" required />
            </div>

            <div class="form-group">
                <label>Description (*)</label>
                <textarea name="description" rows="3" required=""></textarea>
            </div>

            <input type="hidden" name="action" value="updateinventory new book" />
            <button type="submit">Import</button>
        </form>
    </div>
</div>
<p style="color:red;">${UPDATEINVENSTATUS}</p>

<script>
    document.getElementById("yearInput").max = new Date().getFullYear();
</script>
</body>
</html>
