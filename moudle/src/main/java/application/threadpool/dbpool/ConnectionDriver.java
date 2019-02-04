package application.threadpool.dbpool;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

public class ConnectionDriver {

    static class ConnectionHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws InterruptedException {
            if(method.getName().equals("commit")){
                TimeUnit.MINUTES.sleep(100);
            }
            return null;
        }
    }

    /**
     * 创建一个Connection代理，在commit时休眠10秒
     */
    public static final Connection createConnection(){
        return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(),
                new Class[]{ Connection.class },new ConnectionHandler());
    }
}
