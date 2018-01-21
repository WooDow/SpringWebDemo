<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-indigo.css">
    </head>
    <body>
        <div class="w3-container w3-sand">            
            <div class="w3-bar w3-black">
                <a href="#" class="w3-bar-item w3-button">Home</a>
                <a href="#" class="w3-bar-item w3-button">公司</a>
                <div class="w3-dropdown-hover">
                <button class="w3-button">產品</button>
                    <div class="w3-dropdown-content w3-bar-block w3-card-4">
                        <a href="allProductsList.mvc" class="w3-bar-item w3-button">所有模型</a>          
                           <a href="#" class="w3-bar-item w3-button">汽車模型</a>
                      <a href="#" class="w3-bar-item w3-button">機車模型</a>
                      <a href="#" class="w3-bar-item w3-button">飛機模型</a>
                      <a href="showProductForm.mvc" class="w3-button w3-bar-item">產品維修</a>
                    </div>
                </div>                
                <div class="w3-dropdown-hover">
                <button class="w3-button">客服</button>
                    <div class="w3-dropdown-content w3-bar-block w3-card-4">
                      <a href="#" class="w3-bar-item w3-button">退換貨</a>
                      <a href="#" class="w3-bar-item w3-button">故障報修</a>
                      <a href="#" class="w3-bar-item w3-button">常見問題</a>
                    </div>
                </div>                
                <c:if test="${sessionScope.login}">
                    <a href="#" onclick="document.getElementById('id01').style.display='block'" class="w3-bar-item w3-button w3-blue w3-right">登出</a>
                </c:if>
                <c:if test="${not sessionScope.login}">
                    <a href="#" onclick="document.getElementById('id01').style.display='block'" class="w3-bar-item w3-button w3-green w3-right">登入</a>
                </c:if>
                <input type="text" size="30" class="w3-bar-item w3-input w3-right" placeholder="網站搜尋..">
            </div>
            <div id="id01" class="w3-modal">
            <div class="w3-modal-content">
              <div class="w3-container">
                <span onclick="document.getElementById('id01').style.display='none'" 
                class="w3-button w3-display-topright">&times;</span>
                <div class="w3-container">
                <div class="w3-row-padding">
                    <div class="w3-container w3-blue">
                        <h2>會員登入</h2>
                      </div>
                      <form class="w3-container" method="post" action="./newLogin.mvc">
                        <p>
                            
                            
                        <label>帳號</label>
                        <input name="account" class="w3-input" type="text"></p>
                        <p>
                        <label>密碼</label>
                        <input name="passwd" class="w3-input" type="text"></p>
                        <p>
                        <label>會員類型</label>
                          <select class="w3-select" name="option">
                            <option value="" disabled selected>請選擇身分</option>
                            <option value="1">一般會員</option>
                            <option value="2">中級會員</option>
                            <option value="3">VIP會員卡</option>
                          </select>
                        </p>
                        <p>
                            <input type="submit" class="w3-button w3-teal" value="登入"/>
                        </p>
                      </form>
                  </div>
              </div>              
            </div>
            </div>
            </div>
            <div class="w3-container">
               
            </div>
        </div>
    </body>
</html>
