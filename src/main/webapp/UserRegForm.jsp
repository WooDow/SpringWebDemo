<%-- 
    Document   : UserRegForm
    Created on : 2017/9/24, 上午 11:03:27
    Author     : student
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>使用者帳密註冊</h1>
        <form action="UserReg.mvc" method="POST">
            帳號:<input type="text" name="account" value="" />
            密碼:<input type="password" name="passwd" value="" />
            <input type="submit" value="註冊" />
        </form>
    </body>
</html>
