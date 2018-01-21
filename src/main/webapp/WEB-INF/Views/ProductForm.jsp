<%-- 
    Document   : ProductForm
    Created on : 2017/10/8, 下午 02:08:54
    Author     : student
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/tags/form" prefix="form" %>
<!-- spring 的表單套件 form -->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>產品建檔&維護作業</h1>
        <form action="showProductForm.mvc">
            產品編號 :<input type="text" name="qNo" placeholder="請輸入產品編號" value="" />
            <input type="submit" value="查詢" />
        </form>
        
        <form action="saveProduct.mvc" enctype="multipart/form-data">
            模型名稱 :<input type="text" name="pName" value="${p.productName}" /><br/>
            模型比例 :<input type="text" name="pScale" value="${p.productScale}" /><br/>
            現有庫存 :<input type="text" name="pStock" value="${p.quantityInStock}" /><br/>
            標準售價 :<input type="text" name="pPrice" value="${p.msrp}" /><br/>
            參考圖片 :<input type="text" name="pImage" value="" /><br/>
            <input type="submit" value="送出" />
        </form>
            
        <!-- 利用 form 標籤 modelAttribute 及 path 為必要屬性 path 為該表單中的屬性名稱 modelAttribute 為 spring 傳過來這個 JSP 的物件-->
        <form:form  modelAttribute="p2"  action="saveProduct.mvc" enctype="multipart/form-data">
            模型名稱 :<form:input type="text" name="pName" path="productName" /><br/>
            模型比例 :<form:input type="text" name="pScale" path="productScale" /><br/>
            現有庫存 :<form:input type="text" name="pStock" path="quantityInStock" /><br/>
            標準售價 :<form:input type="text" name="pPrice" path="msrp" /><br/>
            <!-- 因為在資料庫中沒 meta data 這個欄位 -->
            <input type="hidden" name="productCode" value="You can't see me~">
            參考圖片 :<input type="file" name="pImage" value="" /><br/>
            <input type="submit" value="送出" />
        </form:form>
        
    </body>
</html>
