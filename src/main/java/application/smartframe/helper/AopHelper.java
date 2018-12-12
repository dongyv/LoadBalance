package application.smartframe.helper;

import application.smartframe.annotation.Aspect;
import application.smartframe.aop.proxy.AspectProxy;
import application.smartframe.aop.proxy.Proxy;
import application.smartframe.aop.proxy.ProxyManager;
import application.smartframe.aop.proxy.TransactionProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * 方法拦截助手类
 *
 * Created by xiachenhang on 2018/12/8
 */
public final class AopHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            //获取所有继承AspectProxy的类与代理的目标类映射
            //存的是Aspect —> Controller
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            //获取所有目标类 与 代理此目标 的代理类集合 映射
            //Controller ->  Aspect.class.newInstance
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            //遍历
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                //创建代理
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                //遍历添加代理为bean，如果已经被添加至bean类中 将会覆盖已存在的bean 现在的是一个有CGLib生成的代理类
                BeanHelper.setBean(targetClass, proxy);
            }
        } catch (Exception e) {
            LOGGER.error("aop failure", e);
        }
    }

    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
        addAspectProxy(proxyMap);
        /**
         * 1. 为所有Service方法添加事务代理
         * 2. 判断是否有事务注解
         * 3. 有事务注解的开启事务->执行方法->提交事务
         * 4. 无事务注解的执行方法(应用于单条查询)
         */
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    private static void addTransactionProxy(Map<Class<?>,Set<Class<?>>> proxyMap){
        Set<Class<?>> serviceClassSet = ClassHelper.getServiceClassSet();
        proxyMap.put(TransactionProxy.class,serviceClassSet);
    }

    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        //获取实现切面代理的类
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            //如果有@Aspect注解 加入代理类的
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
    }



    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
        Class<? extends Annotation> annotation = aspect.value();
        if (annotation != null && !annotation.equals(Aspect.class)) {
            //获取有 切面注解value中的注解 的类 ，加入此注解的目标类中
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    /**
     *  获取 目标类的代理类列表 映射
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy = (Proxy) proxyClass.newInstance();

                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    //如果目标类还没有添加代理 初始化并添加代理
                    List<Proxy> proxyList = new ArrayList<Proxy>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
    }
}
