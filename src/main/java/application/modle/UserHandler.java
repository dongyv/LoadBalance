package application.modle;

import application.config.HttpConfig;
import application.config.ModuleConfig;

import javax.servlet.http.HttpServletRequest;

public class UserHandler implements RequestHandler {

    private RequestHandler next;
    private String ip;

    public UserHandler(String ip){
        this.ip = ip;
    }


    @Override
    public String handleRequest(HttpServletRequest request) {
        if(request.getRequestURI().contains(ModuleConfig.user)){
            return HttpConfig.getUrl(ip,request);
        }else{
            if(next==null){
                throw new NullPointerException("next 不能为空");
            }
            return next.handleRequest(request);
        }
    }

    @Override
    public void setNextHandler(RequestHandler next) {
        this.next = next;
    }
}
