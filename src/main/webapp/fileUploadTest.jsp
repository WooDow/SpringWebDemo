<%-- 
    Document   : fileUploadTest
    Created on : 2017/10/8, 上午 10:02:33
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
        <h1>檔案上傳測試</h1>
        
        <form action="fileUpload.mvc" method="POST" enctype="multipart/form-data">
            <input type="file" name="uploadFile" value="" />
            <input type="submit" value="Upload" />
            
        </form>
    </body>
</html>
