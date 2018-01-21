package tw.mymis.springwebdemo.controllers;

import cmdata.dao.ProductsJpaController;
import cmdata.dao.UsersController;
import cmdata.model.Products;
import cmdata.model.Users;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.C;
import model.D;
import model.Product;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import java.sql.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;




@Controller
public class DemoController {
    
    private Connection con;
    private Statement stmt;
    private ResultSet rs;   
    
    @Autowired
    User guser ;
    
    private EntityManagerFactory emf = null;
    
    
    //處理 /  首頁
    @RequestMapping("/index.mvc")
    public String get() {
        //Bootstraping JPA Factory        
        return "index";
    }
    
    // 負責處理 /test.mvc
    @RequestMapping("/test.mvc")
    public String Test() {
        // Model 代表資料
        // View 代筆畫面(Web Page)        
        ModelAndView mv = new ModelAndView();
        return "test";
    }
    
    @RequestMapping("/show.mvc")
    public ModelAndView ShowProdcut( Product p, User u,@Autowired D d1) {
        
        Product np = p;
        User nu = u;
        D d = d1;
        np.setName("Apple iPhone 7+");
        np.setId("m001");
        np.setPrice(35000);
        nu.setAccount("user01@gmail.com");
        nu.setUsername("user01");
        
        ModelAndView mv = new ModelAndView();
        mv.setViewName("info");
        mv.addObject("product", np);
        mv.addObject("user", nu );
        mv.addObject("d",d);
        return mv;
    }
    
    @RequestMapping("/about.mvc")
    public ModelAndView About() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("about");
        mv.addObject("url", "http://www.mymis.tw");
        mv.addObject("company","mymis it co ltd");
        
        return mv;
    }
    
    @RequestMapping("/newLogin2.mvc")
    public ModelAndView login2(@RequestParam("account") String user ,
            @RequestParam("passwd") String password , HttpSession session) {
        ModelAndView mv = new ModelAndView();
        //取出登錄表單的帳密做驗證        
        //連接 mariadb 存取 classicmodels 進行 users 資料查詢
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mariadb://localhost/classicmodels","dba","1234");
            stmt = con.createStatement();
            String sql = "Select * from users where account='"+user+"' and passwd='" + password+"';";
            System.out.println("sql: " + sql);
            rs = stmt.executeQuery(sql);
            if( rs.next() ) {
                 session.setAttribute("login", true);
            } else {
                 session.setAttribute("login", false);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DemoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println("SQL異常: " + ex.getMessage() );
        }
        
        //
        //mv.addObject("user", user);
        //mv.addObject("passwd", password);
        mv.setViewName("index");
        
        return mv;
    }
    
    @RequestMapping("/newLogin.mvc")
    public ModelAndView login(@RequestParam("account") String user ,
            @RequestParam("passwd") String password , HttpSession session) {
        ModelAndView mv = new ModelAndView();
        //EntityManager em = datasource.EMFManager.getEMF().createEntityManager();
        EntityManager em = datasource.EMFManager.getEntityManager();
        List<Users> users = 
             em.createNativeQuery("select * from users where account='"+user+"' and passwd='"+password+"'", Users.class).getResultList();
        if( users.size() > 0 ) {
            session.setAttribute("login", true);
        } else {
            session.setAttribute("login", false);
        }
        //em.getTransaction().begin(); //開始交易
          // 修改了五個  Tables 的資料
        //em.getTransaction().rollback();// 交易完成
        //
        //mv.addObject("user", user);
        //mv.addObject("passwd", password);
        mv.setViewName("index");
        
        return mv;
    }
    
    @PersistenceUnit(unitName = "cm")
    EntityManagerFactory emf2;       // Spring 透過 IoC 注射至 emf2 
    
    @RequestMapping("/listUsers2.mvc")
    public ModelAndView listUsers2() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("ListUsers");
        EntityManager em  =  emf2.createEntityManager();
        List<Users>  users = em.createNativeQuery("select * from users", Users.class).getResultList();
        String message = "使用者資料表內共有 " + users.size() + " 筆資料";
        mv.addObject("message", message);        
        return mv;        
    }
    
    
    @RequestMapping("/listUsers.mvc")    
    public ModelAndView listUsers() {                
        ModelAndView mv = new ModelAndView();
        mv.setViewName("ListUsers");
        UsersController userController = new UsersController(emf2);
        ProductsJpaController productController = new ProductsJpaController(emf2);
        
        String message = "UserController 使用者資料表內共有 " + userController.getUsersCount() + " 筆資料";
        message+=("<br/> 產品有 " + productController.getProductsCount() + "筆");
        
        mv.addObject("message", message);        
        return mv;        
    }
    
    @RequestMapping("/UserReg.mvc")
    public ModelAndView userReg(@RequestParam("account") String user , @RequestParam("passwd") String passwd) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("NewUserInfo");
        mv.addObject("username", user);
        // 先檢查帳號是否存在?
        EntityManager em = emf2.createEntityManager();
        //Query q= em.createNativeQuery("select * from users where account='"+user+"'", Users.class);
        //TypedQuery q = em.createNamedQuery("Users.findByAccount",Users.class);
        //q.setParameter("account", user);
        //q.getResultList();
        List<Users> users = em.createNamedQuery("Users.findByAccount", Users.class)
                .setParameter("account", user)
                .getResultList();       
        
        if( users.size() > 0 ) {
            //帳號已經存在
            mv.addObject("message", "Named Query版本: 該帳號已經存在,無法重複建立");
        } else {        
        // 透過 EntityManager 新增帳號
            Users newUser = new Users();
            newUser.setAccount(user);
            newUser.setPasswd(passwd);        
            UsersController userController = new UsersController(emf2);
            userController.create(newUser);
            mv.addObject("message", "帳號建立完畢");
        }
