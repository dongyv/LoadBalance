package application.modle.request;

import application.config.HttpConfig;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiachenhang
 */
public class UserHandler implements RequestHandler {

    private String ip;

    public UserHandler(String ip){
        this.ip = ip;
    }


    @Override
    public String handleRequest(HttpServletRequest request) {
        return HttpConfig.getUrl(ip,request);
    }

}
