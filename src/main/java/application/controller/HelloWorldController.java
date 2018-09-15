package application.controller;

import application.resource.Hash;
import application.resource.LoanBalance;
import application.resource.Random;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class HelloWorldController {
	
    //负载均衡
    @RequestMapping("/load")
    @ResponseBody
    public void load(HttpServletRequest request, HttpServletResponse response){
        String requestURI = request.getRequestURI();//获取资源地址
//        StringBuffer requestURL = request.getRequestURL();
        // 3、获得客户机的信息---获得访问者IP地址
//        String remoteAddr = request.getRemoteAddr();
        String param = request.getQueryString();
        LoanBalance loan = new Random();
        String ip = loan.getServer();//获取转发的ip地址
        if(request.getMethod().equals("GET")){//get方法请求，那么重定向
            try {
                String url = ip + requestURI + "?" + param;
                response.sendRedirect(url);//重定向
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(request.getMethod().equals("POST")){//post方法请求，那么转发

        }
    }

}