//        EntityManager em = emf2.createEntityManager();
//        em.getTransaction().begin();
//        em.persist(newUser);
//        em.getTransaction().commit();
        // 
        return mv;
    }
    
    @RequestMapping("/updatePasswd.mvc")
    public ModelAndView updatePassword() {
        ModelAndView mv = new ModelAndView();
        UsersController userCtrl = new UsersController(emf2);
        Users user = userCtrl.findUsers(10);
        
        try {
            userCtrl.edit(user);
            mv.addObject("message", "密碼變更成功");
        } catch (Exception ex) {
            mv.addObject("message", "密碼變更失敗 原因: " + ex.getMessage());
        }
        mv.setViewName("updatePassword");
        return mv;
    }

     @RequestMapping("/deleteUser.mvc")
    public ModelAndView deleteUser() {
        ModelAndView mv = new ModelAndView();
        UsersController userCtrl = new UsersController(emf2);
        
        try {
            userCtrl.destroy(10);
            mv.addObject("message", "用者刪除成功");
        } catch (Exception ex) {
            mv.addObject("message", "用者刪除失敗 原因: " + ex.getMessage());
        }
        mv.setViewName("updatePassword");
        return mv;
    }
    
    @RequestMapping("/allProductsList.mvc")
    public ModelAndView getAllProducts(){
        ModelAndView mv = new ModelAndView();
        EntityManager em = emf2.createEntityManager();
        List<Products> products = em.createNamedQuery("Products.findAll" , Products.class).getResultList();
        
        mv.addObject("products",products);
        mv.setViewName("listProducts");
        return mv;
    }
    
    @RequestMapping("/editProduct.mvc")
    public ModelAndView editProductA(
            @RequestParam("pid")String pid,
            @RequestParam("pname")String pname){
        ModelAndView mv = new ModelAndView();
        mv.addObject("pid",pid);
        mv.addObject("pname",pname);
        EntityManager em =emf2.createEntityManager();
        Products p = em.createNamedQuery("Products.findByProductCode", Products.class)
                .setParameter("productCode", pid)
                .getSingleResult();
        
        mv.addObject("product",p);
        mv.setViewName("editProductForm");
        return mv;
    }
}
