package application.config;

import javax.servlet.http.HttpServletRequest;

public class HttpConfig {
    public static final String getMethod = "get";
    public static final String postMethod = "post";

    public static String getUrl(String ip, HttpServletRequest request){
        // 3、获得客户机的信息---获得访问者IP地址
//        String remoteAddr = request.getRemoteAddr();
        //获取资源地址
        String requestURI = request.getRequestURI();
        String param = request.getQueryString();
        return ip + requestURI + "?" + param;
    }
}
