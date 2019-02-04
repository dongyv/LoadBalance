package application.smartframe.aop.proxy;

import application.smartframe.annotation.Transaction;
import application.smartframe.helper.DataBaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 事务代理
 * @author xiachenhang
 * Created by xiachenhang on 2018/12/8
 */
public class TransactionProxy implements Proxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);
    private static final ThreadLocal<Boolean>FLAG_HOLDER = new ThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };
    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {

        Object result;
        boolean flag = FLAG_HOLDER.get();
        Method method = proxyChain.getTargetMethod();
        //如果还没有执行并且有事务注解
        if(!flag && method.isAnnotationPresent(Transaction.class)){
            try {
                FLAG_HOLDER.set(true);
                DataBaseHelper.beginTransaction();
                LOGGER.debug("begin transaction");
                result = proxyChain.doProxyChain();
                DataBaseHelper.commitTransaction();
                LOGGER.debug("commit transaction");
            }catch (Exception e){
                DataBaseHelper.rollBackTransaction();
                LOGGER.debug("rollback transaction");
                throw e;
            }finally {
                FLAG_HOLDER.remove();
            }
        }else {
            result = proxyChain.doProxyChain();
        }
        return result;
    }
}
