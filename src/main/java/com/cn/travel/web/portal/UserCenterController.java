package com.cn.travel.web.portal;

import com.cn.travel.role.user.entity.User;
import com.cn.travel.role.user.service.imp.UserService;
import com.cn.travel.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class UserCenterController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping("/userCenter")
    public ModelAndView userCenter(HttpSession httpSession) {
        ModelAndView mv = this.getModeAndView();
        try {
            User user= userService.findByUserName(httpSession.getAttribute("userName").toString());
            mv.addObject("fee",user.getMoney());
        }catch (Exception e){
            e.printStackTrace();
        }
        mv.setViewName("portal/userCenter");
        return mv;
    }

}
