package application.ChainOfResponsibility;

import application.config.ModuleConfig;

import javax.servlet.http.HttpServletRequest;

public class MemberHandler implements RequestHandler {

    private RequestHandler next;
    private String ip;

    public MemberHandler(String ip){
        this.ip = ip;
    }


    @Override
    public int handleRequest(HttpServletRequest request) {
        if(request.getRequestURI().contains(ModuleConfig.member)){

        }
        return 0;
    }

    @Override
    public void setNextHandler(RequestHandler next) {
        this.next = next;
    }
}
