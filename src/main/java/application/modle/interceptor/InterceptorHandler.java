package application.modle.interceptor;


import javax.servlet.http.HttpServletRequest;

/**
 * @author xiachenhang
 */
public interface InterceptorHandler {

    boolean hasInterceptorHandler(HttpServletRequest request,Object handler);

    void setNextInterceptor(InterceptorHandler interceptorHandler);
}
