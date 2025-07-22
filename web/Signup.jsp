
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Sign Up</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/signup.css" />
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
        
        <form class="signup-form" action="MainController"  method="post">
            <p>Name : <input type="text" name="txtname" required=""></p>
            <p>Email : <input type="email" name="txtemail" required=""></p>
            <p>Password : <input type="password" name="txtpassword" required=""></p>
            <p>Confirm password : <input type="password" name="txtconfirmpassword" required=""></p>
            <p><input type="submit" name="action" value="register"></p>
        </form>
        
        <div class="login-container" >
            <p>Already have an account?</p>
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="loginpage">
                <input type="submit" value="Log In">
            </form>
        </div>

        <div class="error-message">${requestScope.SIGNUPSTATUS}</div>
      
        
        
    </body>
</html>
