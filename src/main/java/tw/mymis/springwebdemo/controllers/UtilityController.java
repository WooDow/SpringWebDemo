/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.mymis.springwebdemo.controllers;

import cmdata.dao.ProductsJpaController;
import cmdata.model.Products;
import datasource.EMFManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.io.*;
import java.nio.file.*;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author student
 */
@Controller
public class UtilityController {

    @RequestMapping("/fileUpload.mvc")
    // MultipartFile 要在 spring 設定檔設定 CommonsMultipartResolver 才可以接收檔案
    public ModelAndView fileUpload(@RequestParam("uploadFile") MultipartFile uploadFile,HttpServletRequest request) throws IOException {  
    // @RequestParam("uploadFile")可不寫  因為跟MultipartFile 物件同名稱 會自動對應
        ModelAndView mv = new ModelAndView();
        mv.setViewName("uploadMessage");
        //檔案來源 (讀取)
        System.out.println("File Name：" + uploadFile.getOriginalFilename());
        System.out.println("File type：" + uploadFile.getContentType());
        System.out.println("File Size：" + uploadFile.getSize() + " bytes");
        // 將圖片存入 C:/temp/ (使用 java.nio.file.Files.copy)
        InputStream is = uploadFile.getInputStream();
        File targetFile = new File("C:/temp/" + uploadFile.getOriginalFilename());
        //java.nio.file.Files.copy(is, targetFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        // 用 request.getContextPath() 取得網站所在路徑 
        System.out.println("Context Path :" + request.getContextPath());
        String imagePath = request.getSession().getServletContext().getRealPath("/images");
        System.out.println("Images path : " + imagePath);
        File targetFile2 = new File(imagePath + "/" + uploadFile.getOriginalFilename());
        java.nio.file.Files.copy(is, targetFile2.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        
        mv.addObject("imageName" , uploadFile.getOriginalFilename());
        return mv;
    }

    @RequestMapping("/showProductForm.mvc")
    public ModelAndView ProductForm(HttpServletRequest request){
        
        //接受表單串入產品編號
        String qNo = null;
        cmdata.model.Products p =null;
        qNo = request.getParameter("qNo");
        if(qNo!=null){
            EntityManager em = EMFManager.getEntityManager();
            p = (cmdata.model.Products)em.createNativeQuery("select * from products where productCode='"+qNo+"'",Products.class).getSingleResult();
            System.out.println("產品名稱 :" + p.getProductName());
        }
        
        //使用產品編號查詢資料庫
        //取得該筆資料後將資料帶入表單內
        ModelAndView mv =new ModelAndView();
        mv.setViewName("ProductForm");
        //如果不用 EntityManager 就要分別塞入 20筆欄位
        if(p!=null){
            mv.addObject("p",p);
            mv.addObject("p2",p);
        }else{
            // 但 p 是 null 為何 ${p.productName} 沒問題? 因為 ${ } EL語法會主動攔截 null
            mv.addObject("p2",new Products());  //modelAttritube 不可以為 null 若 null 先給空物件
        }
        
        
        return mv;
    }
    
    // 表單若是透過 <form : form modelAttritube 宣告 則會使用 modelAttritube 傳入物件
    // ModelAttribute 是 spring 的用法 接收form 傳過來的物件
    @RequestMapping("/saveProduct.mvc")
    public ModelAndView saveProduct(@ModelAttribute("p2")Products p2,
            @RequestParam("pImage") MultipartFile pImage,
            HttpServletRequest request) throws IOException, Exception{
        
        // 要另外宣告 products 物件 生命週期會較傳進來的參數長一些
        Products newProduct = p2;
        ModelAndView mv =new ModelAndView();
        newProduct.setImageName(pImage.getOriginalFilename());
        
        mv.setViewName("ProductSaved");
        System.out.println("產品售價:" + newProduct.getMsrp());
        System.out.println("產品名稱:" + newProduct.getProductName());
        System.out.println("產品照片:" + pImage.getOriginalFilename());
        String imagePath = request.getSession().getServletContext().getRealPath("/images");
        System.out.println("Images path : " + imagePath);
        File targetFile2 = new File(imagePath + "/" + pImage.getOriginalFilename());
        java.nio.file.Files.copy(pImage.getInputStream(), targetFile2.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        
        // 修改資料庫裡的資料
        ProductsJpaController pController = new ProductsJpaController(EMFManager.getEMF());
        // 重新抓出該筆資料
        pController.findProducts(newProduct.getProductCode());
        // 填入被使用者修改的欄位
        // 重新存檔
        pController.edit(newProduct);
        
        mv.addObject("newProduct",newProduct);
        return mv;
    }
}
