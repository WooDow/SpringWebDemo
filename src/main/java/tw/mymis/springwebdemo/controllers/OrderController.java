/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.mymis.springwebdemo.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author student
 */
@Controller
public class OrderController {
    @Autowired
    model.Product p;
    
    @RequestMapping("/showProduce.mvc")
    public ModelAndView showProduct(HttpServletRequest req) {
        ModelAndView mv = new ModelAndView();
        p.setPrice(8233);
        //model.Product p = new model.Product();
        mv.addObject("product",p);
        mv.setViewName("listProduct");
        return mv;
    }
}
