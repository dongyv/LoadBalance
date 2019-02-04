package com.dongyv.blog.web;

import com.dongyv.blog.model.User;
import com.dongyv.blog.service.redis.ICacheCompone;
import com.dongyv.blog.service.user.IUserService;
import com.dongyv.blog.wrapper.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Controller
public class UserController {

    @Autowired(required = false)
    private IUserService userService;

    @Autowired
    private ICacheCompone cacheCompone;


    @RequestMapping("/login")
    @ResponseBody
    public ApiResult login(HttpServletRequest request, HttpServletResponse response){
        System.out.println("请求进入当前方法");
        String name = request.getParameter("name");
        String psd = request.getParameter("psd");
        User user = new User();
        user.setName(name);
        user.setPassword(psd);
        cacheCompone.putObject("user",user, 2,TimeUnit.DAYS);
        User user1 = (User)cacheCompone.getObject("user");
        return userService.login(name,psd);
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

}
