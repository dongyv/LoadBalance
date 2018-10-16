package application.modle.interceptor;

import application.modle.request.HandlerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiachenhang
 */
public class InterceptorProvider {
    private static InterceptorProvider interceptorProvider;

    private HttpServletRequest httpServletRequest;
    private Object handler;

    private InterceptorProvider(HttpServletRequest httpServletRequest, Object handler){
        this.httpServletRequest = httpServletRequest;
        this.handler            = handler;
    }

    public static InterceptorProvider createInstance(HttpServletRequest httpServletRequest, Object handler){
        if(interceptorProvider == null){
            synchronized (HandlerFactory.class) {
                if(interceptorProvider == null){
                    interceptorProvider = new InterceptorProvider(httpServletRequest,handler);
                }
            }
        }
        return interceptorProvider;
    }

    public static InterceptorHandler getInterceptorHandlers(){
        InterceptorHandler power = new AnnotationInterceptor();
        InterceptorHandler role = new SignInterceptor();
        power.setNextInterceptor(role);
        return power;
    }
}
