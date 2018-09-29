package application.modle;

import application.config.HttpConfig;
import application.config.ModuleConfig;

import javax.servlet.http.HttpServletRequest;

public class MemberHandler implements RequestHandler {

    private RequestHandler next;
    private String ip;

    public MemberHandler(String ip){
        this.ip = ip;
    }


    @Override
    public String handleRequest(HttpServletRequest request) {
        if(request.getRequestURL().toString().contains(ModuleConfig.member)){
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
