

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Log In</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/login.css"/>
    </head>
    <body>
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
        
        <form action="MainController" method="post">
            <p>Email : <input type="text" name="txtemail" required=""/></p>
            <p>PassWord : <input type="password" name="txtpassword" required=""/></p>
            <p><input type="submit" name="action" value="login"/></p>
        </form>
        
        <div class="signup-container">
            <p>Don't have an account?</p>
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="signuppage">
                <input type="submit" value="Sign Up">
            </form>
        </div>

        <div class="error-message">${requestScope.LOGINERROR}</div>
    </body>
</html>