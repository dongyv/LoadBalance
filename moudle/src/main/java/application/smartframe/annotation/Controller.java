package application.smartframe.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xiachenhang on 2018/12/8
 * 控制器注解
 * @Target(ElementType.TYPE)   //接口、类、枚举、注解
 * @Retention(RetentionPolicy.RUNTIME)  // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
 * http://blog.csdn.net/yixiaogang109/article/details/7328466 具体可以看
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
}
