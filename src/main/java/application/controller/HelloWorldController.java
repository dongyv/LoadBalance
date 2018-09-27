package application.controller;

import application.config.HttpConfig;
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
    /**
     * 负载均衡
     */
    @RequestMapping("/load")
    @ResponseBody
    public void load(HttpServletRequest request, HttpServletResponse response){
        //获取资源地址
        String requestURI = request.getRequestURI();
//        StringBuffer requestURL = request.getRequestURL();
        // 3、获得客户机的信息---获得访问者IP地址
//        String remoteAddr = request.getRemoteAddr();
        String param = request.getQueryString();
        LoanBalance loan = new Random();
        //获取转发的ip地址
        String ip = loan.getServer();
        if(request.getMethod().equals(HttpConfig.getMethod)){
            try {
                String url = ip + requestURI + "?" + param;
                //重定向
                response.sendRedirect(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(request.getMethod().equals(HttpConfig.postMethod)){
            //使用责任链模式，将每个模块进行对应转发

        }
    }

}