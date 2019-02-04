package application.smartframe.aop.proxy;

/**
 * 代理接口
 * Created by xiachenhang on 2018/12/8
 */
public interface Proxy {
    /**
     * 执行链代理
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
