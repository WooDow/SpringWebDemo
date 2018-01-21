<%-- 
    Document   : listProducts
    Created on : 2017/9/24, 下午 03:30:08
    Author     : student
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
        <link href="../../css/w3.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <h1 class="w3-green w3-bar">模型清單</h1>
        <table class="w3-table w3-border w3-hoverable">
            <header>
                <tr>
                    <td>產品名稱</td> <td>模型比例</td> <td>產品售價</td> <td>產品庫存</td> <td>功能</td>
                </tr>
            </header>
            <c:forEach items="${products}" var="product">
                <tr>
                    <td>${product.productName}</td><td>${product.productScale}</td>
                    <td>${product.msrp}</td><td>${product.quantityInStock}</td>
                    <td><a href="editProduct.mvc?pid=${product.productCode}&pname=${product.productName}">編輯</a></td>
                </tr>
                    
            </c:forEach>
            
        </table>
    </body>
</html>
