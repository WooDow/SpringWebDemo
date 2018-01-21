/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traditional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author student
 */
// 對應 /abc.mvc
public class AbcController extends AbstractController{

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
        ModelAndView mv = new ModelAndView();
        //取出登錄表單的帳密做驗證
        String user = req.getParameter("account");
        String pass = req.getParameter("passwd");
        mv.addObject("user", user);
        mv.addObject("passwd", pass);
        mv.setViewName("abc");
        return mv;
    }
    
}
