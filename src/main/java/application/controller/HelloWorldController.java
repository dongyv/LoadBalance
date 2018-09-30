package application.controller;

import application.config.HttpConfig;
import application.modle.*;
import application.resource.LoanBalance;
import application.resource.Random;
import application.util.HttpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HelloWorldController {
    /**
     * 负载均衡
     */
    @RequestMapping("/*/load")
    @ResponseBody
    public void load(HttpServletRequest request, HttpServletResponse response){
        LoanBalance loan = new Random();
        //获取转发的ip地址
        String ip = loan.getServer();
        String url="";
        if(request.getMethod().toUpperCase().equals(HttpConfig.getMethod.toUpperCase())){
            url = HttpConfig.getUrl(ip,request);
        }else if(request.getMethod().toUpperCase().equals(HttpConfig.postMethod.toUpperCase())){
            //使用工厂模式生产每个模块的url
            HandlerFactory handlerFactory = HandlerFactory.createInstance();
            //工厂创建user模块的url
//            String userUrl = handlerFactory.productRequest(request,new UserHandler(ip)).getUrl();
            //工厂创建member模块的url
            String memberUrl = handlerFactory.productRequest(request,new MemberHandler(ip)).getUrl();
            System.out.println(memberUrl);
            //使用责任链模式，将每个模块进行对应转发
            url = RequestHandlerAll.getRequestHandler(ip).handleRequest(request);
            System.out.println(url);
        }
//        HttpUtil.getRedircet(response,url);
    }

}