<%-- 
    Document   : ProductSaved
    Created on : 2017/10/8, 下午 01:59:31
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
        <h1>產品存檔完畢</h1>
        <a href="#">請按此連結檢視完整產品資訊</a>
        產品 : ${ newProduct.productName } <br/>
        照片 :   <img src="images/${newProduct.imageName}"/>
    </body>
</html>
