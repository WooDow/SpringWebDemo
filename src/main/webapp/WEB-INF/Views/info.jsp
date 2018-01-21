<%-- 
    Document   : info
    Created on : 2017/8/27, 下午 01:56:49
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
        <h1>公司產品資訊</h1>
        d = ${d.mesg} <br/>
        訂購人:  ${user.username} <br/>        
        產品名稱:  ${ product.name} <br/>
        售價:     ${ product.price}  <br/>
    </body>
</html>
