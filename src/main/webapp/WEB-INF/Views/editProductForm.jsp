<%-- 
    Document   : editProductForm
    Created on : 2017/9/24, 下午 04:08:02
    Author     : student
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="../../css/w3.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <h1>編輯商品: ${pname}</h1>
        產品編號: ${pid} <br/>
        產品說明: <textarea style="width: 300px;height: 200px">${product.productDescription}</textarea>
    </body>
</html>
