/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.mymis.springwebdemo.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.sql.*;


@Controller
public class UserManagementController {
    @Autowired 
    HttpServletRequest req;      
    // 宣告一個變數 req 要求 Spring 賦予 req 資料(@Autowired), 請自行依照變數類行判斷該給什麼
    
    public void Register() {
        
    }
    public void PasswordReset() {
    
    }
    
    @RequestMapping("/loginCheck.mvc")
    public ModelAndView Authentication() {        
        ModelAndView mv = new ModelAndView();        
        //驗證帳號與密碼
        String user = req.getParameter("account");
        String pass = req.getParameter("passwd");
        mv.addObject("account",user);
        mv.addObject("passwd", pass);        
        //成功--> welcome.jsp
        mv.setViewName("welcome");
        //失敗--> PleaseLogin.jsp        
        return mv;
    }
    
    @RequestMapping("/loginCheck2.mvc")
    public ModelAndView Authentication2(HttpServletRequest req,HttpServletResponse response) {        
        ModelAndView mv = new ModelAndView();   
        boolean isLogin = false;
        //驗證帳號與密碼
        String user = req.getParameter("account");
        String pass = req.getParameter("passwd");
        // 使用 Spring --> 使用 DI 方式將物件產生出來
        Connection con =null;
        Statement stmt = null;
        ResultSet  rs = null; 
        try {
            // 每一個 Java Developer 必須熟悉 SQL 操作
            // 連接資料庫 確認使用者 ....
            // 使用 Mariadb Driver
            // 連入資料庫
            // 查詢是否有該帳號
            Class.forName("org.mariadb.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mariadb://localhost/classicmodels","dbadmin","1234");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserManagementController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println("連線建立失敗");
        }
        try{
            if( con != null ) {
                stmt = con.createStatement();
                // 純示範 請勿使用此種串接字串方式查詢資料庫  容易發生 SQL Injection 一定要檢查 user, pass 內容是否有不妥當字串
                // account =>   ';  "select * from users where account = '';   ';drop database ....
                rs = stmt.executeQuery("select * from users where account = '" + user+"' and passwd='"+pass+"'");
                if( rs.next()) {
                    //有資料
                    isLogin = true;
                } else {
                    isLogin = false;
                }
            }
        }catch(SQLException ex) {
            System.out.println("連線建立失敗");
        }
        
        
        //成功--> welcome.jsp
        mv.addObject("account",user);
        //mv.addObject("passwd", pass);         
        mv.addObject("login", isLogin);
        mv.setViewName("welcome");
        //失敗--> PleaseLogin.jsp        
        return mv;
    }
}
