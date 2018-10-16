package application.modle.interceptor;

import application.modle.interceptor.annotation.NoNeedAccessAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public class AnnotationInterceptor implements InterceptorHandler{

    private static final Logger log = LoggerFactory.getLogger(AnnotationInterceptor.class);

    private InterceptorHandler handlerNext;

    @Override
    public boolean hasInterceptorHandler(HttpServletRequest request, Object handler) {
        if(handler instanceof HandlerMethod){
            if (isHaveAccess(handler)) {
                log.info("<== preHandle - 不需要认证注解不走认证.  token={}");
                return true;
            }
        }
        if(handlerNext != null){
            return handlerNext.hasInterceptorHandler(request,handler);
        }
        return false;
    }

    @Override
    public void setNextInterceptor(InterceptorHandler interceptorHandler) {
        this.handlerNext = interceptorHandler;
    }

    private boolean isHaveAccess(Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        NoNeedAccessAuthentication responseBody = AnnotationUtils.findAnnotation(method, NoNeedAccessAuthentication.class);
        return responseBody != null;
    }
}
