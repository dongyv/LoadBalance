package application.smartframe.aop.aspect;

import application.smartframe.annotation.Aspect;
import application.smartframe.annotation.Controller;
import application.smartframe.aop.proxy.AspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 拦截Controller方法
 * @author xiachenhang
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);

    private long begin;

    @Override
    public void before(Class<?> targetClass, Method targetMethod, Object[] params) {
        LOGGER.debug("--------------begin--------------");
        LOGGER.debug(String.format("class %s",targetClass.getName()));
        LOGGER.debug(String.format("method %s",targetMethod.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> targetClass, Method targetMethod, Object[] params) {
        long x = System.currentTimeMillis() - begin;
        LOGGER.debug("time: "+x+"ms");
        LOGGER.debug("--------------end--------------");
    }
}
