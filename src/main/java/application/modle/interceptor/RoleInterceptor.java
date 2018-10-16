package application.modle.interceptor;

import javax.servlet.http.HttpServletRequest;

public class RoleInterceptor implements InterceptorHandler{

    private InterceptorHandler handlerNext;


    @Override
    public boolean hasInterceptorHandler(HttpServletRequest request, Object handler) {


        if(handlerNext != null){
            return handlerNext.hasInterceptorHandler(request,handler);
        }
        return false;
    }

    @Override
    public void setNextInterceptor(InterceptorHandler interceptorHandler) {
        this.handlerNext = interceptorHandler;
    }
}
