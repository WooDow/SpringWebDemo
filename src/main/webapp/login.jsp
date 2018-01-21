<%-- 
    Document   : login
    Created on : 2017/8/27, 下午 02:50:02
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
        <h1>ABC 會員登入畫面</h1>
        <form method="POST" action="newLogin.mvc">
            帳號:<input type="text" name="account" value="" /> <br/>
            密碼: <input type="password" name="passwd" value="" /><br/>
            <input type="submit" value="登入" />
        </form>
    </body>
</html>
