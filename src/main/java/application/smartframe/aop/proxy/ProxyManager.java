package application.smartframe.aop.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理管理器
 *
 * @author xiachenhang
 */
public class ProxyManager {

    public static <T> T createProxy(final Class<?> targetClass , final List<Proxy> proxyList){
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            //创建一个代理 并且 添加拦截方法
            @Override
            public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass,targetObject,methodProxy,targetMethod,methodParams,proxyList).doProxyChain();
            }
        });
    }

}
