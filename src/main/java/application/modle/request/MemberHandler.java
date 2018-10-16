package application.modle.request;

import application.config.HttpConfig;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiachenhang
 */
public class MemberHandler implements RequestHandler {

    private String ip;

    public MemberHandler(String ip){
        this.ip = ip;
    }


    @Override
    public String handleRequest(HttpServletRequest request) {
            return HttpConfig.getUrl(ip,request);
    }

}
